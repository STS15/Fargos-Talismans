package com.sts15.fargos.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class CustomFlyingMoveControl extends MoveControl {
    private final Mob entity;
    private final double speed;

    public CustomFlyingMoveControl(Mob entity, double speed) {
        super(entity);
        this.entity = entity;
        this.speed = speed;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            Vec3 targetPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
            double distance = targetPos.distanceTo(entity.position());
            if (distance < 0.1) {
                this.entity.setDeltaMovement(Vec3.ZERO);
                this.operation = Operation.WAIT;
                return;
            }
            Vec3 dir = targetPos.subtract(entity.position()).normalize();
            Vec3 velocity = dir.scale(speed);
            float newYaw = (float) (net.minecraft.util.Mth.atan2(velocity.z, velocity.x) * (180F / Math.PI)) - 90.0F;
            this.entity.setYRot(this.rotlerp(this.entity.getYRot(), newYaw, 10)); // Smoothly interpolate the rotation
            this.entity.setPos(this.entity.getX() + velocity.x, this.entity.getY() + velocity.y, this.entity.getZ() + velocity.z);
        }
    }
}
