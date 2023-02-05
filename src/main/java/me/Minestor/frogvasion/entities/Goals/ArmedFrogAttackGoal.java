package me.Minestor.frogvasion.entities.Goals;

import me.Minestor.frogvasion.entities.custom.ArmedFrog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class ArmedFrogAttackGoal extends Goal {
    private final ArmedFrog entity;
    private int attackTime = -1;
    private final double moveSpeedAmp;

    public ArmedFrogAttackGoal(ArmedFrog entity,double moveSpeed) {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeed;
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
        this.entity.setAttacking(true);
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
                if(attackTime >= 0) {this.entity.getMoveControl().strafeTo(-0.15F,0);} else {this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);}
                if (this.attackTime == 1) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                }
                if(this.attackTime >= 1 && this.attackTime<=4) {
                    this.entity.getNavigation().stop();
                }
                if (this.attackTime == 4) {
                    for(int i = 0; i<=20;i++){
                        this.entity.launchToTarget(10);
                    }
                }
                if (this.attackTime >= 250) {
                    this.attackTime = -5;
                }
            }
        }
    }
}
