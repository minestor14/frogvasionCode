package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrogvasiumBlock extends Block {
    public FrogvasiumBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ModFrog m) {
            if(m.isInfused() && world.getBlockState(pos.add(0,1,0)).isOf(Blocks.AIR)) {
                world.setBlockState(pos.add(0,1,0), ModBlocks.FROG_FLAME.getDefaultState());
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
