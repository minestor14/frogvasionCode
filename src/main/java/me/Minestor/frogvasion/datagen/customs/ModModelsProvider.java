package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.ArmorItem;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CONCENTRATED_SLIME);

        blockStateModelGenerator.registerLog(ModBlocks.RUBBER_LOG).log(ModBlocks.RUBBER_LOG).wood(ModBlocks.RUBBER_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_RUBBER_LOG).log(ModBlocks.STRIPPED_RUBBER_LOG).wood(ModBlocks.STRIPPED_RUBBER_WOOD);
        BlockStateModelGenerator.BlockTexturePool rubberTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBER_PLANKS);
        rubberTexturePool.stairs(ModBlocks.RUBBER_STAIRS);
        rubberTexturePool.slab(ModBlocks.RUBBER_SLAB);

        blockStateModelGenerator.registerSingleton(ModBlocks.RUBBER_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.RUBBER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        
        blockStateModelGenerator.registerLog(ModBlocks.KAURI_LOG).log(ModBlocks.KAURI_LOG).wood(ModBlocks.KAURI_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_KAURI_LOG).log(ModBlocks.STRIPPED_KAURI_LOG).wood(ModBlocks.STRIPPED_KAURI_WOOD);
        BlockStateModelGenerator.BlockTexturePool kauriTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.KAURI_PLANKS);
        kauriTexturePool.stairs(ModBlocks.KAURI_STAIRS);
        kauriTexturePool.slab(ModBlocks.KAURI_SLAB);

        blockStateModelGenerator.registerSingleton(ModBlocks.KAURI_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.KAURI_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ICE_SPIKE, Models.GENERATED);
        itemModelGenerator.register(ModBlocks.KAURI_SAPLING.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RUBBER_SAPLING.asItem(), Models.GENERATED);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.GHOST_FRAGMENT_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.GHOST_FRAGMENT_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.GHOST_FRAGMENT_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.GHOST_FRAGMENT_BOOTS);
    }
}
