package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.entity.FrogTrapBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.util.ModDamageSources;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrogTrapBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty LOADED = Properties.ENABLED;
    public FrogTrapBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LOADED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOADED);
        super.appendProperties(builder);
    }

    public void toggleLoaded(BlockState state) {
        state.cycle(LOADED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.FROG_TRAP_TYPE.instantiate(pos,state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getInventory().getMainHandStack().getItem() == Items.SLIME_BALL) {
            ItemStack stack = player.getInventory().getMainHandStack();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogTrapBlockEntity be) {
                be.insertSlimeballs(stack.getCount());
                player.clearActiveItem();
                toggleLoaded(state);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.bypassesSteppingEffects() && entity instanceof ModFrog) {
            entity.kill();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogTrapBlockEntity be) {
                be.decreaseSlimeballs(1);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
