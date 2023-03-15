package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogCageBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class FrogCageBlockEntity extends BlockEntity{
    public int defaultedFrog = 0;
    public FrogCageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROG_CAGE_TYPE, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("frog_cage.frog", this.getWorld().getBlockState(this.getPos()).get(FrogCageBlock.FROG));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        defaultedFrog = nbt.getInt("frog_cage.frog");
    }

    public NbtCompound getNbt(NbtCompound nbt) {
        writeNbt(nbt);
        return nbt;
    }

    public int getDefaultedFrog() {
        return defaultedFrog;
    }

    public void setDefaultedFrog(int defaultedFrog) {
        this.defaultedFrog = defaultedFrog;
    }

}