package com.sts15.fargos.block.pedestal;

import com.mojang.serialization.MapCodec;
import com.sts15.fargos.block.entity.BlockEntitiesInit;
import com.sts15.fargos.block.entity.pedestal.PedestalBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PedestalBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);

    public PedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        // Main description
        pTooltipComponents.add(Component.translatable("block.fargostalismans.tooltip.pedestal")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltipComponents.add(Component.translatable("block.fargostalismans.tooltip.pedestal2")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        // Newline or spacing
        pTooltipComponents.add(Component.literal(" ").setStyle(Style.EMPTY));

        // Default Radius (Gray text and gold number)
        pTooltipComponents.add(Component.literal("• ")
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_1")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_radius")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_1_blocks")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));

        // Amethyst Block Radius (Light purple block name, gold number)
        pTooltipComponents.add(Component.literal("• ")
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_2")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_radius")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_2_blocks")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));

        // Beacon Radius (Cyan block name, gold number)
        pTooltipComponents.add(Component.literal("• ")
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_3")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_radius")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_3_blocks")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));

        // Chorus Flower Radius (Purple block name, gold number)
        pTooltipComponents.add(Component.literal("• ")
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_4")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_radius")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)))
                .append(Component.translatable("block.fargostalismans.tooltip.pedestal_4_blocks")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));

        pTooltipComponents.add(Component.literal(" ").setStyle(Style.EMPTY));

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }



    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntitiesInit.PEDESTAL_BE.get(), PedestalBlockEntity::tickEntity);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                            BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()) {
            if(pLevel.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestalBlockEntity) {
                Containers.dropContents(pLevel, pPos, pedestalBlockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestalBlockEntity) {
            if(pedestalBlockEntity.isEmpty() && !pStack.isEmpty()) {
                pedestalBlockEntity.setItem(0, pStack);
                pStack.shrink(1);
                pLevel.playSound(pPlayer, pPos, SoundEvents.APPLY_EFFECT_RAID_OMEN, SoundSource.BLOCKS, 1f, 2f);
                if (!pLevel.isClientSide()) {
                    showBoundingBoxParticles(pLevel, pPos);
                }
            } else if(pStack.isEmpty()) {
                ItemStack stackOnPedestal = pedestalBlockEntity.getItem(0);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal);
                pedestalBlockEntity.clearContent();
                pLevel.playSound(pPlayer, pPos, SoundEvents.APPLY_EFFECT_BAD_OMEN, SoundSource.BLOCKS, 1f, 1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    private void showBoundingBoxParticles(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            BlockPos blockBelow = pos.below();
            BlockState blockStateBelow = level.getBlockState(blockBelow);

            double range = 5.0;
            if (blockStateBelow.is(Blocks.AMETHYST_BLOCK)) {
                range = 9.0;
            } else if (blockStateBelow.is(Blocks.BEACON)) {
                range = 15.0;
            } else if (blockStateBelow.is(Blocks.CHORUS_FLOWER)) {
                range = 20.0;
            }

            AABB boundingBox = new AABB(pos).inflate(range);

            double minX = boundingBox.minX;
            double maxX = boundingBox.maxX;
            double minY = boundingBox.minY;
            double maxY = boundingBox.maxY;
            double minZ = boundingBox.minZ;
            double maxZ = boundingBox.maxZ;
            double spacing = 0.5;

            for (double x = minX; x <= maxX; x += spacing) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, minY, minZ, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, minY, maxZ, 1, 0, 0, 0, 0);
            }

            for (double z = minZ; z <= maxZ; z += spacing) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, minX, minY, z, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, maxX, minY, z, 1, 0, 0, 0, 0);
            }

            for (double y = minY; y <= maxY; y += spacing) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, minX, y, minZ, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, maxX, y, minZ, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, minX, y, maxZ, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, maxX, y, maxZ, 1, 0, 0, 0, 0);
            }

            for (double x = minX; x <= maxX; x += spacing) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, maxY, minZ, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, maxY, maxZ, 1, 0, 0, 0, 0);
            }

            for (double z = minZ; z <= maxZ; z += spacing) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, minX, maxY, z, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, maxX, maxY, z, 1, 0, 0, 0, 0);
            }
        }
    }
}