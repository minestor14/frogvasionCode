package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.entity.ModEntityGroups;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public abstract class ModTreeFrog extends PassiveEntity {
    public World world;
    public ModTreeFrog(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }

    @Override
    public EntityGroup getGroup() {
        return ModEntityGroups.TREEFROGS;
    }
    PlayState predicate(AnimationState event) {
        if(!this.isOnGround()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.tree_frog.jump", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.tree_frog.walking", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().then("animation.tree_frog.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    PlayState attackPredicate(AnimationState event) {
        if(this.handSwinging) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.tree_frog.attack", Animation.LoopType.PLAY_ONCE));
            this.handSwinging = false;
        }
        return PlayState.CONTINUE;
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
}
