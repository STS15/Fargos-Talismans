package com.sts15.fargos.items.souls;

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

public class Soul_of_Dimensions extends TalismanItem implements 
	Earth_Talisman_Provider, Water_Talisman_Provider, Fire_Talisman_Provider, Air_Talisman_Provider, // Force of Nature
	Emerald_Talisman_Provider, Amethyst_Talisman_Provider, Gold_Talisman_Provider, Diamond_Talisman_Provider, Lapis_Talisman_Provider, Redstone_Talisman_Provider, // Force of Overworld
	Dragon_Talisman_Provider, Wither_Talisman_Provider, Zombie_Talisman_Provider, Skeleton_Talisman_Provider, Blaze_Talisman_Provider, Ghast_Talisman_Provider, Creeper_Talisman_Provider, Vindicator_Talisman_Provider, // Force of Rejectors
	Vampiric_Talisman_Provider, Enchanting_Talisman_Provider, Librarian_Talisman_Provider, Witch_Talisman_Provider, Shulker_Talisman_Provider, Undying_Talisman_Provider, // Force of Mystic
	Battle_Talisman_Provider, Iron_Golem_Talisman_Provider, Thorny_Talisman_Provider, Cactus_Talisman_Provider, Void_Talisman_Provider, // Force of Warrior
	Glowstone_Talisman_Provider, Spectral_Talisman_Provider, Arctic_Talisman_Provider, Enderman_Talisman_Provider, Architect_Talisman_Provider, // Force of Explorer
	Soul_of_Colossus_Provider, Soul_of_Flight_Mastery_Provider, Soul_of_Supersonic_Provider, Soul_of_Trawler_Provider // Additional Souls
	{

    public Soul_of_Dimensions() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.fargostalismans.tooltip.soul_of_dimensions")
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}