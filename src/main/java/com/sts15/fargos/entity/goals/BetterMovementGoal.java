package com.sts15.fargos.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class BetterMovementGoal extends GroundPathNavigation {

    public BetterMovementGoal(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected void trimPath() {
        super.trimPath();
        ArrayList<Node> dumbNodes = new ArrayList<Node>();
        if (path == null || path.getNextNodeIndex() >= path.getNodeCount()) {
            return;
        }
        try {
            var lastImportantNode = path.getNextNode().asVec3();
            var finalNode = path.getEndNode().asVec3();
            if (Math.abs(lastImportantNode.y - finalNode.y) <= 2 && level.clip(new ClipContext(lastImportantNode.add(0, 0.75, 0), finalNode.add(0, 0.75, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty())).getType() == HitResult.Type.MISS
                    && isTraversable(lastImportantNode, finalNode)) {
                for (int i = path.getNextNodeIndex() + 1; i < path.getNodeCount() - 1; i++) {
                    dumbNodes.add(path.getNode(i));
                }
            } else {
                for (int i = path.getNextNodeIndex() + 2; i < path.getNodeCount(); i++) {
                    var node1 = path.getNode(i - 1).asVec3();
                    var node2 = path.getNode(i).asVec3();
                    var delta1 = node1.subtract(lastImportantNode).multiply(1, 3, 1).normalize();
                    var delta2 = node2.subtract(node1).multiply(1, 3, 1).normalize();
                    if (delta1.dot(delta2) > .88 && isTraversable(lastImportantNode, node2)) {
                        dumbNodes.add(path.getNode(i - 1));
                    } else {
                        lastImportantNode = node1;
                    }
                }
            }
            List<Node> optimized = new ArrayList<>();
            for (int i = 0; i < path.getNodeCount(); i++) {
                Node node = path.getNode(i);
                if (!dumbNodes.contains(node)) {
                    optimized.add(node);
                }
            }

            Path newPath = new Path(optimized, path.getTarget(), path.canReach());
            newPath.setNextNodeIndex(path.getNextNodeIndex());
            this.path = newPath;

        } catch (Exception e) {
            this.path = null;
        }
    }

    protected boolean isTraversable(Vec3 pos1, Vec3 pos2) {
        Vec3 step = pos2.subtract(pos1);
        double distance = step.length();
        step = step.scale(1 / distance);
        for (int i = 0; i < distance; i++) {
            BlockPos currentPos = BlockPos.containing(pos1.add(step.scale(i)));
            if (mob.getType().isBlockDangerous(level.getBlockState(currentPos))) {
                return false;
            } else if (!level.getBlockState(currentPos.below()).isFaceSturdy(level, currentPos.below(), Direction.UP)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
