package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.lang.reflect.Field;
import java.util.List;

public class Soul_of_Berserker extends TalismanItem implements ICurioItem, Soul_of_Berserker_Provider
{

    public static final String talismanName = "soul_of_berserker";

    public Soul_of_Berserker() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("When worn as talisman:").withStyle(ChatFormatting.GOLD));

        double melee = Config.SOUL_OF_BERSERKER_MELEE_DAMAGE.get() * 100.0;
        double speed = Config.SOUL_OF_BERSERKER_ATTACK_SPEED.get() * 100.0;
        double lifesteal = Config.SOUL_OF_BERSERKER_LIFESTEAL.get() * 100.0;

        tooltip.add(Component.literal(String.format("+%.0f%% Melee Damage", melee)).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [%.0f%%]", melee)).withStyle(ChatFormatting.GRAY)));

        tooltip.add(Component.literal(String.format("+%.0f%% Attack Speed", speed)).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [%.0f%%]", speed)).withStyle(ChatFormatting.GRAY)));

        tooltip.add(Component.literal("+1 Knockback Immunity").withStyle(ChatFormatting.BLUE)
                .append(Component.literal(" [1]").withStyle(ChatFormatting.GRAY)));

        tooltip.add(Component.literal(String.format("Melee Attacks Restore +%.0f%% Health", lifesteal)).withStyle(ChatFormatting.BLUE)
                .append(Component.literal(String.format(" [%.0f%%]", lifesteal)).withStyle(ChatFormatting.GRAY)));

        if (!checkConfigEnabledStatus()) {
            tooltip.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }

        super.appendHoverText(stack, context, tooltip, flag);
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

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static final ResourceLocation MELEE_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "berserker_melee_damage");
        private static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "berserker_attack_speed");

        private static double getMeleeDamageBoost() {
            return Config.SOUL_OF_BERSERKER_MELEE_DAMAGE.get();
        }

        private static double getAttackSpeedBoost() {
            return Config.SOUL_OF_BERSERKER_ATTACK_SPEED.get();
        }

        private static double getLifestealAmount() {
            return Config.SOUL_OF_BERSERKER_LIFESTEAL.get();
        }

        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            if (++tickCounter < 10) return;
            tickCounter = 0;

            if (!checkConfigEnabledStatus()) {
                removeAttributes(player);
                return;
            }

            boolean hasCurio = top.theillusivec4.curios.api.CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Berserker_Provider, player)
                    .isPresent();

            if (hasCurio) {
                applyAttributes(player);
            } else {
                removeAttributes(player);
            }
        }

        @SubscribeEvent
        public static void onKnockback(LivingKnockBackEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;

            boolean hasCurio = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Berserker_Provider, player)
                    .isPresent();

            if (hasCurio && checkConfigEnabledStatus()) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onMeleeHit(LivingDamageEvent.Pre event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
            if (!event.getSource().is(DamageTypeTags.IS_PLAYER_ATTACK)) return;

            boolean hasCurio = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Berserker_Provider, player)
                    .isPresent();

            if (hasCurio && checkConfigEnabledStatus()) {
                float healAmount = Math.max(1.0f, event.getOriginalDamage() * (float)getLifestealAmount());
                player.heal(healAmount);
            }
        }

        private static void applyAttributes(ServerPlayer player) {
            var damageAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            if (damageAttr != null && damageAttr.getModifier(MELEE_DAMAGE_ID) == null) {
                damageAttr.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        MELEE_DAMAGE_ID, getMeleeDamageBoost(), net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }

            var speedAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);
            if (speedAttr != null && speedAttr.getModifier(ATTACK_SPEED_ID) == null) {
                speedAttr.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        ATTACK_SPEED_ID, getAttackSpeedBoost(), net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
        }

        private static void removeAttributes(ServerPlayer player) {
            var damageAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            if (damageAttr != null) damageAttr.removeModifier(MELEE_DAMAGE_ID);

            var speedAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(ATTACK_SPEED_ID);
        }
    }

}
