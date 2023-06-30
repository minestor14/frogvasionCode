package me.Minestor.frogvasion.worldgen.structures;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.worldgen.structures.custom.FrogHouseStructure;
import me.Minestor.frogvasion.worldgen.structures.custom.FrogMazeStructure;
import me.Minestor.frogvasion.worldgen.structures.custom.RubberRuinStructure;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {
    public static final RegistryKey<Structure> FROG_HOUSE_KEY = key("frog_house");
    public static final RegistryKey<Structure> FROG_MAZE_KEY = key("frog_maze");
    public static final RegistryKey<Structure> RUBBER_RUIN_KEY = key("rubber_ruin");
    public static StructureType<FrogHouseStructure> FROG_HOUSE;
    public static StructureType<FrogMazeStructure> FROG_MAZE;
    public static StructureType<RubberRuinStructure> RUBBER_RUIN;
    public static void registerStructures() {
        FROG_HOUSE = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_house"), () -> FrogHouseStructure.CODEC);
        FROG_MAZE = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_maze"), () -> FrogMazeStructure.CODEC);
        RUBBER_RUIN = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Frogvasion.MOD_ID, "rubber_ruin"), () -> RubberRuinStructure.CODEC);
    }
    private static RegistryKey<Structure> key(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Frogvasion.MOD_ID, name));
    }
    
}
