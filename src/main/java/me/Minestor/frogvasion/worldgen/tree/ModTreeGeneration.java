package me.Minestor.frogvasion.worldgen.tree;

import me.Minestor.frogvasion.worldgen.features.ModFeaturesPlacing;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModFeaturesPlacing.RUBBER_TREE_PLACED_KEY);
    }
}
