package me.Minestor.frogvasion.worldgen.biomes;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.util.ModRegistries;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.biome.OverworldBiomeCreator.getSkyColor;

public class ModBiomes {
    public static final RegistryKey<Biome> RAINFOREST_KEY = registerKey("rainforest");
    public static Biome RAINFOREST;
    public static RegistryEntry<Biome> RAINFOREST_ENTRY = RegistryEntry.Reference.standAlone(new ModRegistries.AnyOwnerBiome(), RAINFOREST_KEY);
    public static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Frogvasion.MOD_ID, name));
    }
    public static void registerBiomes() {
    }
    public static void bootstrap(Registerable<Biome> biomeRegisterable) {
        RegistryEntryLookup<PlacedFeature> registryEntryLookup = biomeRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> registryEntryLookup2 = biomeRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        RAINFOREST = createRainforest(registryEntryLookup, registryEntryLookup2);
        RAINFOREST_ENTRY = biomeRegisterable.register(RAINFOREST_KEY, RAINFOREST);
        Frogvasion.LOGGER.info("biomes initialized");
    }
    public static Biome createRainforest(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 40, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
        return createRainforestFeatures(featureLookup, carverLookup, 0.9F, false, true, true, builder);
    }
    private static Biome createRainforestFeatures(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup, float depth, boolean bamboo, boolean sparse, boolean unmodified, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addBasicFeatures(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        if (bamboo) {
            DefaultBiomeFeatures.addBambooJungleTrees(lookupBackedBuilder);
        } else {
            if (unmodified) {
                DefaultBiomeFeatures.addBamboo(lookupBackedBuilder);
            }

            if (sparse) {
                DefaultBiomeFeatures.addSparseJungleTrees(lookupBackedBuilder);
            } else {
                DefaultBiomeFeatures.addJungleTrees(lookupBackedBuilder);
            }
        }
        DefaultBiomeFeatures.addSavannaTallGrass(lookupBackedBuilder);
        DefaultBiomeFeatures.addSavannaTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addExtraSavannaTrees(lookupBackedBuilder);
        DefaultBiomeFeatures.addJungleGrass(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(lookupBackedBuilder);
        DefaultBiomeFeatures.addVines(lookupBackedBuilder);
        DefaultBiomeFeatures.addMossyRocks(lookupBackedBuilder);
        if (sparse) {
            DefaultBiomeFeatures.addSparseMelons(lookupBackedBuilder);
        } else {
            DefaultBiomeFeatures.addMelons(lookupBackedBuilder);
        }

        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_JUNGLE_AND_FOREST);
        return createBiome(Biome.Precipitation.RAIN, 0.95F, depth, spawnSettings, lookupBackedBuilder, musicSound);
    }
    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return createBiome(precipitation, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
    }

    private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return (new Biome.Builder()).precipitation(precipitation).temperature(temperature).downfall(downfall).effects((new BiomeEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).music(music).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }
    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
    }
}
