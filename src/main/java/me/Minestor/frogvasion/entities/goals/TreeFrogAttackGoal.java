package me.Minestor.frogvasion.entities.goals;

import me.Minestor.frogvasion.entities.custom.ModTreeFrog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class TreeFrogAttackGoal extends Goal {
    private final ModTreeFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;
    public TreeFrogAttackGoal(ModTreeFrog frog, double moveSpeedAmpIn) {
        this.entity = frog;
        this.moveSpeedAmp = moveSpeedAmpIn;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
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
    }
    public void stop() {
        super.stop();
        this.entity.setAttacking(false);
        this.attackTime = -1;
    }
    public void tick() {
        LivingEntity livingentity = this.entity.getTarget();
        if (livingentity != null) {
            double d = this.entity.distanceTo(this.entity.getTarget());
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
            this.entity.lookAtEntity(livingentity, 50.0F, 50.0F);
            if (inLineOfSight && this.entity.canTarget(livingentity)) {
                this.attackTime++;
                if(d >= 1.75) {this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);}
                else this.entity.getMoveControl().strafeTo(-0.25F,0);

                if (this.attackTime == 1 && d <= 2) {
                    entity.swingHand(Hand.MAIN_HAND);
                    entity.setAttacking(true);
                }
                if(this.attackTime >= 1 && this.attackTime<=4) {
                    this.entity.getNavigation().stop();
                }
                if (this.attackTime == 2) {
                    if(d <= 2) {
                        this.entity.tryAttack(livingentity);
                        livingentity.timeUntilRegen = 0;
                    }
                }
                if (this.attackTime >= 4) {
                    this.entity.setAttacking(false);
                    this.attackTime = -5;
                }
            }
        }
    }
}
