package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidMagicSource;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.blocks.custom.FloradicAltarBlock;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.recipe.FloradicAltarRecipe;
import me.Minestor.frogvasion.util.items.ModTags;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FloradicAltarBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> inv = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private int progress = 0;
    private final int maxProgress = 100;
    public FloradicAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLORADIC_ALTAR_TYPE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inv;
    }
    public void setInv(DefaultedList<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            inv.set(i, list.get(i));
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, FloradicAltarBlockEntity entity) {
        if(world.isClient) {
            if(state.get(FloradicAltarBlock.CRAFTING)) {
                entity.progress++;
                Random rand = world.getRandom();
                world.addParticle(ParticleTypes.GLOW, pos.getX() + 0.5, pos.getY() + 0.55, pos.getZ() + 0.5, (double) rand.nextBetweenExclusive(-3, 3) / 10,
                        rand.nextFloat() / 2, (double) rand.nextBetweenExclusive(-3, 3) / 10);
                if(entity.progress >= entity.maxProgress) {
                    entity.resetProgress();
                    world.playSound(pos.getX() + 0.5, pos.getY() + 0.55, pos.getZ() +0.5, SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.BLOCKS, 1f, 1f, true);
                }
            } else {
                entity.resetProgress();
            }
        } else {
            if(hasRecipe(entity)) {
                entity.progress++;
                if(entity.progress >= entity.maxProgress) {
                    craftItem(entity);
                    entity.resetProgress();
                }
                markDirty(world, pos, state);
            } else {
                entity.resetProgress();
                markDirty(world, pos, state);
            }
            if(entity.progress > 0 && !state.get(FloradicAltarBlock.CRAFTING)) {
                world.setBlockState(pos, state.with(FloradicAltarBlock.CRAFTING, true));
            } else if (entity.progress == 0 && state.get(FloradicAltarBlock.CRAFTING)) {
                world.setBlockState(pos, state.with(FloradicAltarBlock.CRAFTING, false));
            }
        }
    }
    private void resetProgress() {
        this.progress = 0;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return side != null && insertItem(stack, side) > 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }
    public int insertItem(ItemStack stack, Direction dir) {
        int a = 0;
        if(dir.getAxis() == Direction.Axis.Y){
            for (int i = 0; i < this.size(); i++) {
                if (inv.get(i).isEmpty()) {
                    a = stack.getCount();
                    inv.set(i, stack);
                    break;
                }
            }
        } else {
            int slot = (dir.getAxis() == Direction.Axis.X) ? (dir == Direction.EAST ? 0 : 2) : (dir == Direction.SOUTH ? 1 : 3);
            if (inv.get(slot).isEmpty()) {
                a = stack.getCount();
                inv.set(slot, stack);
            }
        }

        return a;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inv);
        nbt.putInt("floradic_altar.progress", this.progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inv);
        super.readNbt(nbt);
        progress = nbt.getInt("floradic_altar.progress");
    }
    private static boolean hasRecipe(FloradicAltarBlockEntity entity) {
        ItemStack shell = ItemStack.EMPTY.copy();
        List<ItemStack> sources = new ArrayList<>();
        for (ItemStack ingr: entity.getItems()) {
            if(ingr.isEmpty()) return false;

            if (ingr.isOf(ModItems.ORCHID_GRENADE_SHELL)) {
                shell = ingr;
            }
            if(ingr.isIn(ModTags.ORCHID_MAGIC_SOURCES)) {
                sources.add(ingr);
            }
        }

        SimpleInventory sinv = new SimpleInventory(entity.size());
        for(int i = 0; i < entity.size();i++) {
            sinv.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<FloradicAltarRecipe>> match = entity.world.getRecipeManager().getFirstMatch(FloradicAltarRecipe.Type.INSTANCE, sinv, entity.world);

        return match.isPresent() || (!shell.isEmpty() && sources.size() == 3);
    }
    private static void craftItem(FloradicAltarBlockEntity entity) {
        SimpleInventory sinv = new SimpleInventory(entity.size());
        boolean isGrenadeCrafting = false;
        for(int i = 0; i< entity.size();i++) {
            sinv.setStack(i, entity.getStack(i));

            if(entity.getStack(i).isOf(ModItems.ORCHID_GRENADE_SHELL)) {
                isGrenadeCrafting = true;
            }
        }
        if(!isGrenadeCrafting){
            Optional<RecipeEntry<FloradicAltarRecipe>> recipe = entity.world.getRecipeManager().getFirstMatch(FloradicAltarRecipe.Type.INSTANCE, sinv, entity.world);
            if (hasRecipe(entity)) {
                entity.removeStack(0, 1);
                entity.removeStack(1, 1);
                entity.removeStack(2, 1);
                entity.removeStack(3, 1);

                entity.getWorld().spawnEntity(new ItemEntity(entity.getWorld(), entity.getPos().getX() + 0.5, entity.getPos().getY() + 0.6, entity.getPos().getZ() + 0.5,
                        recipe.get().value().getOutput()));
            }
        } else {
            if(hasRecipe(entity)) {
                List<ItemStack> flowers = new ArrayList<>();
                for (ItemStack contents : entity.getItems()) {
                    if(contents.isIn(ModTags.ORCHID_MAGIC_SOURCES)) {
                        flowers.add(contents);
                    }
                }

                if(flowers.size() == 3){
                    NbtCompound nbtGrenade = new NbtCompound();
                    for (int i = 0; i < 3; i++) {
                        if(flowers.get(i).isOf(Items.BLUE_ORCHID)) {
                            nbtGrenade.putString("orchid_type_" + (i + 1), OrchidType.BLUE.toString());
                            nbtGrenade.putString("orchid_intensity_" + (i + 1), OrchidIntensity.FLOWER.toString());
                        } else {
                            nbtGrenade.putString("orchid_type_" + (i + 1), ((OrchidMagicSource) flowers.get(i).getItem()).getOrchidType().toString());
                            nbtGrenade.putString("orchid_intensity_" + (i + 1), ((OrchidMagicSource) flowers.get(i).getItem()).getOrchidIntensity().toString());
                        }
                    }
                    entity.removeStack(0, 1);
                    entity.removeStack(1, 1);
                    entity.removeStack(2, 1);
                    entity.removeStack(3, 1);

                    NbtCompound nbt = new NbtCompound();
                    nbt.put("grenade", nbtGrenade);

                    ItemStack grenade = ModItems.ORCHID_GRENADE.getDefaultStack();
                    grenade.setNbt(nbt);

                    entity.getWorld().spawnEntity(new ItemEntity(entity.getWorld(), entity.getPos().getX() + 0.5, entity.getPos().getY() + 0.6, entity.getPos().getZ() + 0.5,
                            grenade));
                }
            }
        }
    }

    @Override
    public void markDirty() {
        if(!world.isClient) {
            for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.FLORADIC_S2C, ModPackets.createFloradicUpdate(pos, inv, progress));
            }
        }
        super.markDirty();
    }
}
