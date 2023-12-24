package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.util.items.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class BlackOrchidBlock extends OrchidBlock {
    public BlackOrchidBlock(Settings settings) {
        super(StatusEffects.WITHER, 20, settings, OrchidType.BLACK);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (int i = 0; i < 6; i++) {
            int x = random.nextBetween(-3, 3), y = random.nextBetween(-3, 3), z = random.nextBetween(-3, 3);
            if (world.getBlockState(pos.add(x, y, z)).isIn(ModTags.ORCHIDS)) {
                world.setBlockState(pos.add(x,y,z), ModBlocks.BLACK_ORCHID.getDefaultState());
                break;
            }
        }
    }
}
