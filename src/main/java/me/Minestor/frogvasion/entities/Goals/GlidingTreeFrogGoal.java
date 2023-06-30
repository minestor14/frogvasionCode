package me.Minestor.frogvasion.entities.Goals;

import me.Minestor.frogvasion.entities.custom.GlidingTreeFrog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class GlidingTreeFrogGoal extends Goal {
    private final GlidingTreeFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;
    private boolean attacked;
    public GlidingTreeFrogGoal(GlidingTreeFrog frog, double moveSpeedAmpIn) {
        this.entity = frog;
        this.moveSpeedAmp = moveSpeedAmpIn;
    }
    @Override
    public boolean canStart() {
        return this.entity.getTarget() != null;
    }
    @Override
    public boolean shouldContinue() {
        return canStart();
    }
    @Override
    public void start() {
        super.start();
        this.entity.setAttacking(true);
        attacked = false;
    }
    public void stop() {
        super.stop();
        this.entity.setAttacking(false);
        this.attackTime = -1;
    }
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            double d = this.entity.distanceTo(target);
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(target);
            this.entity.lookAtEntity(target, 50.0F, 50.0F);
            if (inLineOfSight && this.entity.canTarget(target)) {
                this.attackTime++;

                if(this.attackTime == 0) {
                    leap(target.getX(), target.getY(), target.getZ());
                }

                if (this.attackTime > 0 && d <= 1 && !attacked) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                    this.entity.tryAttack(target);
                    target.timeUntilRegen = 0;
                    
                    attacked = true;
                }

                if(this.attackTime >= 1 && this.attackTime <=4 && this.entity.isOnGround()) {
                    double dx = target.getX() - this.entity.getX();
                    double dy = target.getY() - this.entity.getY();
                    double dz = target.getZ() - this.entity.getZ();
                    this.entity.getNavigation().startMovingTo(dx * 2 + target.getX(), dy * 2 + target.getY(), dz * 2 + target.getZ(), moveSpeedAmp);
                }
                if (this.attackTime >= 15) {
                    this.attackTime = -5;
                    attacked = false;
                }
            }
        }
    }
    void leap(double x, double y, double z) {
        double d = this.entity.distanceTo(entity.getTarget())/7;

        Vec3d vec = new Vec3d((x - entity.getX()) * Math.sqrt(d), y - entity.getY() + d * 2, (z - entity.getZ()) * Math.sqrt(d));
        vec = vec.normalize();

        this.entity.addVelocity(vec);
    }
}