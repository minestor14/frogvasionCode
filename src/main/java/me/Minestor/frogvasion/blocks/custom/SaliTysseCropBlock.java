package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SaliTysseCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 5);

    public SaliTysseCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.MUD_FARMLAND);
    }
    @Override
    public int getMaxAge() {
        return 5;
    }
    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.SALI_TYSSE_SEEDS;
    }

}

