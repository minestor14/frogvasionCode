package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.entity.MailBoxBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MailBoxBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty MAIL = IntProperty.of("mail",0,10);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public MailBoxBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(MAIL,0).with(FACING,Direction.NORTH));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MAIL);
        builder.add(FACING);
        super.appendProperties(builder);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MAILBOX_TYPE.instantiate(pos,state);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return state.get(MAIL) >0;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(MAIL);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.MAILBOX_TYPE, MailBoxBlockEntity::tick);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    private boolean doubleprevent = false;
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(state.get(MAIL) > 0 && !world.isClient){
            MailBoxBlockEntity be = (MailBoxBlockEntity) world.getBlockEntity(pos);
            if(!doubleprevent) player.sendMessage(Text.literal(be.getAndRemoveOldestMail()));

            doubleprevent = !doubleprevent;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
