package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidMagicSource;
import me.Minestor.frogvasion.blocks.OrchidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class OrchidBlock extends FlowerBlock implements OrchidMagicSource {
    final OrchidType type;
    public OrchidBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, OrchidType type) {
        super(suspiciousStewEffect, effectDuration, settings);
        this.type = type;
    }

    @Override
    public OrchidType getOrchidType() {
        return type;
    }

    @Override
    public OrchidIntensity getOrchidIntensity() {
        return OrchidIntensity.FLOWER;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.SAVANNA_SOIL);
    }
}
