package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Undying_Talisman_Provider;
import com.sts15.fargos.utils.TalismanUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Undying_Talisman extends TalismanItem implements Undying_Talisman_Provider {

    private static final String talismanName = "undying_talisman";
	
    private static Map<UUID, Long> undyingCooldowns = new HashMap<>();
    private static final int UNDYING_COOLDOWN = 24000;

    public Undying_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    private static void teleportPlayerBackwards(Player player, int blocks) {
        Direction direction = player.getDirection();
        BlockPos pos = player.blockPosition();
        BlockPos newPos = pos.relative(direction.getOpposite(), blocks);
        player.teleportTo(newPos.getX(), newPos.getY(), newPos.getZ());
    }
    
    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {
        
        @SuppressWarnings({ "removal", "deprecation" })
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (player.hasEffect(EffectsInit.UNDYING_TALISMAN_EFFECT) || CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Undying_Talisman_Provider, player).isPresent()) {
                if (!TalismanUtil.isTalismanEnabled(player, talismanName))
                    return;
                if (player.getHealth() - event.getAmount() <= 0) {
                        long currentTime = player.level().getGameTime();
                        undyingCooldowns.putIfAbsent(player.getUUID(), 0L);
                        if (currentTime - undyingCooldowns.get(player.getUUID()) >= UNDYING_COOLDOWN) {
                            teleportPlayerBackwards(player, 10);
                            player.setHealth(player.getMaxHealth());
                            undyingCooldowns.put(player.getUUID(), currentTime);
                            event.setCanceled(true);
                        }
                    }
            }
        }
    }
    
}