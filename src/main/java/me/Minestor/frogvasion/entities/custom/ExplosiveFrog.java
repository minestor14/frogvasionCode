package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.ExplosiveFrogGoal;
import me.Minestor.frogvasion.entities.goals.FrogWanderJumpGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExplosiveFrog extends ModFrog{
    public ExplosiveFrog(EntityType<? extends TameableEntity> type,World world) {
        super(ModEntities.EXPLOSIVE_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.EXPLOSIVE;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 19);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new ExplosiveFrogGoal(this, 0.4));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(3, new FrogWanderJumpGoal(this, 0.4D, 2));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

    }
    public void explode() {
        if (!this.world.isClient) {
            this.dead = true;
            if(isInfused()) world.createExplosion(this, world.getDamageSources().explosion(this,this),null , this.getX() + 0.5, this.getY(), this.getZ() + 0.5, 4.0F, true, this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE);
            else this.world.createExplosion(this, this.getX() + 0.5, this.getY(), this.getZ() + 0.5, 4f, this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE);
            this.discard();
        }

    }
}
