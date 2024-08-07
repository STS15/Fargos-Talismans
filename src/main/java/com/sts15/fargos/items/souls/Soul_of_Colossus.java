package com.sts15.fargos.items.souls;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Soul_of_Colossus extends TalismanItem implements Soul_of_Colossus_Provider {

    public Soul_of_Colossus() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.soul_of_colossus")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
