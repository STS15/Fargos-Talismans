package com.sts15.fargos.items.talismans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
	
    private static Map<UUID, Long> undyingCooldowns = new HashMap<>();
    private static final int UNDYING_COOLDOWN = 24000;

    public Undying_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.undying_talisman")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
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
            if (!(event.getEntity() instanceof Player player))
                return;

            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof Undying_Talisman_Provider, player).isPresent()) {
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