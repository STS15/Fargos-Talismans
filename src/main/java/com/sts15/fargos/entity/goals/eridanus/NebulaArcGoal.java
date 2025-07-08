package com.sts15.fargos.entity.goals.eridanus;

import com.sts15.fargos.entity.eridanus.EridanusBoss;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class NebulaArcGoal extends Goal {
    private static final int    ELLIPSE_STEPS   = 20;
    private static final int    EXTENSION_STEPS = 3;
    private static final double A_RATIO         = 1.0;
    private static final double B_RATIO         = 0.6;

    private final EridanusBoss boss;
    private final double       radius;
    private boolean            shown = false;

    public NebulaArcGoal(EridanusBoss boss, double radius) {
        this.boss   = boss;
        this.radius = radius;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // only run once, when we have a valid player target
        return !shown
                && boss.getTarget() instanceof Player p
                && p.isAlive();
    }

    @Override
    public void start() {
        shown = true;
        showFishArcs();
    }

    @Override
    public boolean canContinueToUse() {
        // we only want one burst
        return false;
    }

    private void showFishArcs() {
        ServerLevel world = (ServerLevel)boss.level();
        Player target     = (Player)boss.getTarget();

        // compute boss→player angle
        Vec3 toPlayer = target.position().subtract(boss.position()).normalize();
        double baseAng = Math.atan2(toPlayer.z, toPlayer.x);

        // spawn preview 1 block in front of the boss
        Vec3 previewBase = boss.position().add(boss.getLookAngle().normalize());

        for(int side = -1; side <= 1; side += 2) {
            double startAng = baseAng + side * Math.PI/2;
            double endAng   = baseAng - side * Math.PI/2;

            // half-ellipse around the player
            for(int i = 0; i < ELLIPSE_STEPS; i++) {
                double t   = i/(double)(ELLIPSE_STEPS-1);
                double θ   = startAng + (endAng - startAng)*t;
                Vec3  pos  = new Vec3(
                        target.getX() + radius*A_RATIO*Math.cos(θ),
                        target.getY() + 1,
                        target.getZ() + radius*B_RATIO*Math.sin(θ)
                );
                world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                        pos.x, pos.y, pos.z,
                        1, 0,0,0, 0
                );
            }

            // straight “tail” diagonal extension
            Vec3 ellipseEnd = new Vec3(
                    target.getX() + radius*A_RATIO*Math.cos(endAng),
                    target.getY() + 1,
                    target.getZ() + radius*B_RATIO*Math.sin(endAng)
            );
            Vec3 dir = ellipseEnd.subtract(target.position()).normalize();
            for(int d = 1; d <= EXTENSION_STEPS; d++) {
                Vec3 p = ellipseEnd.add(dir.scale(d));
                world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                        p.x, p.y, p.z,
                        1, 0,0,0, 0
                );
            }
        }
    }
}
