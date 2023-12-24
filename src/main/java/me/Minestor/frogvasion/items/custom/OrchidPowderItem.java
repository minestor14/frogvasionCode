package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidMagicSource;
import me.Minestor.frogvasion.blocks.OrchidType;
import net.minecraft.item.Item;

public class OrchidPowderItem extends Item implements OrchidMagicSource {
    OrchidType type;
    public OrchidPowderItem(Settings settings, OrchidType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public OrchidType getOrchidType() {
        return type;
    }

    @Override
    public OrchidIntensity getOrchidIntensity() {
        return OrchidIntensity.POWDER;
    }
}