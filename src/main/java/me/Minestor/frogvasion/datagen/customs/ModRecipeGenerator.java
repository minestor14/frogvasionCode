package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.util.items.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BLOCK, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONCENTRATED_SLIME);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.SPINE, RecipeCategory.BUILDING_BLOCKS, Blocks.BONE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST_FRAGMENT, RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROG_HIDE, RecipeCategory.MISC, Items.LEATHER);
        offerShapelessRecipe(exporter, Items.BONE_MEAL, ModItems.SPINE, RecipeCategory.MISC.getName(), 1);
        offerSmelting(exporter, List.of(ModItems.FROG_LEGS), RecipeCategory.FOOD, ModItems.COOKED_FROG_LEGS, 1, 60, RecipeCategory.FOOD.getName());
        offerFoodCookingRecipe(exporter, "smoker", RecipeSerializer.SMOKING, SmokingRecipe::new, 30, ModItems.FROG_LEGS, ModItems.COOKED_FROG_LEGS, 1);
        offerFoodCookingRecipe(exporter, "campfire", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 30, ModItems.FROG_LEGS, ModItems.COOKED_FROG_LEGS, 1);

        offerSmelting(exporter, List.of(ModItems.UNPROCESSED_RUBBER), RecipeCategory.MISC, ModItems.RUBBER, 2, 50, RecipeCategory.MISC.getName());
        offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModItems.RUBBER, ModBlocks.SLIME_LAYER);
        
        offerShapelessRecipe(exporter, ModBlocks.RUBBER_PLANKS, ModBlocks.RUBBER_LOG, RecipeCategory.BUILDING_BLOCKS.getName(), 4);
        offerShapelessRecipe(exporter, ModBlocks.KAURI_PLANKS, ModBlocks.KAURI_LOG, RecipeCategory.BUILDING_BLOCKS.getName(), 4);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBER_PLANKS, ModBlocks.RUBBER_SLAB);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.KAURI_PLANKS, ModBlocks.KAURI_SLAB);
        createStairsRecipe(ModBlocks.RUBBER_STAIRS, Ingredient.ofItems(ModBlocks.RUBBER_PLANKS))
                .criterion(hasItem(ModBlocks.RUBBER_PLANKS), conditionsFromItem(ModBlocks.RUBBER_PLANKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.RUBBER_STAIRS)));
        createStairsRecipe(ModBlocks.KAURI_STAIRS, Ingredient.ofItems(ModBlocks.KAURI_PLANKS))
                .criterion(hasItem(ModBlocks.KAURI_PLANKS), conditionsFromItem(ModBlocks.KAURI_PLANKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.KAURI_STAIRS)));

        offerSmelting(exporter, List.of(ModItems.RAW_FROGVASIUM, ModBlocks.FROGVASIUM_ORE, ModBlocks.DEEPSLATE_FROGVASIUM_ORE), RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT, 1, 40, RecipeCategory.MISC.getName());
        offerBlasting(exporter, List.of(ModItems.RAW_FROGVASIUM, ModBlocks.FROGVASIUM_ORE, ModBlocks.DEEPSLATE_FROGVASIUM_ORE), RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT, 1, 20, RecipeCategory.MISC.getName());
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROGVASIUM_NUGGET, RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FROGVASIUM_INGOT, RecipeCategory.MISC, ModBlocks.FROGVASIUM_BLOCK, "frogvasium_block_comp", RecipeCategory.MISC.getName(), "frogvasium_ingot_comp", RecipeCategory.MISC.getName());
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_FROGVASIUM, RecipeCategory.MISC, ModBlocks.RAW_FROGVASIUM_BLOCK);
        offerShapelessRecipe(exporter, ModItems.SALI_TYSSE_SEEDS, ModItems.SALI_TYSSE, RecipeCategory.FOOD.getName(), 3);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.ORCHID, RecipeCategory.MISC, ModItems.PINK_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Blocks.BLUE_ORCHID, RecipeCategory.MISC, ModItems.BLUE_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.PURPLE_ORCHID, RecipeCategory.MISC, ModItems.PURPLE_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.DARK_PURPLE_ORCHID, RecipeCategory.MISC, ModItems.DARK_PURPLE_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.DARK_RED_ORCHID, RecipeCategory.MISC, ModItems.DARK_RED_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.WHITE_ORCHID, RecipeCategory.MISC, ModItems.WHITE_ORCHID_BOUQUET);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.BLACK_ORCHID, RecipeCategory.MISC, ModItems.BLACK_ORCHID_BOUQUET);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.PINK_ORCHID_POWDER, ModBlocks.ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.BLUE_ORCHID_POWDER, Blocks.BLUE_ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.PURPLE_ORCHID_POWDER, ModBlocks.PURPLE_ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.DARK_PURPLE_ORCHID_POWDER, ModBlocks.DARK_PURPLE_ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.DARK_RED_ORCHID_POWDER, ModBlocks.DARK_RED_ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.WHITE_ORCHID_POWDER, ModBlocks.WHITE_ORCHID, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.MISC, ModItems.BLACK_ORCHID_POWDER, ModBlocks.BLACK_ORCHID, 4);
        offerShapelessRecipe(exporter, ModItems.PINK_ORCHID_POWDER, ModBlocks.ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.BLUE_ORCHID_POWDER, Blocks.BLUE_ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.PURPLE_ORCHID_POWDER, ModBlocks.PURPLE_ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.DARK_PURPLE_ORCHID_POWDER, ModBlocks.DARK_PURPLE_ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.DARK_RED_ORCHID_POWDER, ModBlocks.DARK_RED_ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.WHITE_ORCHID_POWDER, ModBlocks.WHITE_ORCHID, RecipeCategory.MISC.getName(), 2);
        offerShapelessRecipe(exporter, ModItems.BLACK_ORCHID_POWDER, ModBlocks.BLACK_ORCHID, RecipeCategory.MISC.getName(), 2);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ALTAR_MANUAL).input(ItemTags.BOOKSHELF_BOOKS).input(ModTags.ORCHID_MAGIC_SOURCES)
                .criterion(hasItem(ModBlocks.ORCHID), conditionsFromItem(ModBlocks.ORCHID))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.ALTAR_MANUAL)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GUIDE_TO_FROGS).input(ModItems.FROG_HIDE).input(ItemTags.BOOKSHELF_BOOKS)
                .criterion(hasItem(ModItems.FROG_HIDE), conditionsFromItem(ModItems.FROG_HIDE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GUIDE_TO_FROGS)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HERPETOLOGIST_JAR).input(Blocks.GLASS).input(ItemTags.WOODEN_BUTTONS)
                .criterion(hasItem(Blocks.GLASS), conditionsFromItem(Blocks.GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.HERPETOLOGIST_JAR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CROAKING_WHISTLE)
                .pattern("///")
                .pattern(" //")
                .input('/', ModItems.FROGVASIUM_NUGGET)
                .criterion(hasItem(ModItems.FROGVASIUM_INGOT), conditionsFromItem(ModItems.FROGVASIUM_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CROAKING_WHISTLE)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BOOSTING_PLATE)
                .input(ModItems.FROG_LEGS).input(Items.ENCHANTED_BOOK).input(Blocks.SMOOTH_STONE_SLAB)
                .criterion(hasItem(ModItems.FROG_LEGS), conditionsFromItem(ModItems.FROG_LEGS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.BOOSTING_PLATE)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ORCHID_MIX)
                .input(ModBlocks.ORCHID).input(Blocks.BLUE_ORCHID).input(ModBlocks.PURPLE_ORCHID)
                .input(ModBlocks.DARK_PURPLE_ORCHID).input(ModBlocks.DARK_RED_ORCHID).input(ModBlocks.WHITE_ORCHID).input(ModBlocks.BLACK_ORCHID)
                .criterion(hasItem(ModBlocks.ORCHID), conditionsFromItem(ModBlocks.ORCHID))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.ORCHID_MIX)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ORCHID_MIX, 9)
                .input(ModItems.PINK_ORCHID_BOUQUET).input(ModItems.BLUE_ORCHID_BOUQUET).input(ModItems.PURPLE_ORCHID_BOUQUET)
                .input(ModItems.DARK_PURPLE_ORCHID_BOUQUET).input(ModItems.DARK_RED_ORCHID_BOUQUET).input(ModItems.WHITE_ORCHID_BOUQUET).input(ModItems.BLACK_ORCHID_BOUQUET)
                .criterion(hasItem(ModBlocks.ORCHID), conditionsFromItem(ModBlocks.ORCHID))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.ORCHID_MIX) + "_from_bouquets"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONVERSION_PEDESTAL)
                .pattern("###")
                .pattern("#?#")
                .pattern("///")
                .input('?', Items.BREWING_STAND).input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT).input('/', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.BREWING_STAND), conditionsFromItem(Items.BREWING_STAND))
                .criterion(hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT), conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CONVERSION_PEDESTAL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .pattern("#!#")
                .pattern("! !")
                .pattern("#?#")
                .input('?', Items.SOUL_SAND).input('#', ModItems.FROG_HIDE).input('!', ModItems.SPINE)
                .criterion(hasItem(Items.SOUL_SAND), conditionsFromItem(Items.SOUL_SAND))
                .criterion(hasItem(ModItems.FROG_HIDE), conditionsFromItem(ModItems.FROG_HIDE))
                .criterion(hasItem(ModItems.SPINE), conditionsFromItem(ModItems.SPINE))
                .offerTo(exporter, new Identifier("frogvasion:empty_frog_ghost_fragment_shaped"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ADDRESS_CARD, 3)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', Items.NAME_TAG)
                .input('!', Items.ENDER_PEARL)
                .input('#', Items.PAPER)
                .criterion(hasItem(Items.NAME_TAG), conditionsFromItem(Items.NAME_TAG))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.ADDRESS_CARD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, ModBlocks.FROGVASIUM_RAIL, 16)
                .pattern("# #")
                .pattern("#?#")
                .pattern("# #")
                .input('?', Items.STICK).input('#', ModItems.FROGVASIUM_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.FROGVASIUM_INGOT), conditionsFromItem(ModItems.FROGVASIUM_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROGVASIUM_RAIL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_ATTACKER)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', ModItems.SOLDIER_FROG_GHOST).input('#', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE).input('!', Items.REDSTONE)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROGVASIUM_ATTACKER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_DEMOLISHER)
                .pattern("-#-")
                .pattern("-?-")
                .pattern("!!!")
                .input('!', ModItems.SOLDIER_FROG_GHOST).input('#', ModBlocks.FROGVASIUM_ATTACKER).input('?', Items.BLAZE_ROD).input('-', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE)
                .criterion(hasItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE), conditionsFromItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROGVASIUM_DEMOLISHER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROGVASIUM_GRAPPLER)
                .pattern("###")
                .pattern("#?#")
                .pattern("!!!")
                .input('?', ModItems.GRAPPLING_FROG_GHOST).input('#', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE).input('!', Items.REDSTONE)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROGVASIUM_GRAPPLER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROG_STAFF)
                .pattern("#")
                .pattern("?")
                .pattern("?")
                .input('?', Items.STICK).input('#', ModItems.FROG_HELMET_ITEM)
                .criterion(hasItem(ModItems.FROG_HELMET_ITEM), conditionsFromItem(ModItems.FROG_HELMET_ITEM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.FROG_STAFF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_HELMET)
                .pattern("###")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT), conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GHOST_FRAGMENT_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_CHESTPLATE)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT), conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GHOST_FRAGMENT_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_LEGGINGS)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT), conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GHOST_FRAGMENT_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GHOST_FRAGMENT_BOOTS)
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.EMPTY_FROG_GHOST_FRAGMENT)
                .criterion(hasItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT), conditionsFromItem(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GHOST_FRAGMENT_BOOTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.FROG_CAGE)
                .pattern("#?#")
                .pattern("#?#")
                .pattern("#?#").input('#',Items.STRING).input('?',Items.SLIME_BALL)
                .criterion(hasItem(Items.SLIME_BALL), conditionsFromItem(Items.SLIME_BALL))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROG_CAGE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModBlocks.FROG_TRAP)
                .pattern("#?#")
                .pattern("#!#")
                .input('#', ModItems.FROGVASIUM_INGOT).input('!', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE).input('?', Items.STONECUTTER)
                .criterion(hasItem(ModItems.FROGVASIUM_INGOT), conditionsFromItem(ModItems.FROGVASIUM_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROG_TRAP)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAILBOX)
                .pattern("###")
                .pattern("#?#")
                .input('?', ModItems.ADDRESS_CARD).input('#', Items.IRON_INGOT)
                .criterion(hasItem(ModItems.ADDRESS_CARD), conditionsFromItem(ModItems.ADDRESS_CARD))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.MAILBOX)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FROG_FIRE_CHARGE).input(ModItems.FROGVASIUM_NUGGET).input(Items.GUNPOWDER)
                .criterion(hasItem(Items.GUNPOWDER),conditionsFromItem(Items.GUNPOWDER)).offerTo(exporter, new Identifier(getRecipeName(ModItems.FROG_FIRE_CHARGE)));
        
        offerBarkBlockRecipe(exporter, ModBlocks.RUBBER_WOOD, ModBlocks.RUBBER_LOG);
        offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_RUBBER_WOOD, ModBlocks.STRIPPED_RUBBER_LOG);
        offerBarkBlockRecipe(exporter, ModBlocks.KAURI_WOOD, ModBlocks.KAURI_LOG);
        offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_KAURI_WOOD, ModBlocks.STRIPPED_KAURI_LOG);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBER_EXTRACTOR)
                .pattern("#?#")
                .pattern("#!#")
                .pattern("###")
                .input('#', ModItems.RUBBER).input('!', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE).input('?', Blocks.CAULDRON)
                .criterion(hasItem(ModItems.RUBBER), conditionsFromItem(ModItems.RUBBER))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.RUBBER_EXTRACTOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUEST_BLOCK)
                .pattern("#?#")
                .pattern("#!#")
                .pattern("#?#")
                .input('#', ModItems.FROGVASIUM_INGOT).input('!', ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE).input('?', ModItems.RUBBER)
                .criterion(hasItem(ModItems.FROGVASIUM_INGOT), conditionsFromItem(ModItems.FROGVASIUM_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.QUEST_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHROMA_CLUMP)
                .pattern("###")
                .pattern("#!#")
                .pattern("###")
                .input('#', ModItems.SALI_TYSSE_SEEDS).input('!', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CHROMA_CLUMP)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FLORADIC_ALTAR).input(ModTags.ORCHID_MAGIC_SOURCES).input(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE)
                .criterion(hasItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE), conditionsFromItem(ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FLORADIC_ALTAR)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ORCHID_GRENADE_SHELL).input(ModTags.ORCHID_MAGIC_SOURCES).input(ModItems.FROGVASIUM_NUGGET)
                .criterion(hasItem(ModItems.FROGVASIUM_NUGGET), conditionsFromItem(ModItems.FROGVASIUM_NUGGET))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.ORCHID_GRENADE_SHELL)));
    }
}