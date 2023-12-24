package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.ArmedFrogAttackGoal;
import me.Minestor.frogvasion.entities.goals.FrogWanderJumpGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ArmedFrog extends ModFrog{
    public ArmedFrog(EntityType<? extends TameableEntity> type, World world) {
        super(ModEntities.ARMED_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.ARMED;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new ArmedFrogAttackGoal(this,0.4));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 0.4, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new FrogWanderJumpGoal(this, 0.4D, 2));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(6, new SwimGoal(this));

        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

    }
    public void launchToTarget(float pitchChange) {
        if (!this.getWorld().isClient && this.getTarget() != null && this.getEntityWorld().getOtherEntities(this, new Box(this.getBlockPos()).expand(50)).size() <= 50) {
            ServerWorld serverWorld = (ServerWorld)this.getWorld();
            TadpoleRocket fr = new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, serverWorld);
            fr.setPos(this.getX(),this.getEyeY(),this.getZ());
            fr.setVelocity(this, this.getPitch() + pitchChange, this.getYaw(), 0.0F, 1.5F, 1.0F);
            if(this.isTamed()) fr.setTamed(true, (PlayerEntity) this.getOwner());
            fr.setInfused(this.isInfused());
            fr.setTarget(this.getTarget());
            serverWorld.spawnEntityAndPassengers(fr);
        }
    }
    @Override
    public void onDeath(DamageSource damageSource) {
        for(int i = 0; i<=10;i++){
            this.launchToTarget(10);
        }
        super.onDeath(damageSource);
    }
}
