package com.sts15.fargos.entity.attacks.targetLocked;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.Collections;

public class TargetLockedEntity extends Monster implements GeoEntity {
    private int explosionTimer = 0;
    private BlockPos spawnPosition;

    // ─── Geckolib Animations ───────────────────────────────────────────
    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);
    private final RawAnimation TARGET_LOOP =
            RawAnimation.begin().thenLoop("target_locked.targeted");
    private final AnimationController<TargetLockedEntity> controller =
            new AnimationController<>(this, "target_locked_ctrl", 0, this::predicate);

    // ─── Constructors & Attributes ────────────────────────────────────
    public TargetLockedEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,       1.0D)
                .add(Attributes.MOVEMENT_SPEED,   0.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        // no extra data
    }

    // ─── Tick / Explosion Logic ────────────────────────────────────────
    @Override
    public void tick() {
        super.tick();

        explosionTimer++;
        if (explosionTimer == 60 && level() instanceof ServerLevel server) {
            server.explode(this, getX(), getY(), getZ(), 4.0F, Level.ExplosionInteraction.NONE);
        }

        if (explosionTimer == 80) {
            remove(RemovalReason.DISCARDED);
        }

        if (spawnPosition != null) {
            super.setPos(
                    spawnPosition.getX(),
                    spawnPosition.getY(),
                    spawnPosition.getZ()
            );
        }
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (spawnPosition == null) {
            spawnPosition = blockPosition();
        }
    }

    @Override
    public void setPos(double x, double y, double z) {
        if (spawnPosition != null) {
            super.setPos(
                    spawnPosition.getX(),
                    spawnPosition.getY(),
                    spawnPosition.getZ()
            );
        } else {
            super.setPos(x, y, z);
        }
    }

    @Override
    public void push(double x, double y, double z) {
        // ignore all push
    }

    @Override
    public boolean hurt(DamageSource src, float amt) {
        return false; // completely invulnerable
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    // ─── GeckoLib Boilerplate ──────────────────────────────────────────
    @Override
    public void registerControllers(
            AnimatableManager.ControllerRegistrar registrar
    ) {
        registrar.add(controller);
    }

    private PlayState predicate(AnimationState<TargetLockedEntity> state) {
        state.getController().setAnimation(TARGET_LOOP);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ─── Item/Armor Overrides ──────────────────────────────────────────
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        // no-op
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
