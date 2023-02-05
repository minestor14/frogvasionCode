package me.Minestor.frogvasion.entities.Goals;

import me.Minestor.frogvasion.entities.custom.EnderFrog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class EnderFrogAttackGoal extends Goal {
    private final EnderFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;
    private int teleportTime=-1;
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

    public EnderFrogAttackGoal(EnderFrog entity, double moveSpeedAmpIn) {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeedAmpIn;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public void tick() {
        LivingEntity livingentity = this.entity.getTarget();
        if (livingentity != null) {
            double d = this.entity.distanceTo(this.entity.getTarget());
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
            this.entity.lookAtEntity(livingentity, 50.0F, 50.0F);
            if (inLineOfSight && this.entity.canTarget(livingentity)) {
                this.attackTime++;
                this.teleportTime++;
                if(d >= 1.75) {this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);}
                if (this.attackTime == 1 && d <= 2) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                }
                if(this.attackTime >= 1 && this.attackTime<=4) {
                    this.entity.getNavigation().stop();
                }
                if (this.attackTime == 4) {
                    if(d <= 2) {
                        this.entity.tryAttack(livingentity);
                        livingentity.timeUntilRegen = 0;
                    }
                }
                if (this.attackTime >= 10) {
                    this.attackTime = -5;
                }
                if(this.teleportTime >=30) {
                    this.entity.teleport(livingentity.getX(),livingentity.getY(),livingentity.getZ(), true);
                    this.entity.emitTeleportEvent();
                    this.teleportTime = -5;
                }
            }
        }
    }
}