package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.FrogAttackGoal;
import me.Minestor.frogvasion.entities.goals.FrogWanderJumpGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BossSoldierFrog extends ModFrog{
    public BossSoldierFrog(EntityType<? extends TameableEntity> type,World world) {
        super(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world);
        this.lookControl = new FrogLookControl(this);
        this.setStepHeight(3F);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.BOSS_SOLDIER;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8D);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.5));
        this.goalSelector.add(2, new FrogAttackGoal(this, 0.5));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(5, new SwimGoal(this));
        this.goalSelector.add(6, new FrogWanderJumpGoal(this, 0.5D, 1));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SlimeEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MagmaCubeEntity.class, true));

    }
}
