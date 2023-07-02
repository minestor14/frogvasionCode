package me.Minestor.frogvasion.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailBlock;
import net.minecraft.block.RailPlacementHelper;
import net.minecraft.block.enums.RailShape;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class FrogvasiumRailBlock extends RailBlock {
    public FrogvasiumRailBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SHAPE, RailShape.NORTH_SOUTH).with(WATERLOGGED, false));
    }

    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor) {
        if (neighbor.getDefaultState().emitsRedstonePower() && (new RailPlacementHelper(world, pos, state)).getNeighbors().size() == 3) {
            this.updateBlockState(world, pos, state, false);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos,  Entity entity) {
        if(!world.isClient()) {
            List<AbstractMinecartEntity> list = this.getCarts(world, pos, AbstractMinecartEntity.class, (entity1) -> true);
            if(!list.isEmpty()) {
                for(AbstractMinecartEntity e : list) {
                    e.setVelocity(e.getVelocity().add(0,0.4,0));
                }
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }



    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                switch (state.get(SHAPE)) {
                    case ASCENDING_EAST:
                        return state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                }
            case COUNTERCLOCKWISE_90:
                return switch (state.get(SHAPE)) {
                    case ASCENDING_EAST -> state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST -> state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH -> state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH -> state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST -> state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST -> state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST -> state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST -> state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_SOUTH -> state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST -> state.with(SHAPE, RailShape.NORTH_SOUTH);
                };
            case CLOCKWISE_90:
                return switch (state.get(SHAPE)) {
                    case ASCENDING_EAST -> state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST -> state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH -> state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH -> state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case SOUTH_EAST -> state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST -> state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST -> state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST -> state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_SOUTH -> state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST -> state.with(SHAPE, RailShape.NORTH_SOUTH);
                };
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        RailShape railShape = state.get(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                return switch (railShape) {
                    case ASCENDING_NORTH -> state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH -> state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST -> state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST -> state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST -> state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST -> state.with(SHAPE, RailShape.SOUTH_EAST);
                    default -> super.mirror(state, mirror);
                };
            case FRONT_BACK:
                switch (railShape) {
                    case ASCENDING_EAST:
                        return state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(state, mirror);
    }
    private <T extends AbstractMinecartEntity> List<T> getCarts(World world, BlockPos pos, Class<T> entityClass, Predicate<Entity> entityPredicate) {
        return world.getEntitiesByClass(entityClass, this.getCartDetectionBox(pos), entityPredicate);
    }

    private Box getCartDetectionBox(BlockPos pos) {
        final double d = 0.7;
        final double e = 0.3;
        return new Box((double)pos.getX() + d, pos.getY(), (double)pos.getZ() + d, (double)pos.getX() + e, (double)pos.getY() + e, (pos.getZ() + e));
    }

}
