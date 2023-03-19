package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.sounds.ModSounds;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public abstract class ModFrog extends TameableEntity implements GeoAnimatable {
    public static final TrackedData<Integer> MAGMA_EATEN = DataTracker.registerData(ModFrog.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> INFUSED = DataTracker.registerData(ModFrog.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int INFUSED_BRIGHTNESS = 200;

    public ModFrog(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
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

    PlayState predicate(AnimationState event) {
        if(this.getBlockStateAtPos().getBlock() == Blocks.WATER) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(!this.isOnGround()) {
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

    PlayState attackPredicate(AnimationState event) {
        if(this.handSwinging) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.frog.attack", Animation.LoopType.PLAY_ONCE));
            this.handSwinging = false;
        }
        return PlayState.CONTINUE;
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
            return !(p.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.GHOST_FRAGMENT_BOOTS && p.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.GHOST_FRAGMENT_HELMET &&
                    p.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.GHOST_FRAGMENT_LEGGINGS && p.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.GHOST_FRAGMENT_CHESTPLATE)
                    && (!p.isCreative() && !p.isSpectator());
        }
        return true;
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
