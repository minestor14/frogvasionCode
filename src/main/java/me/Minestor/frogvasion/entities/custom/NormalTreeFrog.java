package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.TreeFrogAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;

public class NormalTreeFrog extends ModTreeFrog {
    public NormalTreeFrog(EntityType<? extends PassiveEntity> entityType, World world) {
        super(ModEntities.NORMAL_TREE_FROG_ENTITY, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PassiveEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.42)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4d);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.42));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.42));
        this.goalSelector.add(3, new TreeFrogAttackGoal(this, 0.42D));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, ModFrog.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, FrogEntity.class, true));
    }
}
