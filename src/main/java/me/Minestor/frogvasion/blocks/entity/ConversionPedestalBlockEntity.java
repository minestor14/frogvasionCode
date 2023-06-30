package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.recipe.ConversionPedestalRecipe;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.screen.ConversionPedestalScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConversionPedestalBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inv = DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate pd;
    private int progress = 0;
    private int maxProgress = 150;
    public ConversionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONVERSION_PEDESTAL_TYPE, pos, state);
        this.pd = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ConversionPedestalBlockEntity.this.progress;
                    case 1 -> ConversionPedestalBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ConversionPedestalBlockEntity.this.progress = value;
                    case 1 -> ConversionPedestalBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inv;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("text.block.conversion_pedestal_block");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ConversionPedestalScreenHandler(syncId,inv,this,this.pd);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inv);
        nbt.putInt("conversion_pedestal.progress", this.progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inv);
        super.readNbt(nbt);
        progress = nbt.getInt("conversion_pedestal.progress");
    }
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if(side == Direction.UP) {
            return slot == 0 && stack.getItem() == ModItems.EMPTY_FROG_GHOST;
        }
        if (side == Direction.EAST || side == Direction.NORTH ||side == Direction.SOUTH ||side == Direction.WEST) {
            return slot == 1;
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if(side == Direction.UP) {
            return false;
        }

        if(side == Direction.DOWN) {
            return slot == 2;
        }
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ConversionPedestalBlockEntity entity) {
        if(world.isClient()) return;
        if(hasRecipe(entity)) {
            entity.progress++;
            markDirty(world,pos,state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
                entity.resetProgress();
            }
        } else {
            entity.resetProgress();
            markDirty(world,pos,state);
        }
    }
    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ConversionPedestalBlockEntity entity) {
        SimpleInventory sinv = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size();i++) {
            sinv.setStack(i, entity.getStack(i));
        }
        Optional<ConversionPedestalRecipe> recipe = entity.world.getRecipeManager().getFirstMatch(ConversionPedestalRecipe.Type.INSTANCE, sinv, entity.world);
        if(hasRecipe(entity)) {
            entity.removeStack(0,1);
            entity.removeStack(1,1);
            entity.setStack(2, new ItemStack(recipe.get().getOutput().getItem(), entity.getStack(2).getCount() + 1));
        }
    }

    private static boolean hasRecipe(ConversionPedestalBlockEntity entity) {
        SimpleInventory sinv = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size();i++) {
            sinv.setStack(i, entity.getStack(i));
        }
        Optional<ConversionPedestalRecipe> match = entity.world.getRecipeManager().getFirstMatch(ConversionPedestalRecipe.Type.INSTANCE, sinv, entity.world);

        return match.isPresent() && canAmountFitOutputSlot(sinv,1) && canInsertItemIntoOutputSlot(sinv, match.get().getOutput().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory sinv, Item output) {
        return sinv.getStack(2).getItem() == output.asItem() || sinv.getStack(2).isEmpty();
    }

    private static boolean canAmountFitOutputSlot(SimpleInventory sinv, int amount) {
        return sinv.getStack(2).getMaxCount() >= sinv.getStack(2).getCount() + amount;
    }
}
