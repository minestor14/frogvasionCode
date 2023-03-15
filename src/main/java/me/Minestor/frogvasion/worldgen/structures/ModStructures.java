package me.Minestor.frogvasion.worldgen.structures;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.worldgen.structures.custom.FrogHouseStructure;
import me.Minestor.frogvasion.worldgen.structures.custom.FrogMazeStructure;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {
    public static StructureType<FrogHouseStructure> FROG_HOUSE;
    public static StructureType<FrogMazeStructure> FROG_MAZE;
    public static void registerStructures() {
        FROG_HOUSE = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_house"), () -> FrogHouseStructure.CODEC);
        FROG_MAZE = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_maze"), () -> FrogMazeStructure.CODEC);
    }
}
