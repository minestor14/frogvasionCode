package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogTrapBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

    public void insertSlimeballs(int amount) {
        inv.set(0, new ItemStack(Items.SLIME_BALL, amount));
    }
    public void decreaseSlimeballs(int amount) {
        if(!this.getWorld().getBlockState(this.getPos()).get(FrogTrapBlock.LOADED)) return;
        int count = Math.max(inv.get(0).getCount()- amount, 0);
        inv.set(0, new ItemStack(Items.SLIME_BALL, count));
        if(inv.get(0).getCount() == 0) this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).with(FrogTrapBlock.LOADED, false));
    }
    public int amountSlimeballs() {
        return inv.get(0).getCount();
    }
    public boolean isLoaded() {return inv.get(0).getCount() > 0;}
}
