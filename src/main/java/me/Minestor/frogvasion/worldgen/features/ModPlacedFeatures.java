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
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.BiasedToBottomIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> FROG_TOWER_CRATES_PLACED_KEY = registerKey("frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> NETHER_FROG_TOWER_CRATES_PLACED_KEY = registerKey("nether_frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> END_FROG_TOWER_CRATES_PLACED_KEY = registerKey("end_frog_tower_chests_placed");
    public static final RegistryKey<PlacedFeature> FROGVASIUM_PLACED_KEY = registerKey("frogvasium_placed");
    public static final RegistryKey<PlacedFeature> RUBBER_TREE_PLACED_KEY = registerKey("rubber_tree_placed");
    public static final RegistryKey<PlacedFeature> ORCHID_PLACED_KEY = registerKey("orchid_placed");
    public static final RegistryKey<PlacedFeature> BROMELIAD_PLACED_KEY = registerKey("bromeliad_placed");
    public static final RegistryKey<PlacedFeature> KAURI_TREE_PLACED_KEY = registerKey("kauri_tree_placed");
    public static final RegistryKey<PlacedFeature> MARSH_COLUMN_PLACED_KEY = registerKey("marsh_column_placed");
    public static final RegistryKey<PlacedFeature> MARSH_DISK_PLACED_KEY = registerKey("marsh_disk_placed");
    public static final RegistryKey<PlacedFeature> MARSH_LAKE_PLACED_KEY = registerKey("marsh_lake_placed");
    public static final RegistryKey<PlacedFeature> MARSH_DELTA_PLACED_KEY = registerKey("marsh_delta_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_HONEY_FUNGUS_PLACED = registerKey("flower_honey_fungus_placed");
    public static final RegistryKey<PlacedFeature> SALI_TYSSE_PLANT_PLACED = registerKey("sali_tysse_plant_placed");
    public static void bootstrap(Registerable<PlacedFeature> context) {
        var registryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, FROG_TOWER_CRATES_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.TOWER_CRATES_KEY),
                modifiersWithCount(18, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
        register(context, NETHER_FROG_TOWER_CRATES_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_TOWER_CRATES_KEY),
                modifiersWithCount(8, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(50))));
        register(context, END_FROG_TOWER_CRATES_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.END_TOWER_CRATES_KEY),
                modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(80))));

        register(context, FROGVASIUM_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FROGVASIUM_ORE_KEY),
                modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-20), YOffset.aboveBottom(50))));

        register(context, RUBBER_TREE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.RUBBER_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1,0.2f,2), ModBlocks.RUBBER_SAPLING));
        register(context, ORCHID_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_ORCHID_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, BROMELIAD_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_BROMELIAD_KEY),
                RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, KAURI_TREE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.KAURI_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1,0.2f,2), ModBlocks.KAURI_SAPLING));

        register(context, MARSH_COLUMN_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.MARSH_COLUMN_KEY),
                CountPlacementModifier.of(BiasedToBottomIntProvider.create(1,2)), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE));

        register(context, MARSH_DISK_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.MARSH_DISK_KEY),
                CountPlacementModifier.of(new WeightedListIntProvider(DataPool.<IntProvider>builder().add(ConstantIntProvider.create(2), 4).add(ConstantIntProvider.create(1), 5).add(ConstantIntProvider.create(3), 2).build())),
                SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE));

        register(context, MARSH_LAKE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.MARSH_LAKE_KEY),
                CountPlacementModifier.of(BiasedToBottomIntProvider.create(2,3)), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE));
        register(context, MARSH_DELTA_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.MARSH_DELTA_KEY),
                CountMultilayerPlacementModifier.of(40), BiomePlacementModifier.of());

        register(context, FLOWER_HONEY_FUNGUS_PLACED, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_HONEY_FUNGUS_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        register(context, SALI_TYSSE_PLANT_PLACED, registryEntryLookup.getOrThrow(ModConfiguredFeatures.SALI_TYSSE_PLANT_KEY),
                CountPlacementModifier.of(BiasedToBottomIntProvider.create(0,1)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }
    public static void registerPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.NETHER_FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.END_FROG_TOWER_CRATES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.FROGVASIUM_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.RAINFOREST_KEY),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.FROGVASIUM_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.MARSH_COLUMN_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.MARSH_DISK_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.LAKES, ModPlacedFeatures.MARSH_LAKE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.MARSH_DELTA_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FLOWER_HONEY_FUNGUS_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.FROG_MARSH_KEY),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.SALI_TYSSE_PLANT_PLACED);
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Frogvasion.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
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
