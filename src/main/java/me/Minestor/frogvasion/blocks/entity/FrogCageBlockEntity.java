package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogCageBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class FrogCageBlockEntity extends BlockEntity{
    private String frogNbt = "";
    private int frogType = 0;
    public FrogCageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROG_CAGE_TYPE, pos, state);
    }

    public String getFrogNbt() {
        return frogNbt;
    }

    public void setFrogNbt(String frogNbt) {
        this.frogNbt = frogNbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        frogType = getCachedState().get(FrogCageBlock.FROG);
        nbt.putInt("frog_type", frogType);
        if(frogType == 0) {
            frogNbt = "";
        }
        nbt.putString("frog_nbt", frogNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        frogNbt = nbt.getString("frog_nbt");
        frogType = nbt.getInt("frog_type");
    }
}