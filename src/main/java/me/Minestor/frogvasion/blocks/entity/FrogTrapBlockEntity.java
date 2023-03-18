package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogTrapBlock;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.UpdateTrapPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrogTrapBlockEntity extends BlockEntity implements ImplementedInventory{
    private final DefaultedList<ItemStack> inv = DefaultedList.ofSize(1, ItemStack.EMPTY);


    public FrogTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROG_TRAP_TYPE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inv;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inv);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inv);
        super.readNbt(nbt);
    }
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return slot == 0 && stack.getItem() == Items.SLIME_BALL;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    public void insertSlimeballs(int amount, PlayerEntity p) {
        final int m = amountSlimeballs()+amount;
        final int min = Math.min(m,64);
        inv.set(0, new ItemStack(Items.SLIME_BALL, min));
        if(min == 64) {
            p.getInventory().getMainHandStack().setCount(m - 64);
        } else {
            p.getInventory().getMainHandStack().setCount(0);
        }
        this.getWorld().updateComparators(this.getPos(),this.getWorld().getBlockState(this.getPos()).getBlock());
    }
    public void decreaseSlimeballs(int amount) {
        if(!this.getWorld().getBlockState(this.getPos()).get(FrogTrapBlock.LOADED)) return;
        int count = Math.max(inv.get(0).getCount()- amount, 0);
        inv.set(0, new ItemStack(Items.SLIME_BALL, count));
        this.getWorld().updateComparators(this.getPos(),this.getWorld().getBlockState(this.getPos()).getBlock());
    }
    public int amountSlimeballs() {
        return inv.get(0).getCount();
    }

    public static void tick(World world, BlockPos pos, BlockState state, FrogTrapBlockEntity be) {
        if(!world.isClient) {
            for(Entity e : world.getOtherEntities(null,new Box(pos).expand(64), et -> et instanceof PlayerEntity)) {
                ServerPlayerEntity pl = (ServerPlayerEntity) e;
                ServerPlayNetworking.send(pl, ModMessages.UPDATE_TRAP, UpdateTrapPacket.createUpdate(pos,be.amountSlimeballs()));
            }
            if(be.amountSlimeballs() > 0 && !state.get(FrogTrapBlock.LOADED)) {
                state = state.with(FrogTrapBlock.LOADED,true);
                world.setBlockState(pos,state);
                world.updateComparators(pos,state.getBlock());
            } else if (state.get(FrogTrapBlock.LOADED)) {
                state = state.with(FrogTrapBlock.LOADED,false);
                world.setBlockState(pos,state);
                world.updateComparators(pos,state.getBlock());
            }
        }
    }
}
