package me.Minestor.frogvasion.util.items;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

public class ModMiscItemAndBlockModifiers {
    public static void register() {
        registerCompostables();
        registerStrippables();
        registerFlammables();
    }
    public static void registerCompostables() {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.BROMELIAD, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.HONEY_FUNGUS, 0.85f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.RUBBER_LEAVES, 0.3f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.RUBBER_SAPLING, 0.3f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.KAURI_LEAVES, 0.3f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.KAURI_SAPLING, 0.3f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.UNPROCESSED_RUBBER, 0.2f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.SALI_TYSSE, 0.6f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.SALI_TYSSE_SEEDS, 0.3f);
        for (RegistryEntry<Block> block : Registries.BLOCK.iterateEntries(ModTags.ORCHIDS)) {
            ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(block.value(), 0.65f);
        }
    }
    public static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.RUBBER_LOG, ModBlocks.STRIPPED_RUBBER_LOG);
        StrippableBlockRegistry.register(ModBlocks.RUBBER_WOOD, ModBlocks.STRIPPED_RUBBER_WOOD);
        StrippableBlockRegistry.register(ModBlocks.KAURI_LOG, ModBlocks.STRIPPED_KAURI_LOG);
        StrippableBlockRegistry.register(ModBlocks.KAURI_WOOD, ModBlocks.STRIPPED_KAURI_WOOD);
    }
    public static void registerFlammables() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModTags.RUBBER_LOGS, 5, 5);
        registry.add(ModBlocks.RUBBER_LEAVES, 30, 60);
        registry.add(ModBlocks.RUBBER_PLANKS, 5, 20);
        registry.add(ModBlocks.RUBBER_STAIRS, 5, 20);
        registry.add(ModBlocks.RUBBER_SLAB, 5, 20);

        registry.add(ModTags.KAURI_LOGS, 5, 5);
        registry.add(ModBlocks.KAURI_LEAVES, 30, 60);
        registry.add(ModBlocks.KAURI_PLANKS, 5, 20);
        registry.add(ModBlocks.KAURI_STAIRS, 5, 20);
        registry.add(ModBlocks.KAURI_SLAB, 5, 20);
    }
}
