package com.sts15.fargos.blocks;

import com.sts15.fargos.Fargos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Fargos.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Fargos.MODID);

    public static final DeferredHolder<Block, Block> CRUCIBLE_OF_THE_COSMOS = BLOCKS.register("crucible_of_the_cosmos", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> NETHERITE_ANVIL = BLOCKS.register("netherite_anvil", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

        for (DeferredHolder<? extends Block, ? extends Block> block : BLOCKS.getEntries()) {
            ITEMS.register(block.getId().getPath(), () -> new BlockItemBase(block.get(), new Item.Properties().stacksTo(1)));
        }

        ITEMS.register(eventBus);
    }
}
