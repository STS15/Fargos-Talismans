package com.sts15.fargos.items.forces;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.talismans.*;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Force_of_Rejectors extends TalismanItem implements 
	Dragon_Talisman_Provider, Wither_Talisman_Provider, Zombie_Talisman_Provider, Skeleton_Talisman_Provider, Blaze_Talisman_Provider, Ghast_Talisman_Provider, Creeper_Talisman_Provider, Vindicator_Talisman_Provider
	{

    public Force_of_Rejectors() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_rejectors")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.dragon_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.dragon_talisman"))
                .withStyle(ChatFormatting.LIGHT_PURPLE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.wither_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.wither_talisman"))
                .withStyle(ChatFormatting.DARK_GRAY)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.zombie_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.zombie_talisman"))
                .withStyle(ChatFormatting.DARK_GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.skeleton_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.skeleton_talisman"))
                .withStyle(ChatFormatting.WHITE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.blaze_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.blaze_talisman"))
                .withStyle(ChatFormatting.YELLOW)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.ghast_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.ghast_talisman"))
                .withStyle(ChatFormatting.WHITE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.creeper_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.creeper_talisman"))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.vindicator_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.vindicator_talisman"))
                .withStyle(ChatFormatting.GRAY)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
