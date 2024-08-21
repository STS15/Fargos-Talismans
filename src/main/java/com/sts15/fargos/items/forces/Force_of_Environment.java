package com.sts15.fargos.items.forces;

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
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_negative")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.sun_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.sun_talisman"))
                .withStyle(ChatFormatting.YELLOW)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.true_sun_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.true_sun_talisman"))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.storm_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.storm_talisman"))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.rain_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.rain_talisman"))
                .withStyle(ChatFormatting.AQUA)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.snowy_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.snowy_talisman"))
                .withStyle(ChatFormatting.WHITE)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.day_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.day_talisman"))
                .withStyle(ChatFormatting.BLUE)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.night_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.night_talisman"))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.full_moon_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.full_moon_talisman"))
                .withStyle(ChatFormatting.GRAY)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
