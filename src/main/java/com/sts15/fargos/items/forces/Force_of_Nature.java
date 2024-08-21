package com.sts15.fargos.items.forces;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;
import com.sts15.fargos.items.providers.Earth_Talisman_Provider;
import com.sts15.fargos.items.providers.Water_Talisman_Provider;
import com.sts15.fargos.items.providers.Fire_Talisman_Provider;
import com.sts15.fargos.items.providers.Air_Talisman_Provider;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Force_of_Nature extends TalismanItem implements Earth_Talisman_Provider, Water_Talisman_Provider, Fire_Talisman_Provider, Air_Talisman_Provider {

    public Force_of_Nature() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.force_of_nature")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.earth_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.earth_talisman"))
                .withStyle(ChatFormatting.GREEN)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.water_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.water_talisman"))
                .withStyle(ChatFormatting.BLUE)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.fire_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.fire_talisman"))
                .withStyle(ChatFormatting.RED)
        );
        pTooltipComponents.add(Component.literal("- ")
        		.append(Component.translatable("item.fargostalismans.tooltip.air_talisman.title"))
                .append(Component.literal(": "))
                .append(Component.translatable("item.fargostalismans.tooltip.air_talisman"))
                .withStyle(ChatFormatting.WHITE)
        );
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
