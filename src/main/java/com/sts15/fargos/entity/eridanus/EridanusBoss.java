package com.sts15.fargos.entity.eridanus;

import com.sts15.fargos.entity.attacks.death_sickle.DeathSickleEntity;
import com.sts15.fargos.entity.attacks.targetLocked.TargetLockedEntity;
import com.sts15.fargos.entity.goals.BetterMovementGoal;
import com.sts15.fargos.entity.goals.eridanus.*;
import com.sts15.fargos.utils.ExtendedServerBossEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class EridanusBoss extends Monster implements GeoEntity {

    // ──────── CONSTRUCTORS & FIELDS ────────
    private static final EntityDataAccessor<Integer> PHASE =
            SynchedEntityData.defineId(EridanusBoss.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ExtendedServerBossEvent bossEvent;
    private final RawAnimation IDLE        = RawAnimation.begin().thenLoop("eridanus_idle");
    private final RawAnimation ATTACK      = RawAnimation.begin().thenPlay("eridanus_attack");
    private final RawAnimation START_CHEER = RawAnimation.begin().thenPlay("eridanus_start_cheer");
    private final RawAnimation CHEER       = RawAnimation.begin().thenPlay("eridanus_cheer");
    private final AnimationController<EridanusBoss> idleController   =
            new AnimationController<>(this, "idle_controller", 0, this::idlePredicate);
    private final AnimationController<EridanusBoss> actionController =
            new AnimationController<>(this, "action_controller", 0, this::actionPredicate);
    private boolean startedCheering = false;
    private boolean isCheering      = false;
    private int     cheerLoopCount  = 0;
    private RawAnimation queuedAnimation = null;

    public EridanusBoss(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward    = 60;
        this.lookControl = createLookControl();
        this.moveControl = createMoveControl();
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
    }

    /** Register AI goals: dash attack, float, look, plus targets. */
    @Override
    protected void registerGoals() {
        // Phase 1 goals
        this.goalSelector.getAvailableGoals().forEach(WrappedGoal::stop);
        this.goalSelector.removeAllGoals(x -> true);
        //this.goalSelector.addGoal(0, new DashAttackGoal(this));
        //this.goalSelector.addGoal(0, new SummonTargetLockedGoal(this, 24));
        //List<DeathSickleEntity> spawnedSickles = new ArrayList<>();
        //this.goalSelector.addGoal(2, new SicklePatternGoal(this, 8.0, spawnedSickles));
        this.goalSelector.addGoal(2, new NebulaArcGoal(this, 20));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));

        // Targeting
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        return new BetterMovementGoal(this, level);
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
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            updateBossBar();
            handlePhases();
        }

        if (level().isClientSide && queuedAnimation != null) {
            actionController.forceAnimationReset();
            actionController.setAnimation(queuedAnimation);
            queuedAnimation = null;
        }
    }


    private void updateBossBar() {
        float health   = getHealth(), max = getMaxHealth(), pct = health / max;
        switch (getPhase()) {
            case 0 -> bossEvent.setProgress(pct);
            case 1 -> bossEvent.setProgress((pct - 0.5f) / 0.25f);
            case 2 -> bossEvent.setProgress(1f);
            case 3 -> bossEvent.setProgress(pct / 0.5f);
        }
        bossEvent.setColor(switch (getPhase()) {
            case 0 -> BossEvent.BossBarColor.BLUE;
            case 1 -> BossEvent.BossBarColor.PURPLE;
            default -> BossEvent.BossBarColor.RED;
        });
    }

    private void handlePhases() {
        float pct = getHealth() / getMaxHealth();
        if (getPhase() == Phases.FinalPhase.value) return;

        if (pct >= 0.75f) {
            setPhase(Phases.FirstPhase);
        } else if (pct >= 0.50f) {
            setPhase(Phases.SecondPhase);
        } else if (pct <= 0.20f && !startedCheering && isPhase(Phases.SecondPhase)) {
            // Kick off cheer sequence
            startedCheering  = true;
            isCheering       = false;
            cheerLoopCount   = 0;
            setPhase(Phases.Transition);
            queuedAnimation = START_CHEER;
        } else if (isPhase(Phases.Transition)) {
            // Once start_cheer finishes, loop cheer 5×, then final
            if (!isCheering && actionController.getAnimationState() == AnimationController.State.STOPPED) {
                isCheering      = true;
                queuedAnimation = CHEER;
            } else if (isCheering && actionController.getAnimationState() == AnimationController.State.STOPPED) {
                if (++cheerLoopCount < 5) {
                    queuedAnimation = CHEER;
                } else {
                    setPhase(Phases.FinalPhase);
                }
            }
        }
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
        registrar.add(idleController, actionController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /** Idle animation: loop whenever action controller is not busy. */
    private PlayState idlePredicate(AnimationState<EridanusBoss> state) {
        if (actionController.getAnimationState() == AnimationController.State.RUNNING) {
            return PlayState.STOP;
        }
        state.getController().setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    /**
     * Action controller predicate:
     *  - When queuedAnimation is set, force it to play,
     *  - Continue until it finishes.
     */
    private PlayState actionPredicate(AnimationState<EridanusBoss> state) {
        AnimationController<?> controller = state.getController();
        if (queuedAnimation != null) {
            controller.forceAnimationReset();
            controller.setAnimation(queuedAnimation);
            queuedAnimation = null;
        }
        return controller.getAnimationState() != AnimationController.State.STOPPED
                ? PlayState.CONTINUE
                : PlayState.STOP;
    }

    /** Trigger the attack animation immediately. */
    public void playAttackAnimation() {this.queuedAnimation = ATTACK;}

    @Override
    public boolean doHurtTarget(Entity target) {
        playAttackAnimation();
        return super.doHurtTarget(target);
    }

    /** Helper to check current phase. */
    public boolean isPhaseTransitioning() {return getPhase() == Phases.Transition.value;}
    public AnimationController<EridanusBoss> getActionController() {return this.actionController;}
    public RawAnimation getQueuedAnimation() {return queuedAnimation;}
    public void clearQueuedAnimation() {queuedAnimation = null;}

    // ──────── PHASE ENUM ────────

    public enum Phases {
        FirstPhase(0),
        SecondPhase(1),
        Transition(2),
        FinalPhase(3);

        final int value;
        Phases(int v) { value = v; }
    }

    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public void setPhase(Phases p) {
        this.entityData.set(PHASE, p.value);
    }

    public boolean isPhase(Phases phase) {
        return phase.value == getPhase();
    }

    // ──────── LOOK & MOVE CONTROLS ────────

    protected LookControl createLookControl() {
        return new LookControl(this) {
            @Override protected float rotateTowards(float from, float to, float max) {
                return super.rotateTowards(from, to, max * 2.5f);
            }
        };
    }

    protected MoveControl createMoveControl() {
        return new MoveControl(this) {
            @Override protected float rotlerp(float src, float tgt, float max) {
                double dx = wantedX - mob.getX(), dz = wantedZ - mob.getZ();
                if (dx*dx + dz*dz < .5) return src;
                return super.rotlerp(src, tgt, max * .25f);
            }
        };
    }

    // ──────── SAVING & LOADING ────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("phase", getPhase());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("phase")) setPhase(Phases.values()[tag.getInt("phase")]);
    }
}
