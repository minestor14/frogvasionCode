package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.Goals.GrapplingFrogGoal;
import me.Minestor.frogvasion.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class GrapplingFrog extends ModFrog implements GeoEntity {
    public static final TrackedData<Float> ANGLE = DataTracker.registerData(GrapplingFrog.class, TrackedDataHandlerRegistry.FLOAT);
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public GrapplingFrog(EntityType<? extends TameableEntity> type,World world) {
        super(ModEntities.GRAPPLING_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.GRAPPLING;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
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
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.4D, 2));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SlimeEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));

    }
    @Override
    PlayState attackPredicate(AnimationState event) {
        if(this.handSwinging) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.grappling_frog.attack", Animation.LoopType.PLAY_ONCE));
            this.handSwinging = false;
        }
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",0, this::predicate));
        controllers.add(new AnimationController(this, "attackController",0, this::attackPredicate));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGLE, 0f);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        float j = this.getTarget() == null ? 0: (float)Math.asin((this.getTarget().getY()-this.getY())/this.distanceTo(this.getTarget()));
        nbt.putFloat("Angle", j);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(ANGLE, nbt.getFloat("Angle"));
    }
    public void updateAngle() {
        this.dataTracker.set(ANGLE, this.getTarget() == null ? 0: (float)Math.asin((this.getTarget().getY()-this.getY())/this.distanceTo(this.getTarget())));
    }
    public float getAngle() {
       return this.dataTracker.get(ANGLE);
    }
    public void pullEntity(LivingEntity entity) {
        entity.setVelocity(new Vec3d(this.getX(),this.getEyeY(),this.getZ()).subtract(new Vec3d(entity.getX(),entity.getEyeY(),entity.getZ())));
    }
}
