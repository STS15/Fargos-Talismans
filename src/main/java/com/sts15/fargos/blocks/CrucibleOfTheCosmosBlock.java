package com.sts15.fargos.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CrucibleOfTheCosmosBlock extends Block {
    public CrucibleOfTheCosmosBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
    	if (!pLevel.isClientSide) {
    		//pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
        }
        return ItemInteractionResult.SUCCESS;
    }

//    @Nullable
//    @Override
//    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
//        return new SimpleMenuProvider(
//                (id, inventory, player) -> new CustomCraftingTableMenu(id, inventory, ContainerLevelAccess.create(level, pos)),
//                Component.translatable("container.custom_crafting")
//        );
//    }
}
