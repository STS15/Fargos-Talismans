package com.sts15.fargos.blocks;

import java.util.function.Supplier;

import com.sts15.fargos.Fargos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK.key(), Fargos.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM.key(), Fargos.MODID);

//    public static final DeferredHolder<Block, CrucibleOfTheCosmosBlock> CRUCIBLE_OF_THE_COSMOS = registerBlock("crucible_of_the_cosmos",
//            () -> new CrucibleOfTheCosmosBlock(BlockBehaviour.Properties.of().strength(5.0f, 6.0f)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> toReturn) {
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
    }
}
