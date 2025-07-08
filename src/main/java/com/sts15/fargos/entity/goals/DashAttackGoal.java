package com.sts15.fargos.entity.goals;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animation.AnimationController;
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
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        target = boss.getTarget();
        if (target == null || !target.isAlive() || boss.isPhaseTransitioning())
            return false;
        return !(target instanceof Player p) || (!p.isCreative() && !p.isSpectator());
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || !target.isAlive() || boss.isPhaseTransitioning())
            return false;
        return !(target instanceof Player p) || (!p.isCreative() && !p.isSpectator());
    }

    @Override
    public void start() {
        System.out.println("[DashAttackGoal] start() called");

        this.dashing = true;
        this.dashTicks = 0;

        if (target != null) {
            Vec3 toTarget = target.position().subtract(boss.position());
            float yaw = (float)(Math.toDegrees(Math.atan2(toTarget.z, toTarget.x))) - 90f;
            boss.setYRot(yaw);
            boss.yHeadRot = yaw;
            boss.yBodyRot = yaw;

            boss.playAttackAnimation();
        }
    }


    @Override
    public void tick() {
        if (!dashing) return;

        dashTicks++;
        Vec3 dir = boss.getLookAngle().normalize().scale(0.6);
        boss.setDeltaMovement(dir.x, boss.getDeltaMovement().y, dir.z);
        boss.hasImpulse = true;

        System.out.println("[DashAttackGoal] Dashing... tick=" + dashTicks);

        if (boss.distanceTo(target) < 2.5f) {
            System.out.println("[DashAttackGoal] Hit target!");
            boss.doHurtTarget(target);
        }

        if (dashTicks >= 15) {
            System.out.println("[DashAttackGoal] Dash complete.");
            stop();
        }
    }

    @Override
    public void stop() {
        System.out.println("[DashAttackGoal] stop() called");
        boss.setDeltaMovement(Vec3.ZERO);
        boss.hasImpulse = true;
        dashing = false;
        cooldown = 5;
        dashTicks = 0;
        target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
