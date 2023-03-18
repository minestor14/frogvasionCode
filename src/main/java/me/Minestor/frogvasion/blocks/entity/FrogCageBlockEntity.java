package me.Minestor.frogvasion.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class FrogCageBlockEntity extends BlockEntity{
    public FrogCageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROG_CAGE_TYPE, pos, state);
    }
}