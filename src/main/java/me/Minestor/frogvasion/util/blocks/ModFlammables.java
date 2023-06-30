package me.Minestor.frogvasion.util.blocks;

import me.Minestor.frogvasion.blocks.ModBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class ModFlammables {
    public static void register() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlocks.RUBBER_LOG, 5, 5);
        registry.add(ModBlocks.RUBBER_WOOD, 5, 5);
        registry.add(ModBlocks.STRIPPED_RUBBER_LOG, 5, 5);
        registry.add(ModBlocks.STRIPPED_RUBBER_WOOD, 5, 5);

        registry.add(ModBlocks.RUBBER_LEAVES, 30, 60);
        registry.add(ModBlocks.RUBBER_PLANKS, 5, 20);
        
        registry.add(ModBlocks.KAURI_LOG, 5, 5);
        registry.add(ModBlocks.KAURI_WOOD, 5, 5);
        registry.add(ModBlocks.STRIPPED_KAURI_LOG, 5, 5);
        registry.add(ModBlocks.STRIPPED_KAURI_WOOD, 5, 5);

        registry.add(ModBlocks.KAURI_LEAVES, 30, 60);
        registry.add(ModBlocks.KAURI_PLANKS, 5, 20);
    }
}
