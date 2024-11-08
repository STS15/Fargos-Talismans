package com.sts15.fargos.items.forces;

import java.util.List;

import com.sts15.fargos.init.Config;
import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.*;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Force_of_Explorer extends TalismanItem implements
        Glowstone_Talisman_Provider, Spectral_Talisman_Provider, Arctic_Talisman_Provider, Enderman_Talisman_Provider, Architect_Talisman_Provider
	{

    public Force_of_Explorer() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        int configValue1 = (Config.ARCHITECT_TALISMAN_REACH_DISTANCE.getAsInt());
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_explorer")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.architect_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.architect_talisman",configValue1))
                .withStyle(ChatFormatting.GOLD)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.enderman_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.enderman_talisman"))
                .withStyle(ChatFormatting.DARK_PURPLE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.arctic_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.arctic_talisman"))
                .withStyle(ChatFormatting.WHITE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.spectral_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.spectral_talisman"))
                .withStyle(ChatFormatting.GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.glowstone_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.glowstone_talisman"))
                .withStyle(ChatFormatting.YELLOW)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
