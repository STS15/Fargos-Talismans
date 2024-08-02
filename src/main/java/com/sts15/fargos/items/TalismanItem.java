package com.sts15.fargos.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public abstract class TalismanItem extends Item {
    public TalismanItem(Item.Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

}
