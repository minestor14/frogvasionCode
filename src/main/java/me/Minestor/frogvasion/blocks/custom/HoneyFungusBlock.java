package me.Minestor.frogvasion.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HoneyFungusBlock extends PlantBlock implements Waterloggable, Fertilizable {
    public static final BooleanProperty WATERLOGGED;
    public HoneyFungusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
    public boolean canMobSpawnInside(BlockState state) {
        return true;
    }
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)random.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = 0;
        for (int j = 0; j < 9; j++) {
            if(i > 4) break;
            BlockPos pos1 = pos.add(random.nextBetween(-2,2), random.nextBetween(-2,2), random.nextBetween(-2,2));
            BlockState state1 = world.getBlockState(pos1);

            if(state1.isIn(BlockTags.AZALEA_GROWS_ON) || state1.isIn(BlockTags.LOGS)) {
                if(world.getBlockState(pos1.up()).isIn(BlockTags.REPLACEABLE)) {
                    world.setBlockState(pos1.up(), this.getDefaultState().with(WATERLOGGED, world.isWater(pos1.up())));
                    i++;
                }
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,0.4,1);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
