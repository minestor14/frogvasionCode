package me.Minestor.frogvasion.entities.goals;

import me.Minestor.frogvasion.entities.custom.IceFrog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class IceFrogGoal extends Goal {
    private final IceFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;
    public IceFrogGoal(IceFrog entity, double moveSpeedAmp) {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeedAmp;
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
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
            this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
            if (inLineOfSight) {
                this.attackTime++;
                if(attackTime >= 10) {this.entity.getMoveControl().strafeTo(-0.05F,0);} else {this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);}
                if (this.attackTime == 1) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                    this.entity.setAttacking(true);
                    this.entity.launchToTarget();
                }
                if(this.attackTime >= 1 && this.attackTime<=4) {
                    this.entity.getNavigation().stop();
                }
                if(this.attackTime == 11) {
                    this.entity.setAttacking(false);
                }
                if (this.attackTime >= 35) {
                    this.attackTime = -5;
                }
            }
        }
    }
}
