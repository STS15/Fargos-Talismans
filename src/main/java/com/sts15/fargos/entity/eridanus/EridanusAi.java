package com.sts15.fargos.entity.eridanus;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.sts15.fargos.entity.goals.eridanus.EridanusAttackBehavior;
import com.sts15.fargos.entity.goals.eridanus.EridanusWanderBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;

import java.util.List;

public class EridanusAi {

    public static Brain<EridanusBoss> makeBrain(EridanusBoss eridanus, Dynamic<?> dynamic) {
        Brain<EridanusBoss> brain = Brain.codec(getMemories(), getSensors()).parse(dynamic)
                .getOrThrow();

        initBrain(brain);
        return brain;
    }

    private static void initBrain(Brain<EridanusBoss> brain) {
        brain.setSchedule(Schedule.EMPTY);

        // Core: always running utilities (looking / moving to walk target)
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));

        // Idle: wander when no attack target
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                new EridanusWanderBehavior()
        ));
        brain.setDefaultActivity(Activity.IDLE);

        // Fight: when attack target exists, try to attack
        brain.addActivity(Activity.FIGHT, 10, ImmutableList.of(
                new EridanusAttackBehavior()
        ));

        // Start with idle if possible
        brain.setActiveActivityIfPossible(Activity.IDLE);
    }

    public static void tick(EridanusBoss boss) {
        ServerLevel level = (ServerLevel) boss.level();
        Brain<EridanusBoss> brain = boss.getBrain();
        brain.tick(level, boss);

        // Switch activity based on presence of attack target
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            brain.setActiveActivityIfPossible(Activity.FIGHT);
        } else {
            brain.setActiveActivityIfPossible(Activity.IDLE);
        }
    }

    public static List<MemoryModuleType<?>> getMemories() {
        return List.of(
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY
        );
    }

    public static List<SensorType<? extends Sensor<? super EridanusBoss>>> getSensors() {
        return List.of(
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.NEAREST_PLAYERS,
                SensorType.HURT_BY
        );
    }
}
