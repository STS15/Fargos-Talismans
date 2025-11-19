package com.sts15.fargos.items.fishingrods;

import com.sts15.fargos.items.tools.BasicFishingRod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class HotlineFishingRod extends BasicFishingRod {

    public HotlineFishingRod(Item.Properties props) {
        super(props, 45, true,
                Component.translatable("item.fargostalismans.hotline_note"));
    }
}
