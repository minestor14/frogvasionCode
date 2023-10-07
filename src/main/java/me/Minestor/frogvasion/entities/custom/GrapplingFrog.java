package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.goals.GrapplingFrogGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrapplingFrog extends ModFrog{
    public float angle;
    public GrapplingFrog(EntityType<? extends TameableEntity> type,World world) {
        super(ModEntities.GRAPPLING_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.GRAPPLING;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2D);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new GrapplingFrogGoal(this, 0.4));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(5, new SwimGoal(this));
        this.goalSelector.add(6, new FrogWanderJumpGoal(this, 0.4D, 2));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SlimeEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));

    }
    public void updateAngle() {
        angle = this.getTarget() == null ? 0: (float)Math.asin((this.getTarget().getY()-this.getY())/this.distanceTo(this.getTarget()));
    }
    public float getAngle() {
       return angle;
    }
    public void pullEntity(LivingEntity entity) {
        entity.setVelocity(new Vec3d(this.getX(),this.getEyeY(),this.getZ()).subtract(new Vec3d(entity.getX(),entity.getEyeY(),entity.getZ())));
    }
}
