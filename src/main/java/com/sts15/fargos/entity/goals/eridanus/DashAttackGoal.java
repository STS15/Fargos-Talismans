package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DashAttackGoal extends Goal {
    private final EridanusBoss boss;
    private LivingEntity target;
    private int cooldown = 0;
    private boolean dashing = false;
    private int dashTicks = 0;

    public DashAttackGoal(EridanusBoss boss) {
        this.boss = boss;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        this.dashing = true;
        this.dashTicks = 0;
        boss.setDashing(true);
        boss.playAttackAnimation(); // fallback so actionController sees it too

        if (target != null) {
            Vec3 toTarget = target.position().subtract(boss.position());
            float yaw = (float)(Math.toDegrees(Math.atan2(toTarget.z, toTarget.x))) - 90f;
            boss.setYRot(yaw);
            boss.yHeadRot = yaw;
            boss.yBodyRot = yaw;
        }
    }

    @Override
    public void tick() {
        if (!dashing || target == null) return;

        dashTicks++;
        Vec3 dir = boss.getLookAngle().normalize().scale(0.6);
        boss.setDeltaMovement(dir.x, boss.getDeltaMovement().y, dir.z);
        boss.hasImpulse = true;

        if (boss.distanceTo(target) < 2.5f) {
            boss.doHurtTarget(target);
        }

        if (dashTicks >= 15) {
            stop();
        }
    }

    @Override
    public void stop() {
        boss.setDeltaMovement(Vec3.ZERO);
        boss.hasImpulse = true;
        dashing = false;
        boss.setDashing(false);
        cooldown = 5;
        dashTicks = 0;
        target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
