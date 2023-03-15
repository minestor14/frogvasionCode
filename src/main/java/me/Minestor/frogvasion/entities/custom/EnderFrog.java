package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.Goals.EnderFrogAttackGoal;
import me.Minestor.frogvasion.entities.Goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;

public class EnderFrog extends ModFrog implements GeoEntity {
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public EnderFrog(EntityType<? extends TameableEntity> entityType, World world) {
        super(ModEntities.ENDER_FROG_ENTITY, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.ENDER;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4d);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new EnderFrogAttackGoal(this, 0.4));
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
        controllers.add(new AnimationController(this, "controller",0, this::predicate));
        controllers.add(new AnimationController(this, "attackController",0, this::attackPredicate));
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    public void emitTeleportEvent() {
        this.world.emitGameEvent(GameEvent.TELEPORT, this.getPos(), GameEvent.Emitter.of(this));
        if (!this.isSilent()) {
            this.world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }
    }
}
