package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class VortexBeamAttackGoal extends Goal {
    private final EridanusBoss eridanus;
    private final double range;
    private int tickCount;
    private int cooldown;

    private static final int BEAM_DURATION = 40;
    private static final int COOLDOWN_DURATION = 100;
    private static final float DAMAGE = 3.0f;

    public VortexBeamAttackGoal(EridanusBoss eridanus, double range) {
        this.eridanus = eridanus;
        this.range = range;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        return eridanus.getTarget() instanceof Player player && eridanus.distanceToSqr(player) <= range * range;
    }

    @Override
    public void start() {
        tickCount = 0;
    }

    @Override
    public void tick() {
        tickCount++;
        shootBeam();

        if (tickCount >= BEAM_DURATION) {
            cooldown = COOLDOWN_DURATION;
            stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return tickCount < BEAM_DURATION;
    }

    private void shootBeam() {
        Level level = eridanus.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 lookVec = eridanus.getLookAngle().normalize();
        Vec3 start = eridanus.position().subtract(lookVec.scale(4));
        Vec3 end = start.add(lookVec.scale(range * 2));

        // Beam visual trigger
        //eridanus.setBeam(start, end);

        // Damage logic
        AABB beamBox = new AABB(start, end).inflate(1.0);
        List<LivingEntity> hitEntities = level.getEntitiesOfClass(LivingEntity.class, beamBox, e ->
                e != eridanus && e.isAlive() && e.canBeCollidedWith());

        for (LivingEntity entity : hitEntities) {
            Vec3 hitVec = entity.getBoundingBox().getCenter();
            double dot = lookVec.dot(hitVec.subtract(start).normalize());
            if (dot > 0.95) {
                //entity.hurt(DamageSource.ON_FIRE, DAMAGE);
            }
        }
    }

}