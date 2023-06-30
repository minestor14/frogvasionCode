package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class FrogFlame extends Block {
    private static final int SET_ON_FIRE_SECONDS = 8;
    private final float damage;
    protected static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    public FrogFlame(Settings settings) {
        super(settings);

        damage = 1.5f;
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return this.canPlaceAt(state, world, pos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return isFrogvasiumBase(world.getBlockState(pos.down()));
    }

    public static boolean isFrogvasiumBase(BlockState state) {
        return state.isOf(ModBlocks.FROGVASIUM_BLOCK) || state.isOf(ModBlocks.RAW_FROGVASIUM_BLOCK);
    }

    protected boolean isFlammable(BlockState state) {
        return true;
    }
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(24) == 0) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        int i;
        double d;
        double e;
        double f;
        if (!this.isFlammable(blockState) && !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            if (this.isFlammable(world.getBlockState(pos.west()))) {
                for(i = 0; i < 2; ++i) {
                    d = (double)pos.getX() + random.nextDouble() * 0.10000000149011612;
                    e = (double)pos.getY() + random.nextDouble();
                    f = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
                }
            }

            if (this.isFlammable(world.getBlockState(pos.east()))) {
                for(i = 0; i < 2; ++i) {
                    d = (double)(pos.getX() + 1) - random.nextDouble() * 0.10000000149011612;
                    e = (double)pos.getY() + random.nextDouble();
                    f = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
                }
            }

            if (this.isFlammable(world.getBlockState(pos.north()))) {
                for(i = 0; i < 2; ++i) {
                    d = (double)pos.getX() + random.nextDouble();
                    e = (double)pos.getY() + random.nextDouble();
                    f = (double)pos.getZ() + random.nextDouble() * 0.10000000149011612;
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
                }
            }

            if (this.isFlammable(world.getBlockState(pos.south()))) {
                for(i = 0; i < 2; ++i) {
                    d = (double)pos.getX() + random.nextDouble();
                    e = (double)pos.getY() + random.nextDouble();
                    f = (double)(pos.getZ() + 1) - random.nextDouble() * 0.10000000149011612;
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
                }
            }

            if (this.isFlammable(world.getBlockState(pos.up()))) {
                for(i = 0; i < 2; ++i) {
                    d = (double)pos.getX() + random.nextDouble();
                    e = (double)(pos.getY() + 1) - random.nextDouble() * 0.10000000149011612;
                    f = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
                }
            }
        } else {
            for(i = 0; i < 3; ++i) {
                d = (double)pos.getX() + random.nextDouble();
                e = (double)pos.getY() + random.nextDouble() * 0.5 + 0.5;
                f = (double)pos.getZ() + random.nextDouble();
                world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
            }
        }

    }
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 1);
            if (entity.getFireTicks() == 0) {
                entity.setOnFireFor(SET_ON_FIRE_SECONDS);
            }
        }

        entity.damage(DamageSource.IN_FIRE, this.damage);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ModItems.FROG_FIRE_CHARGE.getDefaultStack();
    }
}
