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

public class Force_of_Overworld extends TalismanItem implements
        Emerald_Talisman_Provider, Amethyst_Talisman_Provider, Gold_Talisman_Provider, Diamond_Talisman_Provider, Lapis_Talisman_Provider, Redstone_Talisman_Provider
	{

    public Force_of_Overworld() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        int configValue1 = (int) (Config.DIAMOND_TALISMAN_DAMAGE_REDUCTION.getAsDouble() * 100);
        int configValue2 = (int) (Config.EMERALD_TALISMAN_INCREASED_ILLAGER_DAMAGE.getAsDouble() * 100);
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_overworld")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.amethyst_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.amethyst_talisman"))
                .withStyle(ChatFormatting.DARK_PURPLE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.redstone_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.redstone_talisman"))
                .withStyle(ChatFormatting.RED)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.diamond_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.diamond_talisman",configValue1))
                .withStyle(ChatFormatting.AQUA)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.emerald_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.emerald_talisman",configValue2))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.lapis_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.lapis_talisman"))
                .withStyle(ChatFormatting.BLUE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.gold_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.gold_talisman"))
                .withStyle(ChatFormatting.GOLD)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
