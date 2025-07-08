package com.sts15.fargos.init;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.entity.attacks.death_sickle.DeathSickleEntity;
import com.sts15.fargos.entity.attacks.targetLocked.TargetLockedEntity;
import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Fargos.MODID);

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<EntityType<?>, EntityType<EridanusBoss>> ERIDANUS_BOSS = ENTITIES.register("eridanus",
            () -> EntityType.Builder.<EridanusBoss>of(EridanusBoss::new, MobCategory.MONSTER)
                    .sized(1f, 3f)
                    .clientTrackingRange(64)
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "eridanus").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<TargetLockedEntity>> TARGET_LOCKED = ENTITIES.register("target_locked",
            () -> EntityType.Builder.<TargetLockedEntity>of(TargetLockedEntity::new, MobCategory.MONSTER)
                    .sized(0.1f, 0.1f)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSummon()
                    .noSave()
                    .build(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "target_locked").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DeathSickleEntity>> DEATH_SICKLE = ENTITIES.register("death_sickle",
            () -> EntityType.Builder.<DeathSickleEntity>of(DeathSickleEntity::new, MobCategory.MONSTER)
                    .sized(1.7f, 1.0f)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .noSummon()
                    .noSave()
                    .build(ResourceLocation.fromNamespaceAndPath(Fargos.MODID, "death_sickle").toString()));

}

