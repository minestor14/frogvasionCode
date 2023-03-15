package me.Minestor.frogvasion.datagen;

import me.Minestor.frogvasion.datagen.customs.ModBlockLootTableGenerator;
import me.Minestor.frogvasion.datagen.customs.ModModelsProvider;
import me.Minestor.frogvasion.datagen.customs.ModRecipeGenerator;
import me.Minestor.frogvasion.datagen.customs.ModWorldGenGenerator;
import me.Minestor.frogvasion.worldgen.biomes.ModBiomes;
import me.Minestor.frogvasion.worldgen.features.ModConfiguredFeatures;
import me.Minestor.frogvasion.worldgen.features.ModFeaturesPlacing;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ModDatageneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockLootTableGenerator::new);
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModModelsProvider::new);
        pack.addProvider(ModWorldGenGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModFeaturesPlacing::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
    }
}
