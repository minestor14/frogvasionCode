package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;

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
        
        addDrop(ModBlocks.KAURI_STAIRS);
        addDrop(ModBlocks.RUBBER_STAIRS);
        addDrop(ModBlocks.KAURI_SLAB, slabDrops(ModBlocks.KAURI_SLAB));
        addDrop(ModBlocks.RUBBER_SLAB, slabDrops(ModBlocks.RUBBER_SLAB));

        addDrop(ModBlocks.HONEY_FUNGUS, dropsWithShears(ModBlocks.HONEY_FUNGUS));
        addDrop(ModBlocks.MUD_FARMLAND, Blocks.MUD);
        addDrop(ModBlocks.SALI_TYSSE_CROP, cropDrops(ModBlocks.SALI_TYSSE_CROP, ModItems.SALI_TYSSE, ModItems.SALI_TYSSE_SEEDS, SurvivesExplosionLootCondition.builder()));
        addDrop(ModBlocks.CHROMA_CLUMP);

        addDrop(ModBlocks.PURPLE_ORCHID);
        addDrop(ModBlocks.DARK_PURPLE_ORCHID);
        addDrop(ModBlocks.DARK_RED_ORCHID);
        addDrop(ModBlocks.WHITE_ORCHID);
        addDrop(ModBlocks.BLACK_ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_PURPLE_ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_DARK_PURPLE_ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_DARK_RED_ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_WHITE_ORCHID);
        addPottedPlantDrops(ModBlocks.POTTED_BLACK_ORCHID);

        addDrop(ModBlocks.SAVANNA_SOIL);
        addDrop(ModBlocks.TROPICAL_ACACIA_SAPLING);
    }
}
