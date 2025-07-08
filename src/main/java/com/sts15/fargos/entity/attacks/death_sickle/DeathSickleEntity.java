package com.sts15.fargos.entity.attacks.death_sickle;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeathSickleEntity extends Monster implements GeoEntity {
    // ─── GeckoLib instance cache & animation definitions ───────────────
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin()
            .thenLoop("death_sickle.idle");
    private final AnimationController<DeathSickleEntity> controller =
            new AnimationController<>(this, "death_sickle_controller", 0, this::predicate);

    // ─── Lifespan & damage tracking ────────────────────────────────────
    private final int spawnTick;
    private boolean customLifespanSet = false;
    private int lifespan;
    private Map<UUID, Integer> lastDamageTick = new HashMap<>();

    // ─── Circling logic ───────────────────────────────────────────────
    private Player circlingPlayer;
    private double circleRadius = 4.0;
    private double assignedAngle = 0.0;

    public DeathSickleEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.spawnTick = this.tickCount;
        this.setCustomName(Component.empty());
        this.setCustomNameVisible(false);
    }

    /** Attributes: 1 HP, no movement. */
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,      1.0D)
                .add(Attributes.MOVEMENT_SPEED,  0.0D);
    }

    /** Optional: externally set a custom lifespan in ticks. */
    public void setLifespan(int ticks) {
        this.lifespan = ticks;
        this.customLifespanSet = true;
    }

    /** Optional: make this sickle circle a player at a given start angle. */
    public void setCirclingPlayer(Player player, double startAngle) {
        this.circlingPlayer = player;
        this.assignedAngle = startAngle;
    }

    @Override
    public void tick() {
        super.tick();

        // ── Auto-expire after ~5 minutes if no custom lifespan given ──
        if (!level().isClientSide && this.tickCount - this.spawnTick >= 6000) {
            remove(RemovalReason.DISCARDED);
            return;
        }

        // ── If no custom lifespan, deal 1/4th health magic "tick" to any nearby players every 10 ticks ──
        if (!customLifespanSet && !level().isClientSide) {
            level().getEntities(this, getBoundingBox().inflate(0.5D), e -> e instanceof Player)
                    .forEach(e -> {
                        Player p = (Player) e;
                        int now = tickCount;
                        UUID id = p.getUUID();
                        if (lastDamageTick.getOrDefault(id, 0) <= now - 10) {
                            //p.hurt(DamageSources, Math.max(0, p.getHealth() * 0.25F - 1.0F));
                            lastDamageTick.put(id, now);
                        }
                    });
        }

        // ── If custom lifespan set, do area magic damage each tick ──
        if (customLifespanSet) {
            AABB box = getBoundingBox().inflate(0.5D);
            @SuppressWarnings("unchecked")
            List<LivingEntity> hits = level().getEntitiesOfClass(LivingEntity.class, box,
                    e -> e != this && !(e instanceof DeathSickleEntity));
            //hits.forEach(e -> e.hurt(DamageSource.MAGIC, 8.0F));
        }

        // ── Handle custom lifespan countdown ──
        if (customLifespanSet) {
            if (--lifespan <= 0) {
                remove(RemovalReason.DISCARDED);
                return;
            }
        }

        // ── If circling a player, update position on a circle ──
        if (circlingPlayer != null) {
            assignedAngle = (assignedAngle + Math.PI/20) % (2*Math.PI);
            double dx = circleRadius * Math.sin(assignedAngle);
            double dz = circleRadius * Math.cos(assignedAngle);
            BlockPos pos = new BlockPos(
                    (int) (circlingPlayer.getX() + dx),
                    (int) (circlingPlayer.getY() + 1),
                    (int) (circlingPlayer.getZ() + dz)
            );
            moveTo(pos, getYRot(), getXRot());
        }
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }

    // ─── Prevent collisions & fluid pushes ────────────────────────────
    @Override public boolean canBeCollidedWith() { return false; }
    @Override public boolean isPushedByFluid()   { return false; }
    @Override
    public void push(Entity byEntity) {
        if (byEntity instanceof EridanusBoss) return;
        super.push(byEntity);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return true;
    }


    // ─── GeckoLib registration ────────────────────────────────────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(controller);
    }

    private PlayState predicate(AnimationState<DeathSickleEntity> state) {
        state.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ─── Equipment stubs ─────────────────────────────────────────────

    @Override public Iterable<ItemStack> getArmorSlots()       { return Collections.emptyList(); }
    @Override public ItemStack       getItemBySlot(EquipmentSlot slot) { return ItemStack.EMPTY; }
    @Override public void            setItemSlot(EquipmentSlot slot, ItemStack stack) { }
    @Override public HumanoidArm     getMainArm()             { return HumanoidArm.RIGHT; }
}