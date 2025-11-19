package com.sts15.fargos.items.fishingrods;

import com.sts15.fargos.items.tools.BasicFishingRod;
import net.minecraft.world.item.Item;

public class FiberglassFishingRod extends BasicFishingRod {

    private final boolean modifiesBiteSpeed = true;

    public FiberglassFishingRod(Item.Properties props) {
        super(props, 30, false, null);
    }

    public boolean doesModifyBiteSpeed() {
        return modifiesBiteSpeed;
    }

    public float getBiteSpeedMultiplier() {
        return (getFishingPower() / 10.0f) - 1.0f;
    }
}
