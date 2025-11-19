// com.sts15.fargos.items.tools.bows.ArrowHelpers.java
package com.sts15.fargos.items.tools.bows;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;

public final class ArrowHelpers {
    private ArrowHelpers() {}

    /** Create a new arrow that matches the base's ammo and weapon via ArrowItem#createArrow. */
    public static AbstractArrow makeArrowLikeBase(AbstractArrow base, LivingEntity shooter) {
        ItemStack ammo = base.getPickupItemStackOrigin();
        ItemStack weapon = base.getWeaponItem();
        if (!(ammo.getItem() instanceof ArrowItem arrowItem)) {
            return null;
        }
        return arrowItem.createArrow(base.level(), ammo, shooter, weapon);
    }

    /** Copy a few safe runtime properties from src -> dst. */
    public static void copyArrowProps(AbstractArrow src, AbstractArrow dst) {
        dst.setBaseDamage(src.getBaseDamage());
        dst.setCritArrow(src.isCritArrow());
        if (src.isOnFire()) {
            dst.setRemainingFireTicks(src.getRemainingFireTicks());
        }
        dst.pickup = src.pickup;
    }
}
