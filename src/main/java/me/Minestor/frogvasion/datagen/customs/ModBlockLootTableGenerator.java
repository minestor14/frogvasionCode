package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {

    public ModBlockLootTableGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CONCENTRATED_SLIME);
        addDrop(ModBlocks.RUBBER_LOG);
        addDrop(ModBlocks.RUBBER_WOOD);
        addDrop(ModBlocks.STRIPPED_RUBBER_LOG);
        addDrop(ModBlocks.STRIPPED_RUBBER_WOOD);
        addDrop(ModBlocks.RUBBER_PLANKS);
        addDrop(ModBlocks.RUBBER_SAPLING);
        
        addDrop(ModBlocks.KAURI_LOG);
        addDrop(ModBlocks.KAURI_WOOD);
        addDrop(ModBlocks.STRIPPED_KAURI_LOG);
        addDrop(ModBlocks.STRIPPED_KAURI_WOOD);
        addDrop(ModBlocks.KAURI_PLANKS);
        addDrop(ModBlocks.KAURI_SAPLING);

        addDrop(ModBlocks.RUBBER_EXTRACTOR);
        addDrop(ModBlocks.BROMELIAD);
        addDrop(ModBlocks.ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_ORCHID);

        addDrop(ModBlocks.FROG_STATUE);
        addDrop(ModBlocks.GOLDEN_FROG_STATUE);
        addDrop(ModBlocks.QUEST_BLOCK);
    }
}
