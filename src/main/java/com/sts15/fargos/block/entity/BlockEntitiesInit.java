package com.sts15.fargos.block.entity;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.block.BlocksInit;
import com.sts15.fargos.block.entity.crucibleofthecosmos.CrucibleOfTheCosmosBlockEntity;
import com.sts15.fargos.block.entity.pedestal.PedestalBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntitiesInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Fargos.MODID);

    public static final Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITIES.register("pedestal_be", () -> BlockEntityType.Builder.of(
                    PedestalBlockEntity::new, BlocksInit.PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<CrucibleOfTheCosmosBlockEntity>> COTC_BE =
            BLOCK_ENTITIES.register("cotc_be", () -> BlockEntityType.Builder.of(
                    CrucibleOfTheCosmosBlockEntity::new, BlocksInit.CRUCIBLE_OF_THE_COSMOS.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}