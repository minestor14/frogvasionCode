package me.Minestor.frogvasion.worldgen.spawing;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.worldgen.biomes.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawning {
    public static void addEntitySpawning() {
        //Grappling Frog
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS), SpawnGroup.MONSTER, ModEntities.GRAPPLING_FROG_ENTITY, 2,1,2);
        SpawnRestriction.register(ModEntities.GRAPPLING_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE, GrapplingFrog::isValidNaturalSpawn);
        //Growing Frog
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA, BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA), SpawnGroup.MONSTER, ModEntities.GROWING_FROG_ENTITY, 3 ,1,2);
        SpawnRestriction.register(ModEntities.GROWING_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE, GrowingFrog::isValidNaturalSpawn);
        //Soldier Frog
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, ModEntities.SOLDIER_FROG_ENTITY, 9 ,1,2);
        SpawnRestriction.register(ModEntities.SOLDIER_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SoldierFrog::isValidNaturalSpawn);
        //Armed Frog
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.MONSTER, ModEntities.ARMED_FROG_ENTITY, 2 ,1,2);
        SpawnRestriction.register(ModEntities.ARMED_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ArmedFrog::isValidNaturalSpawn);
        //Explosive Frog
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, ModEntities.EXPLOSIVE_FROG_ENTITY, 1 ,1,2);
        SpawnRestriction.register(ModEntities.EXPLOSIVE_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ExplosiveFrog::isValidNaturalSpawn);
        //Ice Frog
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.STRAY, EntityType.POLAR_BEAR), SpawnGroup.MONSTER, ModEntities.ICE_FROG_ENTITY, 1 ,1,2);
        SpawnRestriction.register(ModEntities.ICE_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceFrog::isValidNaturalSpawn);
        //Normal Tree Frog
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.RAINFOREST_KEY), SpawnGroup.MONSTER, ModEntities.NORMAL_TREE_FROG_ENTITY,15,1,3);
        SpawnRestriction.register(ModEntities.NORMAL_TREE_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NormalTreeFrog::isValidNaturalSpawn);
        //Gliding Tree Frog
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.RAINFOREST_KEY), SpawnGroup.MONSTER, ModEntities.GLIDING_TREE_FROG_ENTITY,5,1,3);
        SpawnRestriction.register(ModEntities.GLIDING_TREE_FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlidingTreeFrog::isValidNaturalSpawn);
    }
}
