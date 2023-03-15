package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.entity.FrogCageBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrogCageBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty LOADED = Properties.ENABLED;
    public static final IntProperty FROG = IntProperty.of("frog",0,9);
    public FrogCageBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(LOADED,false).with(FROG,0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOADED,FROG);
        super.appendProperties(builder);
    }

    public void toggleLoaded(BlockState state) {
        state.cycle(LOADED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.FROG_CAGE_TYPE.instantiate(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getInventory().getMainHandStack().getItem() == Items.SLIME_BALL) {
            ItemStack stack = player.getInventory().getMainHandStack();
            toggleLoaded(state);
            world.setBlockState(pos, state);
            stack.decrement(1);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(world.isClient()) return;
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogCageBlockEntity be) {
                summonFrog((ServerWorld) world, pos, be.getDefaultedFrog());
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,0.1,1);
    }

    public static void summonFrog(ServerWorld world, BlockPos pos, int defaultedInt) {
        ModFrog frog = ModEntities.getFrog(defaultedInt,world);
        frog.setPos(pos.getX() + 0.5,pos.getY() + 0.1,pos.getZ() + 0.5);
        world.spawnEntityAndPassengers(frog);
    }
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof ModFrog mf && !(entity instanceof TadpoleRocket)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogCageBlockEntity be && state.get(LOADED)) {
                be.setDefaultedFrog(ModEntities.getDefaultedInt(mf.getFrogType()));
                state = state.with(LOADED,false);
                world.setBlockState(pos,state);
                world.updateComparators(pos, this);
                mf.kill();
                mf.discard(); //todo maybe exception to tamed frogs
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return !state.get(LOADED);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FrogCageBlockEntity be) {
            return be.getDefaultedFrog();
        }
        return 0;
    }
}