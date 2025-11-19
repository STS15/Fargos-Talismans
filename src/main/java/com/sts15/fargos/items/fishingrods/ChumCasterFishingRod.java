package com.sts15.fargos.items.fishingrods;

import com.sts15.fargos.items.tools.BasicFishingRod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class ChumCasterFishingRod extends BasicFishingRod {

    public ChumCasterFishingRod(Item.Properties props) {
        super(props, 25, false,
                Component.translatable("item.fargostalismans.chum_note"));
    }
}
