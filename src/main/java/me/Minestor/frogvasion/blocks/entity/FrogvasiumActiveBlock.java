package me.Minestor.frogvasion.blocks.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FrogvasiumActiveBlock {
    default boolean isReceivingPower(BlockPos pos, World world) {
        return world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
    }
    boolean canPlay(World world, BlockPos pos);
}
