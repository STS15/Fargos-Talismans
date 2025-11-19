package com.sts15.fargos.mixins;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Accessor("PIERCE_LEVEL")
    EntityDataAccessor<Byte> getPierceLevelAccessor();

    @Invoker("setPierceLevel")
    void callSetPierceLevel(byte level);
}
