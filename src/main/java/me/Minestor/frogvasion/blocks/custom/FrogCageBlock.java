package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogCageBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty LOADED = Properties.ENABLED;
    public static final IntProperty FROG = IntProperty.of("frog",0,9);
    public FrogCageBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(LOADED,false).with(FROG,0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOADED, FROG);
        super.appendProperties(builder);
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.FROG_CAGE_TYPE.instantiate(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getInventory().getMainHandStack().getItem() == Items.SLIME_BALL && !state.get(LOADED)) {
            ItemStack stack = player.getInventory().getMainHandStack();
            state = state.with(LOADED,true);
            world.setBlockState(pos, state);
            world.updateComparators(pos,this);
            stack.decrement(1);
        }
        if(state.get(FROG) > 0 && !world.isClient) {
            summonFrog((ServerWorld) world, pos, state.get(FROG));
            world.setBlockState(pos, state.with(LOADED, false).with(FROG, 0));
            world.updateComparators(pos,this);
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
            summonFrog((ServerWorld) world, pos, state.get(FROG));
            world.updateComparators(pos,this);
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,0.5625,1);
    }

    public static void summonFrog(ServerWorld world, BlockPos pos, int defaultedInt) {
        ModFrog frog = ModEntities.getFrog(defaultedInt,world);
        if(!(frog instanceof TadpoleRocket)){
            frog.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            world.spawnEntityAndPassengers(frog);
        }
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof ModFrog mf && !(entity instanceof TadpoleRocket)) {
            if (state.get(LOADED) && !mf.isTamed()) {
                state = state.with(LOADED,false).with(FROG,ModEntities.getDefaultedInt(mf.getFrogType()));
                world.setBlockState(pos,state);
                world.updateComparators(pos, this);
                mf.discard();
            }
        }
        if(entity instanceof ItemEntity ie && !state.get(LOADED) && ie.getStack().getItem() == Items.SLIME_BALL) {
            state = state.with(LOADED,true).with(FROG, 0);
            world.setBlockState(pos, state);
            world.updateComparators(pos,this);
            ie.getStack().decrement(1);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return !state.get(LOADED);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(FROG);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.block.frog_cage", Text.translatable("item.minecraft.slime_ball")).formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
}
