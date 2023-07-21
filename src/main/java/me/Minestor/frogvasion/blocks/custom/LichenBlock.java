package me.Minestor.frogvasion.blocks.custom;

import net.minecraft.block.GlowLichenBlock;

public class LichenBlock extends GlowLichenBlock {
    public LichenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float getSlipperiness() {
        return 0.9f;
    }
}
