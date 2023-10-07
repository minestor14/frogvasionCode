package me.Minestor.frogvasion.entities.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.goals.FrogWanderJumpGoal;
import me.Minestor.frogvasion.entities.goals.IceFrogGoal;
import me.Minestor.frogvasion.items.Custom.IceSpikeItemEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class IceFrog extends ModFrog{
    public IceFrog(EntityType<? extends TameableEntity> entityType, World world) {
        super(ModEntities.ICE_FROG_ENTITY, world);
    }

    @Override
    public FrogTypes getFrogType() {
        return FrogTypes.ICE;
    }
    protected void initGoals() {
        this.goalSelector.add(1, new GoToWalkTargetGoal(this,0.4));
        this.goalSelector.add(2, new IceFrogGoal(this, 0.4));
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
    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4d);
    }
    public void launchToTarget() {
        IceSpikeItemEntity iceSpikeItemEntity = new IceSpikeItemEntity(this, world);
        iceSpikeItemEntity.setPos(this.getX(),this.getEyeY(),this.getZ());
        iceSpikeItemEntity.setVelocity(this, this.getPitch(), this.getYaw(), 0.0F, 1F, 0.2F);
        world.spawnEntity(iceSpikeItemEntity);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if(this.getOwner() != player && !player.isCreative() && !player.isSpectator())
            player.setFrozenTicks(player.getFrozenTicks() >= 140 ? player.getFrozenTicks() : player.getFrozenTicks()+10);
        super.onPlayerCollision(player);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        showParticle();
        return super.damage(source, amount);
    }

    protected void showParticle() {
        ParticleEffect particleEffect = ParticleTypes.SNOWFLAKE;

        for(int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }
    public void tickMovement() {
        super.tickMovement();
        if (this.world != null && !this.world.isClient) {
            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY());
            int k = MathHelper.floor(this.getZ());
            BlockPos blockPos = new BlockPos(i, j, k);
            Biome biome = this.world.getBiome(blockPos).value();
            if (biome.getTemperature() >= 0.9) {
                this.damage(world.getDamageSources().onFire(), 1.0F);
            }
            if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return;
            }

            BlockState state = Blocks.SNOW.getDefaultState();
            for(int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.getY());
                k = MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos2 = new BlockPos(i, j, k);
                if (this.world.getBlockState(blockPos2).isAir() && state.canPlaceAt(this.world, blockPos2)) {
                    this.world.setBlockState(blockPos2, state);
                    this.world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this, state));
                }
            }
            if (this.isOnGround()) {
                BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
                int f = 3;
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-f, -1, -f), blockPos.add(f, -1, f))) {
                    if (blockPos2.isWithinDistance(this.getPos(), f)) {
                        mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
                        BlockState blockState2 = world.getBlockState(mutable);
                        if (blockState2.isAir()) {
                            BlockState blockState3 = world.getBlockState(blockPos2);
                            if (blockState3 == FrostedIceBlock.getMeltedState() && blockState.canPlaceAt(world, blockPos2) && world.canPlace(blockState, blockPos2, ShapeContext.absent())) {
                                world.setBlockState(blockPos2, blockState);
                                world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(this.getRandom(), 60, 120));
                            }
                        }
                    }
                }

            }
        }

    }
}
