package me.Minestor.frogvasion.entities.goals;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GrapplingFrog;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class GrapplingFrogGoal extends Goal {
    private final GrapplingFrog entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;
    private final List<Entity> list = new ArrayList<>();

    public GrapplingFrogGoal(GrapplingFrog entity, double moveSpeedAmpIn) {
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
        this.entity.updateAngle();
        list.clear();
        Frogvasion.LOGGER.info("started AI Grappling");
    }
    public void stop() {
        super.stop();
        this.entity.setAttacking(false);
        this.attackTime = -1;
        list.clear();
        this.entity.updateAngle();
    }
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            this.entity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getEyePos());

            double d = this.entity.distanceTo(target);
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(target);

            if (inLineOfSight && Math.abs(entity.getY() - target.getY()) <= 1.2) {
                this.entity.updateAngle();
                this.attackTime++;
                if(d >= 8) this.entity.getMoveControl().moveTo(target.getX(),target.getY(),target.getZ(),this.moveSpeedAmp);

                if (this.attackTime == 1 && d <= 8) {
                    this.entity.swingHand(Hand.MAIN_HAND);
                    this.entity.setAttacking(true);
                } else if (d > 8) this.attackTime = -5;

                double sinYaw = Math.sin(Math.toRadians(-this.entity.headYaw)), cosYaw = Math.cos(Math.toRadians(-this.entity.headYaw)), sinPitch = Math.sin(Math.toRadians(-this.entity.getPitch()));
                double x= this.entity.getX() + sinYaw*(attackTime-10), z= this.entity.getZ() + cosYaw*(attackTime-10), y= this.entity.getY() + sinPitch*(attackTime-10);
                if(this.attackTime >= 11 && this.attackTime <= 18) {
                    for(Entity e : this.entity.getWorld().getOtherEntities(this.entity, new Box(new Vec3d(x+0.5,y+0.5,z+0.5), new Vec3d(x-0.5,y-0.5,z-0.5)),(entity) -> (entity instanceof LivingEntity) && (!(entity instanceof PlayerEntity) || (!((PlayerEntity) entity).isCreative() || !entity.isSpectator())))){
                        this.entity.tryAttack(e);
                        ((LivingEntity)e).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, attackTime - 10,6,false,false));
                        ((LivingEntity)e).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, attackTime - 10,6,false, false));
                        list.add(e);
                    }
                }

                if (this.attackTime == 17 && d <= 9) {
                    for(Entity en : list) {
                        if(this.entity.distanceTo(en) >= 9) continue;
                        this.entity.pullEntity((LivingEntity)en);
                        en.timeUntilRegen = 0;
                    }
                }

                if (this.attackTime >= 30) {
                    this.entity.setAttacking(false);
                    list.clear();
                    this.attackTime = -5;
                }
            }
        }
    }
}
