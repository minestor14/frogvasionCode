package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.Goals.FrogAttackGoal;
import me.Minestor.frogvasion.entities.Goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;

public class SoldierFrog extends ModFrog implements GeoEntity {
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public SoldierFrog(EntityType<? extends TameableEntity> type, World world) {
        super(ModEntities.SOLDIER_FROG_ENTITY, world);
        this.lookControl = new FrogLookControl(this);
        this.stepHeight = 2F;
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.SOLDIER;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4d);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new FrogAttackGoal(this, 0.4));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(3, new FrogWanderJumpGoal(this, 0.4D, 2));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SlimeEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController",0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if(!this.world.isClient() && (this.world.random.nextInt(50) == 42 && this.world.getLocalDifficulty(this.getBlockPos()).isAtLeastHard()) || (this.world.random.nextInt(100) == 69)) {
            ServerWorld sworld = (ServerWorld) this.world;
            BossSoldierFrog bob = new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, sworld);
            bob.setPos(this.getX(), this.getY(), this.getZ());
            sworld.spawnEntityAndPassengers(bob);
        }
    }

}
