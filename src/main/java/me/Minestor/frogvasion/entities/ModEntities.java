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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
    public static final EntityType<IceFrog> ICE_FROG_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "ice_frog"), FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, IceFrog::new).dimensions(EntityDimensions.fixed(0.5f,0.5f)).build());

    public static ModFrog getFrog(String defaultedFrog, World world) {
        if(defaultedFrog == null) return new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY,world);
        return switch (defaultedFrog) {
            case "tadpole", "tadpole_rocket", "empty" -> new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, world);
            case "boss_soldier" -> new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world);
            case "armed" -> new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, world);
            case "ender" -> new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world);
            case "explosive" -> new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, world);
            case "grappling" -> new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, world);
            case "growing" -> new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world);
            case "ice" -> new IceFrog(ModEntities.ICE_FROG_ENTITY, world);
            default -> new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, world);
        };
    }
    public static String getDefaultedFrog(ModFrog frog) {
        return switch (frog.getFrogType()) {
            case SOLDIER -> "soldier";
            case BOSS_SOLDIER -> "boss_soldier";
            case ARMED ->"armed";
            case ENDER -> "ender";
            case EXPLOSIVE -> "explosive";
            case GRAPPLING ->"grappling" ;
            case GROWING -> "growing";
            case TADPOLE_ROCKET -> "tadpole";
            case ICE -> "ice";
        };
    }
    public static String getDefaultedFrog(FrogTypes type) {
        return switch (type) {
            case SOLDIER -> "soldier";
            case BOSS_SOLDIER -> "boss_soldier";
            case ARMED ->"armed";
            case ENDER -> "ender";
            case EXPLOSIVE -> "explosive";
            case GRAPPLING ->"grappling" ;
            case GROWING -> "growing";
            case TADPOLE_ROCKET -> "tadpole";
            case ICE -> "ice";
        };
    }
    public static int getDefaultedInt(String defaultedFrog) {
        return switch (defaultedFrog) {
            case "soldier" -> 1;
            case "boss_soldier" -> 2;
            case "armed" -> 3;
            case "ender" -> 4;
            case "explosive" -> 5;
            case "grappling" -> 6;
            case "growing" -> 7;
            case "tadpole" -> 8;
            case "ice" -> 9;
            default -> 0;
        };
    }
    public static int getDefaultedInt(FrogTypes type) {
        return switch (type) {
            case SOLDIER -> 1;
            case BOSS_SOLDIER -> 2;
            case ARMED -> 3;
            case ENDER -> 4;
            case EXPLOSIVE -> 5;
            case GRAPPLING -> 6;
            case GROWING -> 7;
            case TADPOLE_ROCKET -> 8;
            case ICE -> 9;
        };
    }
    public static ModFrog getFrog(int defaultedInt, World world) {
        return switch (defaultedInt) {
            case 1 -> new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, world);
            case 2 -> new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world);
            case 3 -> new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, world);
            case 4 -> new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world);
            case 5 -> new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, world);
            case 6 -> new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, world);
            case 7 -> new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world);
            case 9 -> new IceFrog(ModEntities.ICE_FROG_ENTITY, world);
            default -> new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY,world);
        };
    }
}