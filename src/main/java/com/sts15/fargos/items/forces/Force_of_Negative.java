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

public class Force_of_Negative extends TalismanItem implements
        Fired_Talisman_Provider, Poisoned_Talisman_Provider, Withered_Talisman_Provider, Blinded_Talisman_Provider, Fatigued_Talisman_Provider,
        Slowed_Talisman_Provider, Nauseated_Talisman_Provider, Weakened_Talisman_Provider
{

    public Force_of_Negative() {
        super(new Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_negative")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.fired_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.fired_talisman"))
                .withStyle(ChatFormatting.DARK_RED)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.poisoned_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.poisoned_talisman"))
                .withStyle(ChatFormatting.DARK_GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.withered_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.withered_talisman"))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.blinded_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.blinded_talisman"))
                .withStyle(ChatFormatting.BLACK)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.fatigued_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.fatigued_talisman"))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.slowed_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.slowed_talisman"))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.nauseated_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.nauseated_talisman"))
                .withStyle(ChatFormatting.YELLOW)
        );
        pTooltipComponents.add(Component.literal("- ")
                .append(Component.translatable("item.fargostalismans.tooltip.weakened_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.weakened_talisman"))
                .withStyle(ChatFormatting.DARK_AQUA)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
