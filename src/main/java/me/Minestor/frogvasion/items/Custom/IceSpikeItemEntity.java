package me.Minestor.frogvasion.items.Custom;

import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.ModThrowables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class IceSpikeItemEntity extends PersistentProjectileEntity {
    public IceSpikeItemEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceSpikeItemEntity(double d, double e, double f, World world) {
        super(ModThrowables.ICE_SPIKE_ITEM_ENTITY_TYPE, d, e, f, world);
    }

    public IceSpikeItemEntity(LivingEntity livingEntity, World world) {
        super(ModThrowables.ICE_SPIKE_ITEM_ENTITY_TYPE, livingEntity, world);
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.asItemStack();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            int i = livingEntity.hurtByWater() ? 5 : 3;
            livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)i);
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2)));
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return ModItems.ICE_SPIKE.getDefaultStack();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.kill();
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected SoundEvent getHitSound() {
        return ModSounds.ICE_SPIKE_BREAK;
    }

    @Override
    public void tick() {
        if(this.getBlockStateAtPos().isOf(Blocks.LAVA) || this.getBlockStateAtPos().isOf(Blocks.FIRE)) {
            this.world.sendEntityStatus(this, (byte)3);
            this.kill();
        }
        super.tick();
    }
}
