package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.compat.ArsCompat;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.lang.reflect.Field;
import java.util.List;

public class Soul_of_Arch_Wizard extends TalismanItem implements ICurioItem, Soul_of_Arch_Wizard_Provider {

    public static final String talismanName = "soul_of_arch_wizard";
    private static final String ARS_MODID = "ars_nouveau";

    public Soul_of_Arch_Wizard() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        if (!checkArsLoaded()) {
            tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.requires_ars") // add this to your lang: "Requires Ars Nouveau to function."
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }

        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }

        double spellDamage = Config.SOUL_OF_ARCH_WIZARD_SPELL_DAMAGE_MULTIPLIER.get();
        double manaRegen = Config.SOUL_OF_ARCH_WIZARD_MANA_REGEN_MULTIPLIER.get();
        double maxMana = Config.SOUL_OF_ARCH_WIZARD_MAX_MANA_ADDITION.get();
        tooltipComponents.add(Component.literal("").setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)));
        tooltipComponents.add(Component.literal("When worn as talisman:").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
        tooltipComponents.add(Component.empty().append(Component.literal(String.format("+%.0f Max Mana ", maxMana)).withStyle(ChatFormatting.BLUE)).append(Component.literal(String.format("[%.0f]", maxMana)).withStyle(ChatFormatting.GRAY)));
        tooltipComponents.add(Component.empty().append(Component.literal(String.format("+%.0f%% Mana Regen ", manaRegen * 100)).withStyle(ChatFormatting.BLUE)).append(Component.literal(String.format("[%.0f%%]", manaRegen * 100)).withStyle(ChatFormatting.GRAY)));
        tooltipComponents.add(Component.empty().append(Component.literal(String.format("+%.0f%% Spell Damage ", spellDamage * 100)).withStyle(ChatFormatting.BLUE)).append(Component.literal(String.format("[%.0f%%]", spellDamage * 100)).withStyle(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        boolean isEnabled = true;
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            isEnabled = ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        return isEnabled;
    }

    public static boolean checkArsLoaded() {
        return ModList.get().isLoaded(ARS_MODID);
    }

    public static void tryApplyArsBonuses(Player player) {
        if (!checkArsLoaded()) return;
        try {
            ArsCompat.applyBonuses(player);
        } catch (Throwable ignored) {}
    }

    public static void tryRemoveArsBonuses(Player player) {
        if (!checkArsLoaded()) return;
        try {
            ArsCompat.removeBonuses(player);
        } catch (Throwable ignored) {}
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            if ((player.tickCount % 10) != 0) return;

            if (!Soul_of_Arch_Wizard.checkConfigEnabledStatus()) {
                Soul_of_Arch_Wizard.tryRemoveArsBonuses(player);
                return;
            }

            boolean hasCurio = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Arch_Wizard_Provider, player)
                    .isPresent();

            if (hasCurio && Soul_of_Arch_Wizard.checkArsLoaded()) {
                Soul_of_Arch_Wizard.tryApplyArsBonuses(player);
            } else {
                Soul_of_Arch_Wizard.tryRemoveArsBonuses(player);
            }
        }
    }
}
