package me.Minestor.frogvasion.worldgen.tree;

import me.Minestor.frogvasion.mixin.Mixins.FoliagePlacerTypeInvoker;
import me.Minestor.frogvasion.mixin.Mixins.TrunkPlacerTypeInvoker;
import me.Minestor.frogvasion.worldgen.features.ModPlacedFeatures;
import me.Minestor.frogvasion.worldgen.tree.tropical_acacia.TropicalAcaciaFoliagePlacer;
import me.Minestor.frogvasion.worldgen.tree.tropical_acacia.TropicalAcaciaTrunkPlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTreeGeneration {
    public static final FoliagePlacerType<?> TROPICAL_ACACIA_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("tropical_acacia_foliage", TropicalAcaciaFoliagePlacer.CODEC);
    public static final TrunkPlacerType<?> TROPICAL_ACACIA_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("tropical_acacia_placer", TropicalAcaciaTrunkPlacer.CODEC);
    public static void registerTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RUBBER_TREE_PLACED_KEY);

    }
}
