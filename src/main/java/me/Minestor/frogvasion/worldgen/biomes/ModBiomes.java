package me.Minestor.frogvasion.worldgen.biomes;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.worldgen.features.ModPlacedFeatures;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.*;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.biome.OverworldBiomeCreator.getSkyColor;

public class ModBiomes {
    public static final RegistryKey<Biome> RAINFOREST_KEY = registerKey("rainforest");
    public static final RegistryKey<Biome> FROG_MARSH_KEY = registerKey("frog_marsh");
    public static final RegistryKey<Biome> TROPICAL_SAVANNA_KEY = registerKey("tropical_savanna");
    public static final RegistryKey<Biome> MIXED_FOREST_KEY = registerKey("mixed_forest");
    public static Biome RAINFOREST;
    public static Biome FROG_MARSH;
    public static Biome TROPICAL_SAVANNA;
    public static Biome MIXED_FOREST;
    public static RegistryEntry<Biome> RAINFOREST_ENTRY;
    public static RegistryEntry<Biome> FROG_MARSH_ENTRY;
    public static RegistryEntry<Biome> TROPICAL_SAVANNA_ENTRY;
    public static RegistryEntry<Biome> MIXED_FOREST_ENTRY;
    public static void register() {}
    public static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Frogvasion.MOD_ID, name));
    }
    public static void bootstrap(Registerable<Biome> biomeRegisterable) {
        RegistryEntryLookup<PlacedFeature> registryEntryLookup = biomeRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> registryEntryLookup2 = biomeRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        RAINFOREST = createRainforest(registryEntryLookup, registryEntryLookup2);
        RAINFOREST_ENTRY = biomeRegisterable.register(RAINFOREST_KEY, RAINFOREST);

        FROG_MARSH = createFrogMarsh(registryEntryLookup, registryEntryLookup2);
        FROG_MARSH_ENTRY = biomeRegisterable.register(FROG_MARSH_KEY, FROG_MARSH);

        TROPICAL_SAVANNA = createTropicalSavanna(registryEntryLookup, registryEntryLookup2);
        TROPICAL_SAVANNA_ENTRY = biomeRegisterable.register(TROPICAL_SAVANNA_KEY, TROPICAL_SAVANNA);

        MIXED_FOREST = createMixedForest(registryEntryLookup, registryEntryLookup2);
        MIXED_FOREST_ENTRY = biomeRegisterable.register(MIXED_FOREST_KEY, MIXED_FOREST);
    }
    public static Biome createMixedForest(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        return createMixedForestFeatures(featureLookup, carverLookup, 0.1f, builder);
    }
    public static Biome createMixedForestFeatures(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup, float depth, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addBasicFeatures(lookupBackedBuilder);
        addGreenwoodFlowers(lookupBackedBuilder);

        DefaultBiomeFeatures.addForestTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addForestFlowers(lookupBackedBuilder);
        DefaultBiomeFeatures.addTaigaTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addGroveTrees(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.DARK_FOREST_VEGETATION);
        lookupBackedBuilder.feature(GenerationStep.Feature.FLUID_SPRINGS, ModPlacedFeatures.WHITE_ORCHID_PLACED_KEY);
        DefaultBiomeFeatures.addExtraGoldOre(lookupBackedBuilder);

        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FOREST);
        return createBiome(Biome.Precipitation.RAIN, 0.6f, depth, spawnSettings, lookupBackedBuilder, musicSound);
    }
    public static Biome createTropicalSavanna(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addDesertMobs(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3));
        return createTropicalSavannaFeatures(featureLookup, carverLookup, 0.3f, builder);
    }
    public static Biome createTropicalSavannaFeatures(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup, float depth, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);

        addBasicFeatures(lookupBackedBuilder);
        addGreenwoodFlowers(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_RED_ORCHID_PLACED_KEY);

        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TROPICAL_ACACIA_PLACED_KEY);
        DefaultBiomeFeatures.addMossyRocks(lookupBackedBuilder);
        DefaultBiomeFeatures.addSavannaGrass(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_CACTUS_DECORATED);
        lookupBackedBuilder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH_BADLANDS);

        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DESERT);
        return createBiome(Biome.Precipitation.NONE, 0.8f, depth, spawnSettings, lookupBackedBuilder, musicSound);
    }
    public static Biome createFrogMarsh(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(builder);
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3));
        return createFrogMarshFeatures(featureLookup, carverLookup, 0.7f, builder);
    }
    public static Biome createFrogMarshFeatures(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup, float depth, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);

        DefaultBiomeFeatures.addFossils(lookupBackedBuilder);
        addBasicFeatures(lookupBackedBuilder);
        addGreenwoodFlowers(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PURPLE_ORCHID_PLACED_KEY);

        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addJungleGrass(lookupBackedBuilder);
        DefaultBiomeFeatures.addSwampVegetation(lookupBackedBuilder);
        DefaultBiomeFeatures.addSavannaGrass(lookupBackedBuilder);

        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_SWAMP);

        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_SWAMP);
        return createBiome(Biome.Precipitation.RAIN, 0.95f, depth, spawnSettings, lookupBackedBuilder, musicSound);
    }
    public static Biome createRainforest(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.NORMAL_TREE_FROG_ENTITY, 15, 1, 3));
        return createRainforestFeatures(featureLookup, carverLookup, 0.9F, builder);
    }
    private static Biome createRainforestFeatures(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup, float depth, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);

        addBasicFeatures(lookupBackedBuilder);
        addGreenwoodFlowers(lookupBackedBuilder);

        DefaultBiomeFeatures.addSparseJungleTrees(lookupBackedBuilder);

        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.KAURI_TREE_PLACED_KEY);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RUBBER_TREE_PLACED_KEY);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.LUSH_CAVES_VEGETATION);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.SPORE_BLOSSOM);

        DefaultBiomeFeatures.addJungleTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addSavannaTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addExtraSavannaTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addJungleGrass(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(lookupBackedBuilder);
        DefaultBiomeFeatures.addVines(lookupBackedBuilder);
        DefaultBiomeFeatures.addMossyRocks(lookupBackedBuilder);
        DefaultBiomeFeatures.addSparseMelons(lookupBackedBuilder);

        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_JUNGLE);
        return createBiome(Biome.Precipitation.RAIN, 0.95F, depth, spawnSettings, lookupBackedBuilder, musicSound);
    }
    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return createBiome(precipitation, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
    }

    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return (new Biome.Builder()).precipitation(precipitation.ordinal() >= 1).temperature(temperature).downfall(downfall).effects((new BiomeEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).music(music).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }
    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addClayDisk(generationSettings);
    }
    private static void addGreenwoodFlowers(GenerationSettings.LookupBackedBuilder generationSettings) {
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BROMELIAD_PLACED_KEY);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ORCHID_PLACED_KEY);
    }
}
