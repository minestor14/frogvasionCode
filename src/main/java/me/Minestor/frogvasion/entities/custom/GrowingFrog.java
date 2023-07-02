package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.Goals.FrogAttackGoal;
import me.Minestor.frogvasion.entities.Goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class GrowingFrog extends ModFrog implements GeoEntity {
    public static final TrackedData<Integer> SIZE= DataTracker.registerData(GrowingFrog.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    public GrowingFrog(EntityType<? extends TameableEntity> entityType, World world) {
        super(ModEntities.GROWING_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.GROWING;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new FrogAttackGoal(this, 0.4));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(3, new FrogWanderJumpGoal(this, 0.4D, 2));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));


        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
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
    PlayState predicate(AnimationState<ModFrog> event) {
        if(this.getBlockStateAtPos().getBlock() == Blocks.WATER && this.getSize() <=10) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(!this.isOnGround() && this.getSize() <=10) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.off_ground", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.random.nextInt(15) == 1) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.croak", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().then("animation.frog.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIZE, 1);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Size", this.getSize());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SIZE, nbt.getInt("Size"));
    }
    public void setSize(Integer size) {
        this.dataTracker.set(SIZE, size);
    }
    public Integer getSize() {
        return this.dataTracker.get(SIZE);
    }
    public void addToSize(Integer amount) {
        setSize(getSize() + amount);
        calculateDimensions();
    }
    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.setPosition(d, e, f);
    }
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.5f * (float)this.getSize()+0.5f);
    }
    @Override
    public boolean tryAttack(Entity target) {
        addToSize(1);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) getSize()/2+0.5);
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) getSize()+9);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double) getSize()/20+0.3);
        this.heal(1f);
        return super.tryAttack(target);
    }

    @Override
    public void tick() {
        super.tick();
        calculateDimensions();
        if (!this.getWorld().isClient) {
            if (getSize() >= 6) {
                this.targetSelector.add(3, new ActiveTargetGoal<>(this, AnimalEntity.class, true));
            }
            if (getSize() >= 11) {
                this.targetSelector.add(4, new ActiveTargetGoal<>(this, HostileEntity.class, true));
            }
            if (getSize() >= 21) {
                this.dead = true;
                this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 8f, this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE);
                this.discard();
            }
        }
    }
}
