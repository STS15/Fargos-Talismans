package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class EridanusAttackBehavior extends OneShot<EridanusBoss> {

    @Override
    public boolean trigger(ServerLevel level, EridanusBoss boss, long gameTime) {
        Optional<LivingEntity> targetOpt = boss.getBrain()
                .getMemory(MemoryModuleType.ATTACK_TARGET)
                .filter(LivingEntity::isAlive);

        if (targetOpt.isEmpty()) return false;

        LivingEntity target = targetOpt.get();
        double reach = boss.getBbWidth() * 2.0 + target.getBbWidth();
        double distSqr = boss.distanceToSqr(target);

        if (distSqr <= reach * reach) {
            boss.doHurtTarget(target);
        } else {
            Vec3 direction = target.position().subtract(boss.position()).normalize().scale(0.4);
            boss.setDeltaMovement(direction);
            boss.getLookControl().setLookAt(target, 30.0f, 30.0f);
        }

        return true;
    }
}
