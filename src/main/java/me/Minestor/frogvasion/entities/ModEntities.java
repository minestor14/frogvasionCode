package me.Minestor.frogvasion.entities;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<SoldierFrog> SOLDIER_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "soldier_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, SoldierFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
    public static final EntityType<BossSoldierFrog> BOSS_SOLDIER_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "boss_soldier_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, BossSoldierFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
    public static final EntityType<ExplosiveFrog> EXPLOSIVE_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "explosive_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, ExplosiveFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
    public static final EntityType<GrowingFrog> GROWING_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "growing_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, GrowingFrog::new).dimensions(EntityDimensions.changing(0.5f,0.5f)).build());
    public static final EntityType<ArmedFrog> ARMED_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "armed_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, ArmedFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
    public static final EntityType<TadpoleRocket> TADPOLE_ROCKET_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "tadpole_rocket"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, TadpoleRocket::new).dimensions(EntityDimensions.fixed(0.2f,0.2f)).build());
    public static final EntityType<GrapplingFrog> GRAPPLING_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "grappling_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, GrapplingFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
    public static final EntityType<EnderFrog> ENDER_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "ender_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, EnderFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());
}