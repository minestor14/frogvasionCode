package me.Minestor.frogvasion.mixin.Mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractDecorationEntity.class)
public abstract class AbstractDecorationEntityMixin extends Entity {
    @Shadow protected BlockPos attachmentPos;
    public AbstractDecorationEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"), cancellable = true)
    private void readMoreData(NbtCompound nbt, CallbackInfo ci) {
        BlockPos blockPos = new BlockPos(nbt.getInt("TileX"), nbt.getInt("TileY"), nbt.getInt("TileZ"));
        if (blockPos.isWithinDistance(this.getBlockPos(), 16.0)) {
            this.attachmentPos = blockPos;
        }
        ci.cancel();
    }
}
