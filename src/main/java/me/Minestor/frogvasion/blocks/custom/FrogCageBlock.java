package me.Minestor.frogvasion.blocks.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.MapCodec;
import me.Minestor.frogvasion.blocks.entity.FrogCageBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.FrogTypes;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.util.entity.GuideUnlocked;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
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
    public static final MapCodec<FrogCageBlock> CODEC = createCodec(FrogCageBlock::new);
    public static final BooleanProperty LOADED = Properties.ENABLED;
    public static final IntProperty FROG = IntProperty.of("frog",0,9);
    public FrogCageBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(LOADED,false).with(FROG,0));
    }
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            world.updateComparators(pos,this);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(LOADED)) {
            ItemStack stack = player.getInventory().getMainHandStack();
            if(stack.getItem() == Items.SLIME_BALL && !world.isClient){
                state = state.with(LOADED, true);
                world.setBlockState(pos, state);
                world.updateComparators(pos, this);
                stack.decrement(1);
            } else if (stack.getItem() == ModItems.HERPETOLOGIST_JAR && !world.isClient) {
                NbtCompound nbt = stack.getOrCreateNbt();
                FrogCageBlockEntity be = world.getBlockEntity(pos, ModBlockEntities.FROG_CAGE_TYPE).get();

                if(nbt.contains("frog_type") && state.get(FROG) == 0) {
                    be.setFrogNbt(nbt.get("frog_nbt").asString());

                    state = state.with(FROG, FrogTypes.valueOf(nbt.getString("frog_type")).getId());
                    world.setBlockState(pos, state);

                    nbt.remove("frog_type");
                    nbt.remove("frog_nbt");
                    stack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
                } else if (!nbt.contains("frog_type") && state.get(FROG) > 0){
                    nbt.putString("frog_type", FrogTypes.getById(state.get(FROG)).name());
                    try {
                        nbt.put("frog_nbt", StringNbtReader.parse(be.getFrogNbt()));
                    } catch (CommandSyntaxException ignored) {}
                    stack.setNbt(nbt);
                    player.getInventory().setStack(player.getInventory().selectedSlot, stack);
                    GuideUnlocked.modifyUnlocked((IEntityDataSaver) player, FrogTypes.getById(state.get(FROG)), true);
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ModMessages.UPDATE_GUIDE, ModPackets.guideUpdate((IEntityDataSaver) player));

                    world.setBlockState(pos, state.with(LOADED, false).with(FROG, 0));
                    world.updateComparators(pos,this);
                }
            }
            return ActionResult.success(world.isClient);
        }
        if(state.get(FROG) > 0 && !world.isClient) {
            summonFrog((ServerWorld) world, pos, state.get(FROG));
            world.setBlockState(pos, state.with(LOADED, false).with(FROG, 0));
            world.updateComparators(pos,this);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,0.5625,1);
    }

    public static void summonFrog(ServerWorld world, BlockPos pos, int id) {
        ModFrog frog = ModEntities.getFrog(id, world);
        if(!(frog instanceof TadpoleRocket)){
            try {
                frog.setNbt(StringNbtReader.parse(world.getBlockEntity(pos, ModBlockEntities.FROG_CAGE_TYPE).get().getFrogNbt()));
            } catch (Exception ignored) {}

            frog.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            world.spawnEntityAndPassengers(frog);
        }
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof ModFrog mf && !(entity instanceof TadpoleRocket)) {
            if (state.get(LOADED) && !mf.isTamed() && !mf.isDead()) {
                state = state.with(LOADED,false).with(FROG, mf.getFrogType().getId());
                world.setBlockState(pos,state);
                world.updateComparators(pos, this);

                FrogCageBlockEntity be = world.getBlockEntity(pos, ModBlockEntities.FROG_CAGE_TYPE).get();
                be.setFrogNbt(NbtPredicate.entityToNbt(mf).asString());

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
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.block.frog_cage", Text.translatable("item.minecraft.slime_ball")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if(nbt != null && nbt.contains("frog_type") && nbt.getInt("frog_type") != 0) {
            tooltip.add(FrogTypes.getById(nbt.getInt("frog_type")).getTranslation().formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ItemStack stack = ctx.getStack();
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if(nbt != null && nbt.contains("frog_type")) {
            return getDefaultState().with(FROG, nbt.getInt("frog_type"));
        }

        return super.getPlacementState(ctx);
    }
}
