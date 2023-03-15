package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.Goals.ArmedFrogAttackGoal;
import me.Minestor.frogvasion.entities.Goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class ArmedFrog extends ModFrog implements GeoEntity {
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public ArmedFrog(EntityType<? extends TameableEntity> type, World world) {
        super(ModEntities.ARMED_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.ARMED;
    }


    @Override
    PlayState attackPredicate(AnimationState event) {
        if(this.handSwinging)
        {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.armed_frog.attack", Animation.LoopType.PLAY_ONCE));
            this.handSwinging = false;
        }
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController",0, this::attackPredicate));
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new ArmedFrogAttackGoal(this,0.4));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(3, new FrogWanderJumpGoal(this, 0.4D, 2));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

    }
    public void launchToTarget(float pitchChange) {
        if (!this.world.isClient && this.getTarget() != null && this.getEntityWorld().getOtherEntities(this, new Box(this.getBlockPos()).expand(50)).size() <= 50) {
            if(this.isTamed()) {
                ServerWorld serverWorld = (ServerWorld)this.world;
                TadpoleRocket fr = new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, serverWorld);
                fr.setPos(this.getX(),this.getEyeY(),this.getZ());
                fr.setVelocity(this, this.getPitch() + pitchChange, this.getYaw(), 0.0F, 1.5F, 1.0F);
                fr.setTamed(true, (PlayerEntity) this.getOwner());
                fr.setInfused(this.isInfused());
                fr.setTarget(this.getTarget());
                serverWorld.spawnEntityAndPassengers(fr);
            } else {
                ServerWorld serverWorld = (ServerWorld)this.world;
                TadpoleRocket fr = new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, serverWorld);
                fr.setPos(this.getX(),this.getEyeY(),this.getZ());
                fr.setVelocity(this, this.getPitch() + pitchChange, this.getYaw(), 0.0F, 1.5F, 1.0F);
                fr.setInfused(this.isInfused());
                fr.setTarget(this.getTarget());
                serverWorld.spawnEntityAndPassengers(fr);
            }
        }
    }
    @Override
    public void onDeath(DamageSource damageSource) {
        for(int i = 0; i<=10;i++){
            this.launchToTarget(10);
        }
        super.onDeath(damageSource);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
