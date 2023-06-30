package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.worldgen.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {
    @Shadow public World world;
    @Shadow public abstract BlockPos getBlockPos();
    @Shadow public abstract Vec3d getVelocity();
    @Shadow public abstract float getYaw();
    @Shadow public abstract float getPitch();

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Inject(method = "getTeleportTarget", at = @At("HEAD"), cancellable = true)
    public void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
        if(destination.getRegistryKey() == ModDimensions.GREENWOOD_DIMENSION_KEY || this.world.getRegistryKey() == ModDimensions.GREENWOOD_DIMENSION_KEY) {

            cir.setReturnValue(new TeleportTarget(new Vec3d(this.getX(),this.getY(),this.getZ()), this.getVelocity(),this.getYaw(), this.getPitch()));
        }
    }
}
