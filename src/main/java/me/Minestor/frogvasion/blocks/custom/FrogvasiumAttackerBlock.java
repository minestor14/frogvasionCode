package me.Minestor.frogvasion.blocks.custom;


import me.Minestor.frogvasion.blocks.entity.FrogvasiumAttackerBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrogvasiumAttackerBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING;
    public FrogvasiumAttackerBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.FROGVASIUM_ATTACKER_TYPE.instantiate(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(FACING, context.getPlayerLookDirection().getOpposite());
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean redstone = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        if (!world.isClient()) {
            BlockPointerImpl blockPointerImpl = new BlockPointerImpl((ServerWorld)world, pos);
            FrogvasiumAttackerBlockEntity be = blockPointerImpl.getBlockEntity();
            if(!redstone){
                be.cooldown = 0;
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FROGVASIUM_ATTACKER_TYPE, FrogvasiumAttackerBlockEntity::tick);
    }

    static {
        FACING = Properties.FACING;
    }
}
