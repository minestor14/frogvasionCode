package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidMagicSource;
import me.Minestor.frogvasion.blocks.OrchidType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class OrchidBlockItem extends BlockItem implements OrchidMagicSource {
    final OrchidType type;
    final OrchidIntensity intensity;
    public OrchidBlockItem(Block block, Settings settings, OrchidType type, OrchidIntensity intensity) {
        super(block, settings);
        this.type = type;
        this.intensity = intensity;
    }

    @Override
    public OrchidType getOrchidType() {
        return type;
    }

    @Override
    public OrchidIntensity getOrchidIntensity() {
        return intensity;
    }
}
