package me.Minestor.frogvasion.entities.Goals;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogCageBlock;
import me.Minestor.frogvasion.blocks.custom.FrogTrapBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FrogWanderJumpGoal extends Goal {
    public static final int DEFAULT_CHANCE = 120;
    protected final PathAwareEntity mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected final double speed;
    protected int chance;
    protected boolean ignoringChance;
    private final boolean canDespawn;

    public FrogWanderJumpGoal(PathAwareEntity mob, double speed) {
        this(mob, speed, DEFAULT_CHANCE);
    }

    public FrogWanderJumpGoal(PathAwareEntity mob, double speed, int chance) {
        this(mob, speed, chance, true);
    }

    public FrogWanderJumpGoal(PathAwareEntity entity, double speed, int chance, boolean canDespawn) {
        this.mob = entity;
        this.speed = speed;
        this.chance = chance;
        this.canDespawn = canDespawn;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        } else {
            if (!this.ignoringChance) {
                if (this.canDespawn && this.mob.getDespawnCounter() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(toGoalTicks(this.chance)) != 0) {
                    return false;
                }
            }

            Vec3d vec3d = this.getWanderTarget();
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                this.ignoringChance = false;
                return true;
            }
        }
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d noTraps = NoPenaltyTargeting.find(this.mob, 10, 7);
        List<BlockPos> st = getFrogTraps(10, this.mob.getBlockPos(), this.mob.getWorld());
        if(!st.isEmpty()) {
            BlockPos pos = st.stream().findAny().get();
            return new Vec3d(pos.getX(),pos.getY(),pos.getZ());
        }
        return noTraps;
    }

    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers();
    }

    public void start() {
        this.mob.getNavigation().startMovingAlong(this.mob.getNavigation().findPathTo(this.targetX, this.targetY, this.targetZ, 0), this.speed);
        if(this.mob.getWorld().getRandom().nextInt(10) == 9 && this.mob.isOnGround()) {
            this.mob.setVelocity(new Vec3d(0,0.5,0));
        }
    }

    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    public void ignoreChanceOnce() {
        this.ignoringChance = true;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public List<BlockPos> getFrogTraps(int radius, BlockPos center, World world) {
        List<BlockPos> poss = new ArrayList<>();
        BlockPos pos;
        for(int i = -radius; i < radius; i++) {
            for(int j = -radius; j < radius; j++) {
                for(int h = - radius; h< radius; h++) {
                    pos = center.add(i,h,j);
                    BlockState state = world.getBlockState(pos);
                    if(state.isOf(ModBlocks.FROG_TRAP) && state.get(FrogTrapBlock.LOADED)) {
                        poss.add(pos.add(0,1,0));
                    }
                    else if(state.isOf(ModBlocks.FROG_CAGE) && state.get(FrogCageBlock.LOADED)) {
                        poss.add(pos.add(0,1,0));
                    }
                }
            }
        }
        return poss;
    }
}
