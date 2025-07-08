package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.attacks.targetLocked.TargetLockedEntity;
import com.sts15.fargos.entity.eridanus.EridanusBoss;
import com.sts15.fargos.init.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class SummonTargetLockedGoal extends Goal {
    private final EridanusBoss eridanus;
    private Player target;
    private final double range;
    private int spawnCount;
    private int spawnTickCounter;
    private int cooldownTickCounter;
    private static final int SPAWN_INTERVAL_TICKS = 10;
    private static final int MAX_SPAWNS = 4;
    private static final int COOLDOWN_TICKS = 100;

    public SummonTargetLockedGoal(EridanusBoss eridanus, double range) {
        this.eridanus = eridanus;
        this.range = range;
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.spawnCount = 0;
        this.spawnTickCounter = 0;
        this.cooldownTickCounter = COOLDOWN_TICKS;
    }

    @Override
    public boolean canUse() {
        var maybeTarget = eridanus.getTarget();
        if (!(maybeTarget instanceof Player p)) return false;
        this.target = (Player) this.eridanus.getTarget();
        if (this.target == null) {
            return false;
        }
        double distanceToTargetSquared = this.eridanus.distanceToSqr(this.target);
        return distanceToTargetSquared <= this.range * this.range;
    }

    @Override
    public void start() {
        super.start();
        this.spawnCount = 0;
        this.spawnTickCounter = 0;
    }

    @Override
    public void tick() {
        if (this.spawnCount < MAX_SPAWNS) {
            this.spawnTickCounter++;
            if (this.spawnTickCounter >= SPAWN_INTERVAL_TICKS) {
                BlockPos targetPosition = this.target.blockPosition();
                summonTargetLocked(targetPosition);
                this.spawnTickCounter = 0;
                this.spawnCount++;
            }
        } else {
            this.cooldownTickCounter = 0;
            this.stop();
        }
    }

    private void summonTargetLocked(BlockPos position) {
        if (this.eridanus.level() instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) this.eridanus.level();
            TargetLockedEntity targetLockedEntity = new TargetLockedEntity(EntityRegistry.TARGET_LOCKED.get(), serverLevel);
            targetLockedEntity.setPos(position.getX(), position.getY(), position.getZ());
            serverLevel.addFreshEntity(targetLockedEntity);
        }
    }
}
