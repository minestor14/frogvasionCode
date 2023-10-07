package me.Minestor.frogvasion.entities.goals;

import me.Minestor.frogvasion.entities.custom.ExplosiveFrog;
import me.Minestor.frogvasion.sounds.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class ExplosiveFrogGoal extends Goal {
    private final ExplosiveFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;

    public ExplosiveFrogGoal(ExplosiveFrog entity, double moveSpeedAmpIn) {
        this.entity = entity;
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
            this.entity.lookAtEntity(livingentity, 50.0F, 30.0F);
            if (inLineOfSight) {
                this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
                if(d <= 3 || this.attackTime > 4) {
                    this.attackTime++;
                } else {
                    if(this.attackTime >= 0) {
                        this.attackTime--;
                    }
                }
                if (this.attackTime == 1) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                    this.entity.setAttacking(true);
                    this.entity.playSound(ModSounds.CHARGE, 1.0F, 0.5F);
                }
                if(this.attackTime >= 1 && this.attackTime<=10) {
                    this.entity.getNavigation().stop();
                }
                if (this.attackTime == 10) {
                    this.entity.setAttacking(false);
                    this.entity.explode();
                }
            }
        }
    }
}
