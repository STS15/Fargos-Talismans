package com.sts15.fargos.items.talismans;

import java.util.List;

import com.sts15.fargos.items.TalismanItem;

import com.sts15.fargos.items.providers.Spectral_Talisman_Provider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class Spectral_Talisman extends TalismanItem implements Spectral_Talisman_Provider {

    private static final String talismanName = "spectral_talisman";

    public Spectral_Talisman() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("item.fargostalismans.tooltip."+talismanName)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
