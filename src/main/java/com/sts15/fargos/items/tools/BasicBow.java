package com.sts15.fargos.items.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public abstract class BasicBow extends BowItem {

    private final float drawSpeed; // custom multiplier
    private final float range;     // custom arrow range modifier

    public BasicBow(float drawSpeed, float range, Rarity rarity, int durability) {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(durability)
                .rarity(rarity));
        this.drawSpeed = drawSpeed;
        this.range = range;
    }

    public float getDrawSpeed() {
        return drawSpeed;
    }

    public float getRange() {
        return range;
    }

    public abstract void onArrowFired(Level level, LivingEntity shooter, AbstractArrow baseArrow, float power);
}
