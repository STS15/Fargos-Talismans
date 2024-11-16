package com.sts15.fargos.block;

import com.sts15.fargos.Fargos;
import com.sts15.fargos.block.crucibleofthecosmos.CrucibleOfTheCosmosBlock;
import com.sts15.fargos.block.pedestal.PedestalBlock;
import com.sts15.fargos.items.ItemInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlocksInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Fargos.MODID);

    public static final DeferredBlock<Block> PEDESTAL = registerBlock("pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> CRUCIBLE_OF_THE_COSMOS = registerBlock("crucible_of_the_cosmos", CrucibleOfTheCosmosBlock::new);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}