package me.Minestor.frogvasion.blocks.custom;

import com.mojang.serialization.MapCodec;
import me.Minestor.frogvasion.blocks.entity.FrogTrapBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.util.entity.ModDamageSources;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrogTrapBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<FrogTrapBlock> CODEC = createCodec(FrogTrapBlock::new);
    public static final BooleanProperty LOADED = Properties.ENABLED;
    public FrogTrapBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LOADED, false));
    }
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOADED);
        super.appendProperties(builder);
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
        if(player.getInventory().getMainHandStack().getItem() == Items.SLIME_BALL && !world.isClient) {
            ItemStack stack = player.getInventory().getMainHandStack();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogTrapBlockEntity be) {
                be.insertSlimeballs(stack.getCount(), player);
                world.updateComparators(pos, this);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }


    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.bypassesSteppingEffects() && entity instanceof ModFrog mf && !mf.isDead() && !world.isClient) {
            entity.damage(world.getDamageSources().create(ModDamageSources.FROGVASIUM_ATTACK_KEY), 5f);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FrogTrapBlockEntity be && mf.isDead()) {
                be.decreaseSlimeballs(1);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.FROG_TRAP_TYPE, FrogTrapBlockEntity::tick);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return state.get(LOADED);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof FrogTrapBlockEntity te) {
            return (int) Math.round(((double)te.amountSlimeballs()/64)*15);
        }
        return 0;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(world.isClient()) return;
        if (state.getBlock() != newState.getBlock()) {
            ItemScatterer.spawn(world,pos,world.getBlockEntity(pos,ModBlockEntities.FROG_TRAP_TYPE).get().getItems());
            world.updateComparators(pos,this);
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
