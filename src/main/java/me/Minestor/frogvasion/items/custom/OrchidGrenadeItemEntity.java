package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.util.items.ModThrowables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OrchidGrenadeItemEntity extends ThrownItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(OrchidGrenadeItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    ItemStack stack;
    public OrchidGrenadeItemEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(ModThrowables.ORCHID_GRENADE_ITEM_ENTITY_TYPE, world);
    }
    public OrchidGrenadeItemEntity(World world, double x, double y, double z, ItemStack stack, LivingEntity livingEntity) {
        super(ModThrowables.ORCHID_GRENADE_ITEM_ENTITY_TYPE, livingEntity, world);
        setPosition(x,y,z);
        this.dataTracker.set(ITEM, stack.copy());
        this.stack = stack.copy();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ITEM, ItemStack.EMPTY);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("GrenadeItem", getStack().writeNbt(new NbtCompound()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(ITEM, ItemStack.fromNbt(nbt.getCompound("GrenadeItem")));

    }

    public void setStack(ItemStack stack) {
        this.dataTracker.set(ITEM, stack.copy());
    }

    @Override
    protected Item getDefaultItem() {
        return getStack().getItem();
    }

    public ItemStack getStack() {
        ItemStack stack = this.dataTracker.get(ITEM);
        return stack.isEmpty() ? new ItemStack(ModItems.ORCHID_GRENADE) : stack;
    }
    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getStack();
        return itemStack.isEmpty() ? ParticleTypes.ASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        explode();
        super.onCollision(hitResult);
    }

    public void explode() {
        ItemStack stack = getStack();
        NbtCompound nbt = stack.getNbt();
        if(!stack.isEmpty() && nbt != null && nbt.contains("grenade") && !this.getWorld().isClient){
            NbtCompound nbtGrenade = nbt.getCompound("grenade");
            for (int i = 1; i < 4; i++) {
                OrchidType type = OrchidType.valueOf(nbtGrenade.getString("orchid_type_" + i));
                OrchidIntensity intensity = OrchidIntensity.valueOf(nbtGrenade.getString("orchid_intensity_" + i));
                float r = intensity.getRange();

                List<LivingEntity> entities = new ArrayList<>();
                for (Entity e : getWorld().getOtherEntities(this, new Box(-r + getX(), -r + getY(), -r + getZ(), r + getX(), r + getY(), r + getZ()), entity -> entity instanceof LivingEntity)) {
                    LivingEntity le = (LivingEntity) e;
                    if(le.getType() == EntityType.ENDER_DRAGON || le.getType() == EntityType.WITHER || le.getType() == EntityType.WARDEN) continue;
                    entities.add(le);
                }
                type.applyEffect(entities, intensity.getPower(), this, (LivingEntity) this.getOwner());
            }
            for (PlayerEntity p : getWorld().getPlayers()) {
                if(p.distanceTo(this) <= 60 && p instanceof ServerPlayerEntity sp){
                    ServerPlayNetworking.send(sp, ModMessages.GRENADE_EXPLOSION, ModPackets.grenadeExplosion(getX(), getY(), getZ(), stack));
                }
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
        if(this.getBlockStateAtPos().isOf(Blocks.LAVA) || this.getBlockStateAtPos().isOf(Blocks.FIRE)) {
            this.explode();
        }
        super.tick();
    }
}
