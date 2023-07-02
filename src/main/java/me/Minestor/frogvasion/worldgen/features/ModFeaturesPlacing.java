package me.Minestor.frogvasion.worldgen.features;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.worldgen.biomes.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModFeaturesPlacing {

    public static final RegistryKey<PlacedFeature> FROG_TOWER_CRATES_PLACED_KEY = registerKey("frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> NETHER_FROG_TOWER_CRATES_PLACED_KEY = registerKey("nether_frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> END_FROG_TOWER_CRATES_PLACED_KEY = registerKey("end_frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> FROGVASIUM_PLACED_KEY = registerKey("frogvasium_placed");
    public static final RegistryKey<PlacedFeature> RUBBER_TREE_PLACED_KEY = registerKey("rubber_tree_placed");
    public static final RegistryKey<PlacedFeature> ORCHID_PLACED_KEY = registerKey("orchid_placed");
    public static final RegistryKey<PlacedFeature> BROMELIAD_PLACED_KEY = registerKey("bromeliad_placed");
    public static final RegistryKey<PlacedFeature> KAURI_TREE_PLACED_KEY = registerKey("kauri_tree_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, FROG_TOWER_CRATES_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.TOWER_CRATES_KEY),
                modifiersWithCount(18, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
        register(context, NETHER_FROG_TOWER_CRATES_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_TOWER_CRATES_KEY),
                modifiersWithCount(8, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(50))));
        register(context, END_FROG_TOWER_CRATES_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_TOWER_CRATES_KEY),
                modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(80))));

        register(context, FROGVASIUM_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FROGVASIUM_ORE_KEY),
                modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(50))));

        register(context, RUBBER_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RUBBER_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1,0.2f,2), ModBlocks.RUBBER_SAPLING));
        register(context, ORCHID_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_ORCHID_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, BROMELIAD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_BROMELIAD_KEY),
                RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, KAURI_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.KAURI_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1,0.2f,2), ModBlocks.KAURI_SAPLING));
    }
    public static void registerPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModFeaturesPlacing.FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModFeaturesPlacing.NETHER_FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModFeaturesPlacing.END_FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModFeaturesPlacing.FROGVASIUM_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.RAINFOREST_KEY),
                GenerationStep.Feature.UNDERGROUND_ORES, ModFeaturesPlacing.FROGVASIUM_PLACED_KEY);
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Frogvasion.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }
}
