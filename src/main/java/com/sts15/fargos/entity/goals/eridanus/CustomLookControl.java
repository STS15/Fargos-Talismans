package com.sts15.fargos.entity.goals.eridanus;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class CustomLookControl extends LookControl {
    private final Mob mob;

    public CustomLookControl(Mob entity) {
        super(entity);
        this.mob = entity;
    }

    @Override
    public void tick() {
        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;
            this.getYRotD().ifPresent(yRot -> {
                this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, yRot, 10);
            });
        } else {
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10);
        }
    }

    protected float rotateTowards(float currentAngle, float targetAngle, float maxTurn) {
        float angleDifference = Mth.degreesDifference(currentAngle, targetAngle);
        return Mth.approachDegrees(currentAngle, targetAngle, maxTurn);
    }

}