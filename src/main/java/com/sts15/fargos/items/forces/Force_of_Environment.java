package com.sts15.fargos.items.forces;

import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Force_of_Environment extends TalismanItem implements
        Day_Talisman_Provider, Snowy_Talisman_Provider, True_Sun_Talisman_Provider, Storm_Talisman_Provider,
        Rain_Talisman_Provider, Night_Talisman_Provider, Full_Moon_Talisman_Provider, Sun_Talisman_Provider
{

    public Force_of_Environment() {
        super(new Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        int configValue1 = (int) (Config.SUN_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue2 = (int) (Config.TRUE_SUN_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue3 = (int) (Config.STORM_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue4 = (int) (Config.RAIN_TALISMAN_INCREASED_SPEED.getAsDouble() * 100);
        int configValue5 = (int) (Config.SNOWY_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        int configValue6 = (int) (Config.DAY_TALISMAN_ADDITIONAL_DAMAGE.getAsDouble() * 100);
        int configValue7 = (int) (Config.NIGHT_TALISMAN_INCREASED_ARMOR.getAsDouble() * 100);
        int configValue8 = (int) (Config.FULL_MOON_TALISMAN_INCREASED_DAMAGE.getAsDouble() * 100);
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_negative")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.sun_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.sun_talisman",configValue1))
                .withStyle(ChatFormatting.YELLOW)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.true_sun_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.true_sun_talisman",configValue2))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.storm_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.storm_talisman",configValue3))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.rain_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.rain_talisman",configValue4))
                .withStyle(ChatFormatting.AQUA)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.snowy_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.snowy_talisman",configValue5))
                .withStyle(ChatFormatting.WHITE)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.day_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.day_talisman",configValue6))
                .withStyle(ChatFormatting.BLUE)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.night_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.night_talisman",configValue7))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.full_moon_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.full_moon_talisman",configValue8))
                .withStyle(ChatFormatting.GRAY)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
