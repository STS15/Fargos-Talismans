package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

public class EridanusWanderBehavior extends OneShot<EridanusBoss> {
    private int cooldown = 0;

    public EridanusWanderBehavior() {
        // OneShot has no constructor args here; trigger controls execution.
    }

    @Override
    public boolean trigger(ServerLevel level, EridanusBoss boss, long gameTime) {
        // If boss has an attack target, skip wandering.
        if (boss.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        }

        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        // Pick a random nearby offset
        RandomSource rand = boss.getRandom();
        double dx = (rand.nextDouble() * 2 - 1) * 6; // radius ~6
        double dy = (rand.nextDouble() * 2 - 1) * 2; // small vertical wiggle
        double dz = (rand.nextDouble() * 2 - 1) * 6;
        Vec3 targetPos = boss.position().add(dx, dy, dz);

        // Set walk target so MoveToTargetSink handles navigation
        boss.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0f, 1));

        // Reset cooldown (100-160 ticks)
        cooldown = 100 + rand.nextInt(60);
        return true;
    }
}