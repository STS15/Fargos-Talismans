package com.sts15.fargos.items.fishingrods;

import com.sts15.fargos.items.tools.BasicFishingRod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class StyxCasterFishingRod extends BasicFishingRod {

    public StyxCasterFishingRod(Item.Properties props) {
        super(props, 55, true,
                Component.translatable("item.fargostalismans.styx_note"));
    }
}
