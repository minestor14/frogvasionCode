package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.entity.ModEntityGroups;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ModTreeFrog extends PassiveEntity {
    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(ModTreeFrog.class, TrackedDataHandlerRegistry.BOOLEAN);
    public boolean isMoving = false;
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimTimeout = 0;
    private int moveAnimTimeout = 0;
    private int attackAnimTimeout = 0;
    public World world;
    public ModTreeFrog(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }

    @Override
    public EntityGroup getGroup() {
        return ModEntityGroups.TREEFROGS;
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static <T extends MobEntity> boolean isValidNaturalSpawn(EntityType<T> tEntityType, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(pos.down()).isFullCube(world, pos);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TREE_FROG_CROAK;
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FROG_DEATH;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return super.canTarget(target) && !target.hasStatusEffect(ModEffects.FROG_CAMOUFLAGE);
    }
    private void setupAnimationStates() {
        if(!this.isOnGround() && !this.jumpAnimationState.isRunning()) {
            this.jumpAnimationState.start(this.age);
        } else if (this.isOnGround()) {
            this.jumpAnimationState.stop();
        }

        if(this.isMoving && this.moveAnimTimeout <=0 && !this.jumpAnimationState.isRunning()) {
            moveAnimTimeout = 38;
            moveAnimationState.start(this.age);
        } else {
            --this.moveAnimTimeout;
        }
        if(!this.isMoving) {
            moveAnimationState.stop();
            this.moveAnimTimeout = 0;
        }

        if (this.idleAnimTimeout <= 0 && !this.moveAnimationState.isRunning() && !this.jumpAnimationState.isRunning()) {
            this.idleAnimTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimTimeout;
        }

        if(this.isAttacking() && this.attackAnimTimeout <=0) {
            this.attackAnimTimeout = 5;
            this.attackAnimationState.start(this.age);
        } else {
            --this.attackAnimTimeout;
        }
        if(!this.isAttacking()) {
            this.attackAnimationState.stop();
            this.attackAnimTimeout = 0;
        }
    }
    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        Vec3d velocity = this.getVelocity();
        float avgVelocity = (float)(Math.abs(velocity.x) + Math.abs(velocity.z) / 2f);
        isMoving = avgVelocity >= 0.015f;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }
    @Override
    public boolean isAttacking() {
        return dataTracker.get(ATTACKING);
    }

    @Override
    public void setAttacking(boolean attacking) {
        dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 52*52;
    }
}
