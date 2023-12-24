package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidMagicSource;
import me.Minestor.frogvasion.blocks.OrchidType;
import net.minecraft.item.Item;

public class OrchidMixItem extends Item implements OrchidMagicSource {
    public OrchidMixItem(Settings settings) {
        super(settings);
    }

    @Override
    public OrchidType getOrchidType() {
        return OrchidType.MIX;
    }

    @Override
    public OrchidIntensity getOrchidIntensity() {
        return OrchidIntensity.FLOWER;
    }
}
