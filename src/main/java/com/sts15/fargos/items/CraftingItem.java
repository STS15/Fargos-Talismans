package com.sts15.fargos.items;

import net.minecraft.world.item.Item;

public abstract class CraftingItem extends Item {
    public CraftingItem(Item.Properties properties) {
        super(properties.stacksTo(64));
    }

}
