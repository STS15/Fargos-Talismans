package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.attacks.death_sickle.DeathSickleEntity;
import com.sts15.fargos.entity.eridanus.EridanusBoss;
import com.sts15.fargos.init.EntityRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.*;

/**
 * A goal that spawns a ring of DeathSickleEntity and animates them
 * in one of many never-repeating patterns around the boss.
 */
public class SicklePatternGoal extends Goal {
    private static final long ACTIVE_DURATION_TICKS = 10 * 20;
    private static final long COOLDOWN_TICKS        = 10 * 20;
    private static final int  DEGREE_STEP           = 20;
    private final EridanusBoss boss;
    private final double       baseRadius;
    private final List<DeathSickleEntity> sickles;
    private final Random       random = new Random();
    private long   startTick, lastStartTick = -COOLDOWN_TICKS;
    private double spinOffset;
    private SicklePattern pattern, lastPattern = null;

    public SicklePatternGoal(EridanusBoss boss, double radius, List<DeathSickleEntity> sickles) {
        this.boss       = boss;
        this.baseRadius = radius;
        this.sickles    = sickles;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        long now = boss.level().getGameTime();
        return now >= lastStartTick + COOLDOWN_TICKS;
    }

    @Override
    public void start() {
        this.startTick     = boss.level().getGameTime();
        this.lastStartTick = startTick;
        this.spinOffset    = random.nextDouble() * Math.PI * 2;

        // pick a new pattern that's not the same as last time
        SicklePattern[] vals = SicklePattern.values();
        do {
            pattern = vals[random.nextInt(vals.length)];
        } while (pattern == lastPattern);
        lastPattern = pattern;

        // spawn one sickle per DEGREE_STEP around the circle
        for (int deg = 0; deg < 360; deg += DEGREE_STEP) {
            double rad = Math.toRadians(deg);
            double x = boss.getX() + baseRadius * Math.cos(rad);
            double y = boss.getY();
            double z = boss.getZ() + baseRadius * Math.sin(rad);

            DeathSickleEntity s = new DeathSickleEntity(EntityRegistry.DEATH_SICKLE.get(), boss.level());
            s.setNoGravity(true);
            s.setPos(x, y, z);
            boss.level().addFreshEntity(s);
            sickles.add(s);
        }
    }

    @Override
    public boolean canContinueToUse() {
        long now = boss.level().getGameTime();
        return now < startTick + ACTIVE_DURATION_TICKS;
    }

    @Override
    public void stop() {
        for (DeathSickleEntity s : sickles) s.discard();
        sickles.clear();
    }

    @Override
    public void tick() {
        double t    = (boss.level().getGameTime() - startTick) / 20.0;
        double spin = spinOffset + t * Math.PI;
        long now     = boss.level().getGameTime();
        long   half  = ACTIVE_DURATION_TICKS / 2;
        boolean part2 = (now - startTick) >= half;

        switch (pattern) {
            case CIRCLE           -> { if (!part2) circle1(spin); else circle2(spin); }
            case RADIAL_PULSATE   -> { if (!part2) radial1(spin); else radial2(spin); }
            case PETAL            -> { if (!part2) petal1(spin); else petal2(spin); }
            case HOMING           -> { if (!part2) homing1(spin); else homing2(spin); }
            case ELLIPSE          -> { if (!part2) ellipse1(spin); else ellipse2(spin); }
            case FIGURE_EIGHT     -> { if (!part2) eight1(spin);  else eight2(spin);  }
            case SPIRAL_IN        -> { if (!part2) spiral1(spin, true);  else spiral2(spin, true);  }
            case SPIRAL_OUT       -> { if (!part2) spiral1(spin, false); else spiral2(spin, false); }
            case DUAL_ORBIT        -> dualOrbit(spin);
            case POLYGON_TRIANGLE  -> polygon(spin, 3);
            case POLYGON_SQUARE    -> polygon(spin, 4);
            case POLYGON_PENTAGON  -> polygon(spin, 5);
            case SINE_WAVE         -> sineWaveRing(spin, 0.5);
            case GRID_SWEEP        -> gridSweep(t);
            case STAR_SHAPE        -> starShape(spin);
            case HEART_SHAPE       -> heartShape(spin);
            case TORNADO           -> tornado(spin);
            case CASCADE           -> cascade(spin);
            case WAVE_RING         -> waveRing(spin);
        }
    }

    // ───────────────────────────────────────────────────────────────────
    // Pattern implementations (trigonometric or simple procedural)
    // ───────────────────────────────────────────────────────────────────

    private void circle1(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = spin + i++ * 2 * Math.PI / sickles.size();
            setPos(s, baseRadius, θ, 0);
        }
    }

    private void radial1(double spin) {
        double pulse = baseRadius + Math.sin(spin * 3) * 0.5;
        circle1(spin);
        int i = 0;
        for (var s : sickles) {
            double θ = spin + i++ * 2 * Math.PI / sickles.size();
            setPos(s, pulse, θ, 0);
        }
    }

    private void petal1(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = spin + i++ * 2 * Math.PI / sickles.size();
            double r = baseRadius * (1 + Math.cos(3 * θ) * 0.3);
            setPos(s, r, θ, 0);
        }
    }

    private void homing1(double spin) {
        LivingEntity tgt = boss.getTarget() instanceof LivingEntity e ? e : null;
        if (tgt != null && tgt.isFallFlying()) {
            for (var s : sickles) {
                Vec3 dir = tgt.position().subtract(s.position()).normalize().scale(0.2);
                s.setDeltaMovement(dir);
            }
        } else {
            circle1(spin);
        }
    }

    private void ellipse1(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = spin + i++ * 2 * Math.PI / sickles.size();
            double x = boss.getX() + 1.5 * baseRadius * Math.cos(θ);
            double z = boss.getZ() + 0.8 * baseRadius * Math.sin(θ);
            s.setPos(x, boss.getY(), z);
        }
    }

    private void eight1(double spin) {
        int i = 0;
        for (var s : sickles) {
            double t = spin + i++ * 2 * Math.PI / sickles.size();
            double x = boss.getX() + baseRadius * Math.sin(t);
            double z = boss.getZ() + baseRadius * Math.sin(2 * t) / 2;
            s.setPos(x, boss.getY(), z);
        }
    }

    private void spiral1(double spin, boolean inward) {
        int count = sickles.size();
        for (int i = 0; i < count; i++) {
            double t = spin + i * 0.5;
            double r = inward
                    ? baseRadius * (1 - i / (double) count)
                    : baseRadius * (i / (double) count);
            setPos(sickles.get(i), r, t, 0);
        }
    }

    private void circle2(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = -spin + i++ * 2 * Math.PI / sickles.size();
            setPos(s, baseRadius * 0.8, θ, 0);
        }
    }

    private void radial2(double spin) {
        double pulse = baseRadius + Math.sin(spin * 6) * 0.8;
        circle2(spin * 1.5);
        int i = 0;
        for (var s : sickles) {
            double θ = -spin + i++ * 2 * Math.PI / sickles.size();
            setPos(s, pulse, θ, 0.5);
        }
    }

    private void petal2(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = -spin + i++ * 2 * Math.PI / sickles.size();
            double r = baseRadius * (1 + Math.cos(5 * θ) * 0.5);
            setPos(s, r, θ, 0.2);
        }
    }

    private void homing2(double spin) {
        LivingEntity tgt = boss.getTarget() instanceof LivingEntity e ? e : null;
        if (tgt != null && tgt.isFallFlying()) {
            for (var s : sickles) {
                Vec3 dir = tgt.position().subtract(s.position()).normalize().scale(0.4);
                s.setDeltaMovement(dir);
            }
        } else {
            spiral1(spin, false);
        }
    }

    private void ellipse2(double spin) {
        int i = 0;
        for (var s : sickles) {
            double θ = -spin + i++ * 2 * Math.PI / sickles.size();
            double x = boss.getX() + 0.8 * baseRadius * Math.cos(θ);
            double z = boss.getZ() + 1.5 * baseRadius * Math.sin(θ);
            s.setPos(x, boss.getY() + 0.3, z);
        }
    }

    private void eight2(double spin) {
        int i = 0;
        for (var s : sickles) {
            double t = spin + i++ * 2 * Math.PI / sickles.size();
            double x = boss.getX() + baseRadius * Math.cos(t);
            double z = boss.getZ() + baseRadius * Math.sin(2 * t) / 2;
            s.setPos(x, boss.getY(), z);
        }
    }

    private void spiral2(double spin, boolean inward) {
        int count = sickles.size();
        for (int i = 0; i < count; i++) {
            double t = 2 * spin + i * 0.8;
            double r = inward
                    ? baseRadius * (i / (double) count)
                    : baseRadius * (1 - i / (double) count);
            setPos(sickles.get(i), r, t, (i / (double) count) * 1.0);
        }
    }

    private void dualOrbit(double spin) {
        int half = sickles.size()/2;
        for (int i = 0; i < sickles.size(); i++) {
            double θ = spin + i * 2 * Math.PI / half;
            double r = (i < half ? baseRadius : baseRadius*0.5);
            setPos(sickles.get(i), r, θ, 0);
        }
    }

    private void polygon(double spin, int sides) {
        double θ0 = spin % (2*Math.PI);
        for (int i = 0; i < sickles.size(); i++) {
            int v = i % sides;
            double θ = θ0 + v * 2 * Math.PI / sides;
            setPos(sickles.get(i), baseRadius, θ, 0);
        }
    }

    private void sineWaveRing(double spin, double amp) {
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double θ = spin + i * 2 * Math.PI / sickles.size();
            double r = baseRadius + Math.sin(i + spin*2) * amp;
            setPos(s, r, θ, 0);
            i++;
        }
    }

    private void gridSweep(double t) {
        int size = (int)Math.sqrt(sickles.size());
        int idx = 0;
        for (int xi=0; xi<size; xi++) {
            for (int zi=0; zi<size && idx<sickles.size(); zi++, idx++) {
                double fraction = ((t + xi + zi) % size) / (double)size;
                double x = boss.getX() + (xi - size/2) + fraction;
                double z = boss.getZ() + (zi - size/2);
                sickles.get(idx).setPos(x, boss.getY(), z);
            }
        }
    }

    private void starShape(double spin) {
        // approximate 5-point star by alternating radii
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double θ = spin + i * 2 * Math.PI / sickles.size();
            double r = (i % 2 == 0 ? baseRadius : baseRadius*0.5);
            setPos(s, r, θ, 0);
            i++;
        }
    }

    private void heartShape(double spin) {
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double t = 2 * Math.PI * i++ / sickles.size();
            double x = boss.getX() + baseRadius * 0.5 * (16*Math.sin(t)*Math.sin(t)*Math.sin(t));
            double z = boss.getZ() + baseRadius * 0.5 * (13*Math.cos(t)-5*Math.cos(2*t)-2*Math.cos(3*t)-Math.cos(4*t));
            s.setPos(x, boss.getY(), z);
        }
    }

    private void tornado(double spin) {
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double θ = spin + i * 0.5;
            double y = boss.getY() + (i / (double)sickles.size()) * 5;
            double r = baseRadius * (1 - i / (double)sickles.size());
            setPos(s, r, θ, y - boss.getY());
            i++;
        }
    }

    private void cascade(double spin) {
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double phase = (spin + i) % Math.PI;
            double y = boss.getY() + Math.abs(Math.sin(phase)) * 3;
            setPos(s, baseRadius, spin + i*0.3, y - boss.getY());
            i++;
        }
    }

    private void waveRing(double spin) {
        int i = 0;
        for (DeathSickleEntity s : sickles) {
            double θ = spin + i * 2 * Math.PI / sickles.size();
            double y = boss.getY() + Math.sin(θ*3 + spin) * 1.5;
            setPos(s, baseRadius, θ, y - boss.getY());
            i++;
        }
    }

    private void setPos(DeathSickleEntity s, double r, double θ, double yOff) {
        double x = boss.getX() + r * Math.cos(θ);
        double y = boss.getY() + yOff;
        double z = boss.getZ() + r * Math.sin(θ);
        s.setPos(x, y, z);
    }

    /** All available patterns. */
    public enum SicklePattern {
        CIRCLE,
        RADIAL_PULSATE,
        PETAL,
        HOMING,
        ELLIPSE,
        FIGURE_EIGHT,
        SPIRAL_IN,
        SPIRAL_OUT,
        VERTICAL_RING,
        HORIZONTAL_RING,
        DIAGONAL_RING,
        DUAL_ORBIT,
        POLYGON_TRIANGLE,
        POLYGON_SQUARE,
        POLYGON_PENTAGON,
        SINE_WAVE,
        RANDOM_SCATTER,
        GRID_SWEEP,
        STAR_SHAPE,
        HEART_SHAPE,
        TORNADO,
        CASCADE,
        WAVE_RING
    }
}
