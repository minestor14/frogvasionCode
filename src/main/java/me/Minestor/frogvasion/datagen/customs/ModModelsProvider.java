package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CONCENTRATED_SLIME);

        blockStateModelGenerator.registerLog(ModBlocks.RUBBER_LOG).log(ModBlocks.RUBBER_LOG).wood(ModBlocks.RUBBER_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_RUBBER_LOG).log(ModBlocks.STRIPPED_RUBBER_LOG).wood(ModBlocks.STRIPPED_RUBBER_WOOD);

        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBER_PLANKS);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBER_LEAVES);

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.RUBBER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        
        blockStateModelGenerator.registerLog(ModBlocks.KAURI_LOG).log(ModBlocks.KAURI_LOG).wood(ModBlocks.KAURI_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_KAURI_LOG).log(ModBlocks.STRIPPED_KAURI_LOG).wood(ModBlocks.STRIPPED_KAURI_WOOD);

        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.KAURI_PLANKS);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.KAURI_LEAVES);

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.KAURI_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ICE_SPIKE, Models.GENERATED);
    }
}
