package com.sts15.fargos.entity.eridanus;

import com.mojang.serialization.Dynamic;
import com.sts15.fargos.entity.attacks.death_sickle.DeathSickleEntity;
import com.sts15.fargos.entity.attacks.targetLocked.TargetLockedEntity;
import com.sts15.fargos.utils.ExtendedServerBossEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EridanusBoss extends Monster implements GeoEntity {

    // ──────── CONSTRUCTORS & FIELDS ────────
    private static final EntityDataAccessor<Integer> PHASE =
            SynchedEntityData.defineId(EridanusBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_DASHING =
            SynchedEntityData.defineId(EridanusBoss.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ExtendedServerBossEvent bossEvent;

    private final RawAnimation IDLE        = RawAnimation.begin().thenLoop("eridanus_idle");
    private final RawAnimation ATTACK      = RawAnimation.begin().thenPlay("eridanus_attack");
    private final RawAnimation START_CHEER = RawAnimation.begin().thenPlay("eridanus_start_cheer");
    private final RawAnimation CHEER       = RawAnimation.begin().thenPlay("eridanus_cheer");

    private final AnimationController<EridanusBoss> mainController =
            new AnimationController<>(this, "main_controller", 0, this::mainPredicate);
    private RawAnimation queuedAnimation = null;

    public EridanusBoss(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward    = 60;
        this.lookControl = new LookControl(this);
        this.moveControl = new MoveControl(this);
        createBossEvent();
    }

    /** Attributes: damage, speed, health, etc. */
    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE,             10.0)
                .add(Attributes.ARMOR,                     15.0)
                .add(Attributes.MAX_HEALTH,                5000.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,      .8)
                .add(Attributes.ATTACK_KNOCKBACK,          .6)
                .add(Attributes.ENTITY_INTERACTION_RANGE,  4.0)
                .add(Attributes.FOLLOW_RANGE,             32.0)
                .add(Attributes.FLYING_SPEED,            .155)
                .add(Attributes.MOVEMENT_SPEED,          .155);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PHASE, 0);
        builder.define(IS_DASHING, false);
    }

    public boolean isDashing() {
        return this.entityData.get(IS_DASHING);
    }

    public void setDashing(boolean dashing) {
        this.entityData.set(IS_DASHING, dashing);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return EridanusAi.makeBrain(this, dynamic);
    }

    @Override
    public Brain<EridanusBoss> getBrain() {
        return (Brain<EridanusBoss>) super.getBrain();
    }


    protected @NotNull PathNavigation createNavigation(@NotNull Level p_186262_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_186262_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    /** BossBar event setup */
    private void createBossEvent() {
        this.bossEvent = (ExtendedServerBossEvent)
                new ExtendedServerBossEvent(getUUID(), getDisplayName(),
                        BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)
                        .setDarkenScreen(true)
                        .setCreateWorldFog(true);
    }

    // ──────── TICK LOGIC ────────

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level();
        this.getBrain().tick(serverLevel, this);
        EridanusAi.tick(this);
        super.customServerAiStep();
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            updateBossBar();
        }
        if (level().isClientSide && queuedAnimation != null) {
            mainController.forceAnimationReset();
            mainController.setAnimation(queuedAnimation);
            queuedAnimation = null;
        }
    }

    private void updateBossBar() {
        float health = getHealth();
        float max = getMaxHealth();
        float pct = health / max;
        bossEvent.setProgress(pct);
        bossEvent.setColor(BossEvent.BossBarColor.PURPLE);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public void push(Entity byEntity) {
        if (byEntity instanceof DeathSickleEntity
                || byEntity instanceof TargetLockedEntity) return;
        super.push(byEntity);
    }

    // ──────── ANIMATION STUFF ────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(mainController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private PlayState mainPredicate(AnimationState<EridanusBoss> state) {
        AnimationController<?> controller = state.getController();

        if (queuedAnimation != null) {
            controller.forceAnimationReset();
            controller.setAnimation(queuedAnimation);
            queuedAnimation = null;
            return PlayState.CONTINUE;
        }

        if (isDashing()) {
            controller.setAnimation(ATTACK);
            return PlayState.CONTINUE;
        }

        controller.setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    /** Trigger the attack animation immediately outside of dash (optional). */
    public void playAttackAnimation() {
        queuedAnimation = ATTACK;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        playAttackAnimation();
        return super.doHurtTarget(target);
    }

    // ──────── SAVING & LOADING ────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }
}
