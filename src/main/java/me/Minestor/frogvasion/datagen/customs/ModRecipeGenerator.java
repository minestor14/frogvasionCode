package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BLOCK, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONCENTRATED_SLIME);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.SPINE, RecipeCategory.BUILDING_BLOCKS, Blocks.BONE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST_FRAGMENT, RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROG_HIDE, RecipeCategory.MISC, Items.LEATHER);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROGVASIUM_NUGGET, RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT, RecipeCategory.MISC, ModBlocks.FROGVASIUM_BLOCK,
                "frogvasium_block_comp", RecipeCategory.MISC.getName(), "frogvasium_ingot_comp", RecipeCategory.MISC.getName());
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_FROGVASIUM, RecipeCategory.MISC, ModBlocks.RAW_FROGVASIUM_BLOCK);
        offerSmelting(exporter, List.of(ModItems.RAW_FROGVASIUM), RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT, 1, 40, RecipeCategory.MISC.getName());
        offerShapelessRecipe(exporter, Items.BONE_MEAL, ModItems.SPINE, RecipeCategory.MISC.getName(), 1);
        offerSmelting(exporter, List.of(ModItems.FROG_LEGS), RecipeCategory.FOOD, ModItems.COOKED_FROG_LEGS, 1, 60, RecipeCategory.FOOD.getName());

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONVERSION_PEDESTAL)
                .pattern("###")
                .pattern("#?#")
                .pattern("///")
                .input('?', Items.BREWING_STAND)
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .input('/', Items.AMETHYST_SHARD)
                .criterion(RecipeProvider.hasItem(Items.BREWING_STAND),
                        RecipeProvider.conditionsFromItem(Items.BREWING_STAND))
                .criterion(RecipeProvider.hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT),
                        RecipeProvider.conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .criterion(RecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        RecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModBlocks.CONVERSION_PEDESTAL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .pattern("#!#")
                .pattern("! !")
                .pattern("#?#")
                .input('?', Items.SOUL_SAND)
                .input('#', ModItems.FROG_HIDE)
                .input('!', ModItems.SPINE)
                .criterion(RecipeProvider.hasItem(Items.SOUL_SAND),
                        RecipeProvider.conditionsFromItem(Items.SOUL_SAND))
                .criterion(RecipeProvider.hasItem(ModItems.FROG_HIDE),
                        RecipeProvider.conditionsFromItem(ModItems.FROG_HIDE))
                .criterion(RecipeProvider.hasItem(ModItems.SPINE),
                        RecipeProvider.conditionsFromItem(ModItems.SPINE))
                .offerTo(exporter, new Identifier("frogvasion:empty_frog_ghost_fragment_shaped"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ADDRESS_CARD)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', Items.NAME_TAG)
                .input('#', Items.ENDER_PEARL)
                .input('!', Items.PAPER)
                .criterion(RecipeProvider.hasItem(Items.NAME_TAG),
                        RecipeProvider.conditionsFromItem(Items.NAME_TAG))
                .criterion(RecipeProvider.hasItem(Items.ENDER_PEARL),
                        RecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .criterion(RecipeProvider.hasItem(Items.PAPER),
                        RecipeProvider.conditionsFromItem(Items.PAPER))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.ADDRESS_CARD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, ModBlocks.FROGVASIUM_RAIL, 16)
                .pattern("# #")
                .pattern("#?#")
                .pattern("# #")
                .input('?', Items.STICK)
                .input('#', ModItems.FROGVASIUM_INGOT)
                .criterion(RecipeProvider.hasItem(Items.STICK),
                        RecipeProvider.conditionsFromItem(Items.STICK))
                .criterion(RecipeProvider.hasItem(ModItems.FROGVASIUM_INGOT),
                        RecipeProvider.conditionsFromItem(ModItems.FROGVASIUM_INGOT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModBlocks.FROGVASIUM_RAIL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_ATTACKER)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', ModItems.SOLDIER_FROG_GHOST)
                .input('#', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE)
                .input('!', Items.REDSTONE)
                .criterion(RecipeProvider.hasItem(Items.REDSTONE),
                        RecipeProvider.conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModBlocks.FROGVASIUM_ATTACKER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_DEMOLISHER)
                .pattern("-#-")
                .pattern("-?-")
                .pattern("!!!")
                .input('!', ModItems.SOLDIER_FROG_GHOST)
                .input('#', ModBlocks.FROGVASIUM_ATTACKER)
                .input('?', Items.NETHER_STAR)
                .input('-', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE)
                .criterion(RecipeProvider.hasItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE),
                        RecipeProvider.conditionsFromItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModBlocks.FROGVASIUM_DEMOLISHER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_GRAPPLER)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', ModItems.GRAPPLING_FROG_GHOST)
                .input('#', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE)
                .input('!', Items.REDSTONE)
                .criterion(RecipeProvider.hasItem(Items.REDSTONE),
                        RecipeProvider.conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModBlocks.FROGVASIUM_GRAPPLER)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROG_STAFF)
                .pattern(" # ")
                .pattern(" ? ")
                .pattern(" ? ")
                .input('?', Items.STICK)
                .input('#', ModItems.FROG_HELMET_ITEM)
                .criterion(RecipeProvider.hasItem(ModItems.FROG_HELMET_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.FROG_HELMET_ITEM))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.FROG_STAFF)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_HELMET)
                .pattern("###")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(RecipeProvider.hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT),
                        RecipeProvider.conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.GHOST_FRAGMENT_HELMET)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_CHESTPLATE)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(RecipeProvider.hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT),
                        RecipeProvider.conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.GHOST_FRAGMENT_CHESTPLATE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_LEGGINGS)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(RecipeProvider.hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT),
                        RecipeProvider.conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.GHOST_FRAGMENT_LEGGINGS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_BOOTS)
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(RecipeProvider.hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT),
                        RecipeProvider.conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.GHOST_FRAGMENT_BOOTS)));
    }
}
