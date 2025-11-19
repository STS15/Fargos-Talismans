package com.sts15.fargos.items.souls;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.compat.IronsCompat;
import com.sts15.fargos.items.providers.Soul_of_Conjurist_Provider;
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

public class Soul_of_Conjurist extends TalismanItem implements ICurioItem, Soul_of_Conjurist_Provider {

    public static final String talismanName = "soul_of_conjurist";

    public Soul_of_Conjurist() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip." + talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        double maxMana = Config.SOUL_OF_CONJURIST_MAX_MANA_ADDITION.get();
        double castTime = Config.SOUL_OF_CONJURIST_CAST_TIME_REDUCTION_MULTIPLIER.get() * 100;
        double resist = Config.SOUL_OF_CONJURIST_SPELL_RESIST_MULTIPLIER.get() * 100;
        double spellPower = Config.SOUL_OF_CONJURIST_SPELL_POWER_MULTIPLIER.get() * 100;
        double summon = Config.SOUL_OF_CONJURIST_SUMMON_DAMAGE_MULTIPLIER.get() * 100;
        double cooldown = Config.SOUL_OF_CONJURIST_COOLDOWN_REDUCTION_MULTIPLIER.get() * 100;

        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("When worn as talisman:")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f Max Mana ", maxMana)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f]", maxMana)).withStyle(ChatFormatting.GRAY)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f%% Cast Speed ", castTime)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f%%]", castTime)).withStyle(ChatFormatting.GRAY)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f%% Spell Resistance ", resist)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f%%]", resist)).withStyle(ChatFormatting.GRAY)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f%% Spell Power ", spellPower)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f%%]", spellPower)).withStyle(ChatFormatting.GRAY)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f%% Summon Damage ", summon)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f%%]", summon)).withStyle(ChatFormatting.GRAY)));

        tooltipComponents.add(Component.empty()
                .append(Component.literal(String.format("+%.0f%% Cooldown Reduction ", cooldown)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(String.format("[%.0f%%]", cooldown)).withStyle(ChatFormatting.GRAY)));

        if (!checkConfigEnabledStatus()) {
            tooltipComponents.add(Component.translatable("config.fargostalismans.tooltip.disabled")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static boolean checkConfigEnabledStatus() {
        try {
            String fieldName = talismanName.toUpperCase() + "_TOGGLE";
            Field toggleField = Config.class.getField(fieldName);
            return ((ModConfigSpec.BooleanValue) toggleField.get(null)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return true;
        }
    }

    public static boolean checkIronsLoaded() {
        return ModList.get().isLoaded("irons_spellbooks");
    }

    public static void tryApplyIronsBonuses(Player player) {
        if (!checkIronsLoaded()) return;
        try {
            IronsCompat.applyBonuses(player);
        } catch (Throwable ignored) {}
    }

    public static void tryRemoveIronsBonuses(Player player) {
        if (!checkIronsLoaded()) return;
        try {
            IronsCompat.removeBonuses(player);
        } catch (Throwable ignored) {}
    }

    @EventBusSubscriber(modid = Fargos.MODID)
    public static class Events {

        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            if (!Soul_of_Conjurist.checkConfigEnabledStatus()) {
                Soul_of_Conjurist.tryRemoveIronsBonuses(player);
                return;
            }

            boolean hasCurio = CuriosApi.getCuriosHelper()
                    .findEquippedCurio(stack -> stack.getItem() instanceof Soul_of_Conjurist_Provider, player)
                    .isPresent();

            if (++tickCounter < 10) return;
            tickCounter = 0;

            if (hasCurio && Soul_of_Conjurist.checkIronsLoaded()) {
                Soul_of_Conjurist.tryApplyIronsBonuses(player);
            } else {
                Soul_of_Conjurist.tryRemoveIronsBonuses(player);
            }
        }
    }
}
