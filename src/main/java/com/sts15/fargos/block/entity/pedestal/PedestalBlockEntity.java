package com.sts15.fargos.block.entity.pedestal;

import com.sts15.fargos.block.entity.BlockEntitiesInit;
import com.sts15.fargos.effect.EffectsInit;
import com.sts15.fargos.items.providers.*;
import com.sts15.fargos.items.talismans.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class PedestalBlockEntity extends BlockEntity implements Container {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private float rotation;
    private int tickCounter = 0;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesInit.PEDESTAL_BE.get(), pPos, pBlockState);
    }

    public List<Player> getPlayersInRange() {
        double range = 5.0;

        BlockPos blockBelow = this.worldPosition.below();
        BlockState blockStateBelow = level.getBlockState(blockBelow);

        if (blockStateBelow.is(Blocks.AMETHYST_BLOCK)) {
            range = 9.0;
        } else if (blockStateBelow.is(Blocks.BEACON)) {
            range = 15.0;
        } else if (blockStateBelow.is(Blocks.CHORUS_FLOWER)) {
            range = 20.0;
        }

        AABB aabb = new AABB(this.worldPosition).inflate(range);

        if (this.level instanceof ServerLevel serverLevel) {
            return serverLevel.getEntitiesOfClass(Player.class, aabb);
        }

        return List.of();
    }


    public void applyTalismanEffect() {
        ItemStack itemStack = this.getItem(0);

        List<Player> players = getPlayersInRange();
        for (Player player : players) {
            if (player instanceof ServerPlayer serverPlayer) {

                if (itemStack.getItem() instanceof Air_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.AIR_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Amethyst_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.AMETHYST_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Apple_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.APPLE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Architect_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.ARCHITECT_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Arctic_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.ARCTIC_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Battle_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.BATTLE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Blaze_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.BLAZE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Blinded_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.BLINDED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Cactus_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.CACTUS_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Copper_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.COPPER_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Creeper_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.CREEPER_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Day_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.DAY_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Diamond_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.DIAMOND_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Dragon_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.DRAGON_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Earth_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.EARTH_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Emerald_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.EMERALD_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Enchanting_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.ENCHANTING_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Enderman_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.ENDERMAN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Fatigued_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.FATIGUED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Fire_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.FIRE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Fired_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.FIRED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Full_Moon_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.FULL_MOON_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Ghast_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.GHAST_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Glowstone_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.GLOWSTONE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Gold_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.GOLD_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Iron_Golem_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.IRON_GOLEM_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Iron_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.IRON_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Lapis_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.LAPIS_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Librarian_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.LIBRARIAN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Mooshroom_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.MOOSHROOM_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Nauseated_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.NAUSEATED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Nether_Star_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.NETHER_STAR_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Night_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.NIGHT_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Obsidian_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.OBSIDIAN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Pickaxe_Talisman) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.PICKAXE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Poisoned_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.POISONED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Rain_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.RAIN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Redstone_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.REDSTONE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Shulker_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SHULKER_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Skeleton_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SKELETON_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Slowed_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SLOWED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Snowy_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SNOWY_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Spectral_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SPECTRAL_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Storm_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.STORM_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Sun_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SUN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Thorny_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.THORNY_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof True_Sun_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.TRUE_SUN_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Undying_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.UNDYING_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Vampiric_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.VAMPIRIC_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Vindicator_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.VINDICATOR_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Void_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.VOID_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Water_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.WATER_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Weakened_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.WEAKENED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Witch_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.WITCH_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Wither_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.WITHER_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Withered_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.WITHERED_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Zombie_Talisman_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.ZOMBIE_TALISMAN_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Soul_of_Colossus_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SOUL_OF_COLOSSUS_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Soul_of_Flight_Mastery_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SOUL_OF_FLIGHT_MASTERY_EFFECT, 219, 0, false, false));
                }
                if (itemStack.getItem() instanceof Soul_of_Supersonic_Provider) {
                    serverPlayer.addEffect(new MobEffectInstance(EffectsInit.SOUL_OF_SUPERSONIC_EFFECT, 219, 0, false, false));
                }
            }
        }

    }

    public static void tickEntity(Level level, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {
        if (!level.isClientSide) {
            blockEntity.tickCounter++;
            if (blockEntity.tickCounter >= 10) {
                blockEntity.applyTalismanEffect();
                blockEntity.tickCounter = 0;
            }
        }
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        setChanged();
        return itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        setChanged();
        ItemStack stack = itemHandler.getStackInSlot(pSlot);
        stack.shrink(pAmount);
        return itemHandler.insertItem(pSlot, stack, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        setChanged();
        return pSlot >= 0 && pSlot < getContainerSize() ? itemHandler.insertItem(pSlot, ItemStack.EMPTY, false) : ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        setChanged();
        itemHandler.insertItem(pSlot, pStack.copyWithCount(1), false);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < getContainerSize(); i++) {
            itemHandler.extractItem(i, 64, false);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

}