package com.sts15.fargos.items.talismans;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.init.Config;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class Undying_Talisman extends TalismanItem implements Undying_Talisman_Provider {

    private static final String talismanName = "undying_talisman";
	
    private static Map<UUID, Long> undyingCooldowns = new HashMap<>();

    public Undying_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {}
        return isEnabled;
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
                        if (currentTime - undyingCooldowns.get(player.getUUID()) >= Config.UNDYING_TALISMAN_COOLDOWN.getAsInt()) {
                            teleportPlayerBackwards(player, Config.UNDYING_TALISMAN_TELEPORT_DISTANCE.getAsInt());
                            player.setHealth(player.getMaxHealth());
                            undyingCooldowns.put(player.getUUID(), currentTime);
                            event.setCanceled(true);
                        }
                    }
            }
        }
    }
    
}