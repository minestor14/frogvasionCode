package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.worldgen.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class GreenwoodPortalBlock extends Block {
    public GreenwoodPortalBlock(Settings settings) {
        super(settings);
    }
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals() && !entity.hasPortalCooldown()) {
            if (entity.getWorld() instanceof ServerWorld serverWorld && entity instanceof LivingEntity le) {
                MinecraftServer minecraftServer = serverWorld.getServer();
                RegistryKey<World> registryKey = le.getWorld().getRegistryKey() == ModDimensions.GREENWOOD_DIMENSION_KEY ? World.OVERWORLD : ModDimensions.GREENWOOD_DIMENSION_KEY;
                ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);

                if (serverWorld2 != null) {
                    le.resetPortalCooldown();
                    le.setInNetherPortal(pos);
                    le.moveToWorld(serverWorld2);
                    generatePortal(serverWorld2, pos, entity);
                }
            }
        }
        entity.resetPortalCooldown();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(direction == Direction.DOWN && !hasBase(neighborState)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
    public static void generatePortal(ServerWorld world, BlockPos pos, Entity entity) {
        if(world.getBlockState(pos).isOf(ModBlocks.GREENWOOD_PORTAL) && hasBase(world.getBlockState(pos.down()))) {
            return;
        }
        square(world, pos, Blocks.AIR.getDefaultState());
        square(world, pos.up(), Blocks.AIR.getDefaultState());
        square(world, pos.down(), Blocks.STONE.getDefaultState());
        if(entity.getType() == EntityType.PLAYER) { //prevention of frogvasium farm by sending normal entities through
            world.setBlockState(pos, ModBlocks.GREENWOOD_PORTAL.getDefaultState());
            world.setBlockState(pos.down(), ModBlocks.FROGVASIUM_BLOCK.getDefaultState());
        }
    }
    private static void square(ServerWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state);
        world.setBlockState(pos.west(), state);
        world.setBlockState(pos.north(), state);
        world.setBlockState(pos.south(), state);
        world.setBlockState(pos.east(), state);
        world.setBlockState(pos.west().north(), state);
        world.setBlockState(pos.north().east(), state);
        world.setBlockState(pos.south().west(), state);
        world.setBlockState(pos.east().south(), state);
    }

    public static boolean hasBase(BlockState below) {
        return below.isOf(ModBlocks.FROGVASIUM_BLOCK) || below.isOf(ModBlocks.RAW_FROGVASIUM_BLOCK);
    }
}
