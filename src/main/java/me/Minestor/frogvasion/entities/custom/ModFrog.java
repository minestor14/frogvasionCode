package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.entity.ModEntityGroups;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public abstract class ModFrog extends TameableEntity{
    public World world;
    public static final TrackedData<Integer> MAGMA_EATEN = DataTracker.registerData(ModFrog.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> INFUSED = DataTracker.registerData(ModFrog.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(ModFrog.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int INFUSED_BRIGHTNESS = 200;
    public boolean isMoving = false;
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState croakAnimationState = new AnimationState();
    private int idleAnimTimeout = 0;
    private int moveAnimTimeout = 0;
    private int attackAnimTimeout = 0;
    private int swimAnimTimeout = 0;
    public ModFrog(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CROAK;
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FROG_DEATH;
    }
    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(pos.down()).isFullCube(world, pos);
    }
    public abstract FrogTypes getFrogType();
    @Override
    public void setBodyYaw(float bodyYaw) {
        this.setYaw(bodyYaw);
        super.setBodyYaw(bodyYaw);
    }
    public void setTamed(boolean tamed, PlayerEntity owner) {
        setTamed(tamed);
        setOwner(owner);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MAGMA_EATEN, 0);
        this.dataTracker.startTracking(INFUSED, false);
        this.dataTracker.startTracking(ATTACKING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("MagmaEaten", this.getMagmaEaten());
        nbt.putBoolean("Infused", this.isInfused());
    }

    public boolean isInfused() {
        return this.dataTracker.get(INFUSED);
    }

    public void setInfused(boolean value) {
        this.dataTracker.set(INFUSED, value);
    }

    @Override
    public boolean isFireImmune() {
        return isInfused();
    }
    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(MAGMA_EATEN, nbt.getInt("MagmaEaten"));
        this.dataTracker.set(INFUSED, nbt.getBoolean("Infused"));
    }
    public void setMagmaEaten(Integer amount) {
        this.dataTracker.set(MAGMA_EATEN, amount);
    }
    public Integer getMagmaEaten() {
        return this.dataTracker.get(MAGMA_EATEN);
    }
    public void addToMagmaCounter(Integer amount) {
        setMagmaEaten(getMagmaEaten() + amount);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(target.getType() == EntityType.MAGMA_CUBE) addToMagmaCounter(1);
        if(isInfused()) target.setFireTicks(40);
        return super.tryAttack(target);
    }
    private void setupAnimationStates() {
        boolean swim = this.swimAnimationState.isRunning();
        if(this.getBlockStateAtPos().isOf(Blocks.WATER) && this.swimAnimTimeout <=0) {
            swimAnimTimeout = 45;
            swimAnimationState.start(this.age);
        } else {
            --this.swimAnimTimeout;
        }
        if(!this.getBlockStateAtPos().isOf(Blocks.WATER)) {
            swimAnimationState.stop();
        }

        boolean jump = this.jumpAnimationState.isRunning();
        if(!this.isOnGround() && !jump && !swim) {
            jumpAnimationState.start(this.age);
        } else if (this.isOnGround()) {
            jumpAnimationState.stop();
        }

        boolean move = this.moveAnimationState.isRunning();
        if(this.isMoving && this.moveAnimTimeout <=0 && !jump && !swim) {
            moveAnimTimeout = 16;
            moveAnimationState.start(this.age);
        } else {
            --this.moveAnimTimeout;
        }
        if(!this.isMoving) {
            moveAnimationState.stop();
            moveAnimTimeout = 0;
        }

        if (this.idleAnimTimeout <= 0 && !move && !jump && !swim) {
            idleAnimTimeout = this.random.nextInt(40) + 80;
            idleAnimationState.start(this.age);

            if(this.random.nextInt(15) == 1) croakAnimationState.start(this.age);
        } else {
            --this.idleAnimTimeout;
        }

        if(this.isAttacking() && this.attackAnimTimeout <=0 && !attackAnimationState.isRunning()) {
            attackAnimTimeout = getAttackLength(this);
            attackAnimationState.start(this.age);
        } else {
            --this.attackAnimTimeout;
        }
        if(!this.isAttacking()) {
            attackAnimationState.stop();
            attackAnimTimeout = 0;
        }
    }
    private int getAttackLength(ModFrog frog) {
        return switch (frog.getFrogType()) {
            case ARMED, EXPLOSIVE -> 20;
            case GRAPPLING -> 60;
            default -> 10;
        };
    }
    private void setupAnimationStatesTadpole() {
        boolean move = this.moveAnimationState.isRunning();
        if(this.isMoving && this.moveAnimTimeout <=0) {
            moveAnimTimeout = 20;
            moveAnimationState.start(this.age);
        } else {
            --this.moveAnimTimeout;
        }
        if(!this.isMoving) {
            moveAnimationState.stop();
            moveAnimTimeout = 0;
        }

        if (this.idleAnimTimeout <= 0 && !move) {
            idleAnimTimeout = this.random.nextInt(40) + 80;
            idleAnimationState.start(this.age);
        } else {
            --this.idleAnimTimeout;
        }
    }
    @Override
    public void tick() {
        if(getMagmaEaten() >= 10 && !getWorld().isClient()) {
            ServerWorld world =(ServerWorld) getWorld();
            ItemEntity item = new ItemEntity(world, this.getX(),this.getY(),this.getZ(), new ItemStack(ModBlocks.LAVA_INFUSED_FROGLIGHT));
            item.setVelocity(new Vec3d(world.random.nextBoolean() ? world.random.nextFloat()/2 :-world.random.nextFloat()/2,0.5,world.random.nextBoolean() ?world.random.nextFloat()/2 :-world.random.nextFloat()/2));
            world.spawnEntityAndPassengers(item);
            setMagmaEaten(0);
        }
        super.tick();

        if (this.getWorld().isClient()) {
            if (this instanceof TadpoleRocket){
                this.setupAnimationStatesTadpole();
            } else {
                this.setupAnimationStates();
            }
        }

        Vec3d velocity = this.getVelocity();
        float avgVelocity = (float)(Math.abs(velocity.x) + Math.abs(velocity.z) / 2f);
        isMoving = avgVelocity >= 0.015f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        fallDistance -= 4f;
        damageMultiplier *= 0.5f;
        return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if(target.isDead()) return false;
        if(target instanceof PlayerEntity p) {
            return !((p.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.GHOST_FRAGMENT_BOOTS && p.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.GHOST_FRAGMENT_HELMET &&
                    p.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.GHOST_FRAGMENT_LEGGINGS && p.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.GHOST_FRAGMENT_CHESTPLATE)
                    || p.hasStatusEffect(ModEffects.FROG_CAMOUFLAGE))
                    && (!p.isCreative() && !p.isSpectator());
        }
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return ModEntityGroups.FROGS;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
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
        if(this instanceof TadpoleRocket) {
            return distance < 40*40;
        }
        return distance < 52*52;
    }

    public class FrogLookControl extends LookControl {
        FrogLookControl(MobEntity entity) {
            super(entity);
        }

        protected boolean shouldStayHorizontal() {
            return ModFrog.this.getTarget() != null;
        }
    }
}