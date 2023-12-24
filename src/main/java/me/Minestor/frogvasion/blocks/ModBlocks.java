package me.Minestor.frogvasion.blocks;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.custom.*;
import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.util.items.ModItemGroups;
import me.Minestor.frogvasion.worldgen.features.ModConfiguredFeatures;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.BlockView;

import java.util.Optional;

public final class ModBlocks {
    public static final Block CONCENTRATED_SLIME = registerBlock("concentrated_slime",new ConcentratedSlimeBlock(FabricBlockSettings.copy(Blocks.SLIME_BLOCK).breakInstantly().nonOpaque()), ItemGroups.NATURAL);
    public static final Block SLIME_LAYER = registerBlockNoItem("slime_layer",new SlimeLayerBlock(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).breakInstantly().noCollision().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block CONVERSION_PEDESTAL = registerBlock("conversion_pedestal", new ConversionPedestalBlock(FabricBlockSettings.copy(Blocks.ENCHANTING_TABLE).strength(4f).requiresTool()), ItemGroups.FUNCTIONAL);
    public static final Block FROG_TOWER_CHEST = registerBlock("frog_tower_chest", new FrogTowerChestBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_HYPHAE).sounds(BlockSoundGroup.WOOD)), ItemGroups.BUILDING_BLOCKS);
    public static final Block LAVA_INFUSED_FROGLIGHT = registerBlock("lava_infused_froglight", new LavaInfusedFroglightBlock(FabricBlockSettings.copyOf(Blocks.MAGMA_BLOCK).luminance(15)), ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_ORE = registerBlock("frogvasium_ore", new ExperienceDroppingBlock(ConstantIntProvider.create(2), FabricBlockSettings.copyOf(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), ItemGroups.NATURAL);
    public static final Block DEEPSLATE_FROGVASIUM_ORE = registerBlock("deepslate_frogvasium_ore", new ExperienceDroppingBlock(ConstantIntProvider.create(2), FabricBlockSettings.copyOf(FROGVASIUM_ORE).requiresTool().strength(3.0F, 3.0F).mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE)), ItemGroups.NATURAL);
    public static final Block FROGVASIUM_BLOCK = registerBlock("frogvasium_block", new FrogvasiumBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().strength(4f,4f).jumpVelocityMultiplier(1.2f)), ItemGroups.BUILDING_BLOCKS);
    public static final Block RAW_FROGVASIUM_BLOCK = registerBlock("raw_frogvasium_block", new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).requiresTool().strength(4f,4f)), ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_RAIL = registerBlock("frogvasium_rail", new FrogvasiumRailBlock(FabricBlockSettings.copyOf(Blocks.RAIL).requiresTool().pistonBehavior(PistonBehavior.PUSH_ONLY)), ItemGroups.FUNCTIONAL);
    //(#A18594) 2x124
    public static final Block FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE = registerBlock("frogvasium_embedded_polished_blackstone", new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE).requiresTool()),ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_ATTACKER = registerBlock("frogvasium_attacker", new FrogvasiumAttackerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()) ,ItemGroups.REDSTONE);
    public static final Block FROGVASIUM_DEMOLISHER = registerBlock("frogvasium_demolisher", new FrogvasiumDemolisherBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()), ItemGroups.REDSTONE);
    public static final Block FROGVASIUM_GRAPPLER = registerBlock("frogvasium_grappler", new FrogvasiumGrapplerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()), ItemGroups.REDSTONE);
    public static final Block FROG_TRAP = registerBlock("frog_trap", new FrogTrapBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).requiresTool().strength(3.5f).pistonBehavior(PistonBehavior.BLOCK)), ItemGroups.REDSTONE);
    public static final Block FROG_CAGE = registerBlock("frog_cage", new FrogCageBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.BLOCK)), ItemGroups.REDSTONE);
    public static final Block MAILBOX = registerBlock("mailbox", new MailBoxBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.BONE).requiresTool().strength(3.2f)), ItemGroups.FUNCTIONAL);
    public static final Block RUBBER_LOG = registerBlock("rubber_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block RUBBER_WOOD = registerBlock("rubber_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block STRIPPED_RUBBER_LOG = registerBlock("stripped_rubber_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block STRIPPED_RUBBER_WOOD = registerBlock("stripped_rubber_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block RUBBER_PLANKS = registerBlock("rubber_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroups.NATURAL);
    public static final Block RUBBER_STAIRS = registerBlock("rubber_stairs", new StairsBlock(ModBlocks.RUBBER_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)), ItemGroups.BUILDING_BLOCKS);
    public static final Block RUBBER_SLAB = registerBlock("rubber_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)), ItemGroups.BUILDING_BLOCKS);
    public static final Block RUBBER_LEAVES = registerBlock("rubber_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(0.2F).nonOpaque().blockVision(ModBlocks::never)), ItemGroups.NATURAL);
    public static final Block RUBBER_SAPLING = registerBlock("rubber_sapling", new SaplingBlock(new SaplingGenerator("rubber", Optional.empty(), Optional.of(ModConfiguredFeatures.RUBBER_TREE_KEY), Optional.empty()),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).breakInstantly()), ItemGroups.NATURAL);
    public static final Block RUBBER_EXTRACTOR = registerBlock("rubber_extractor", new RubberExtractorBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).strength(2.2f).requiresTool().ticksRandomly()), ItemGroups.FUNCTIONAL);
    public static final Block KAURI_LOG = registerBlock("kauri_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block KAURI_WOOD = registerBlock("kauri_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block STRIPPED_KAURI_LOG = registerBlock("stripped_kauri_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block STRIPPED_KAURI_WOOD = registerBlock("stripped_kauri_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ItemGroups.NATURAL);
    public static final Block KAURI_PLANKS = registerBlock("kauri_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroups.NATURAL);
    public static final Block KAURI_STAIRS = registerBlock("kauri_stairs", new StairsBlock(ModBlocks.KAURI_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)), ItemGroups.BUILDING_BLOCKS);
    public static final Block KAURI_SLAB = registerBlock("kauri_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)), ItemGroups.BUILDING_BLOCKS);
    public static final Block KAURI_LEAVES = registerBlock("kauri_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(0.2F).nonOpaque().blockVision(ModBlocks::never)), ItemGroups.NATURAL);
    public static final Block KAURI_SAPLING = registerBlock("kauri_sapling", new SaplingBlock(new SaplingGenerator("kauri", Optional.of(ModConfiguredFeatures.KAURI_TREE_KEY), Optional.empty(), Optional.empty()),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).breakInstantly()), ItemGroups.NATURAL);
    public static final Block BROMELIAD = registerBlock("bromeliad", new TallFlowerBlock(FabricBlockSettings.copyOf(Blocks.SUNFLOWER).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ)), ItemGroups.NATURAL);
    public static final Block ORCHID = registerOrchidBlock("orchid", new OrchidBlock(ModEffects.IMPROVER, 3, FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ), OrchidType.PINK));
    public static final Block POTTED_ORCHID = registerBlockNoItem("potted_orchid", new FlowerPotBlock(ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block GREENWOOD_PORTAL = registerBlockNoItem("greenwood_portal", new GreenwoodPortalBlock(FabricBlockSettings.copyOf(Blocks.NETHER_PORTAL).pistonBehavior(PistonBehavior.BLOCK)));
    public static final Block FROG_FLAME = registerBlockNoItem("frog_flame", new FrogFlame(FabricBlockSettings.copyOf(Blocks.FIRE).noCollision().breakInstantly().luminance((state) -> 15).sounds(BlockSoundGroup.WOOL)));
    public static final Block FROG_STATUE = registerBlock("frog_statue", new FrogStatueBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).breakInstantly().nonOpaque()), ItemGroups.BUILDING_BLOCKS);
    public static final Block GOLDEN_FROG_STATUE = registerBlock("golden_frog_statue", new FrogStatueBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).breakInstantly().nonOpaque()), ItemGroups.BUILDING_BLOCKS);
    public static final Block QUEST_BLOCK = registerBlock("quest_block", new QuestBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).strength(3.5f,5f)), ItemGroups.BUILDING_BLOCKS);
    public static final Block HONEY_FUNGUS = registerBlock("honey_fungus", new HoneyFungusBlock(FabricBlockSettings.create().breakInstantly().collidable(false).noCollision().nonOpaque().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly()), ItemGroups.NATURAL);
    public static final Block MUD_FARMLAND = registerBlockNoItem("mud_farmland", new MudFarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND)));
    public static final Block SALI_TYSSE_CROP = registerBlockNoItem("sali_tysse_crop", new SaliTysseCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CHROMA_CLUMP = registerBlock("chroma_clump", new Block(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)), ItemGroups.BUILDING_BLOCKS);

    public static final Block PURPLE_ORCHID = registerOrchidBlock("purple_orchid", new OrchidBlock(StatusEffects.NAUSEA, 4, FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID), OrchidType.PURPLE));
    public static final Block DARK_PURPLE_ORCHID = registerOrchidBlock("dark_purple_orchid", new OrchidBlock(StatusEffects.MINING_FATIGUE, 20, FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID), OrchidType.DARK_PURPLE));
    public static final Block DARK_RED_ORCHID = registerOrchidBlock("dark_red_orchid", new OrchidBlock(StatusEffects.HEALTH_BOOST, 10, FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID), OrchidType.DARK_RED));
    public static final Block WHITE_ORCHID = registerOrchidBlock("white_orchid", new OrchidBlock(StatusEffects.HERO_OF_THE_VILLAGE, 60, FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID), OrchidType.WHITE));
    public static final Block BLACK_ORCHID = registerOrchidBlock("black_orchid", new BlackOrchidBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ORCHID).ticksRandomly()));
    public static final Block POTTED_PURPLE_ORCHID = registerBlockNoItem("potted_purple_orchid", new FlowerPotBlock(PURPLE_ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block POTTED_DARK_PURPLE_ORCHID = registerBlockNoItem("potted_dark_purple_orchid", new FlowerPotBlock(DARK_PURPLE_ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block POTTED_DARK_RED_ORCHID = registerBlockNoItem("potted_dark_red_orchid", new FlowerPotBlock(DARK_RED_ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block POTTED_WHITE_ORCHID = registerBlockNoItem("potted_white_orchid", new FlowerPotBlock(WHITE_ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block POTTED_BLACK_ORCHID = registerBlockNoItem("potted_black_orchid", new FlowerPotBlock(BLACK_ORCHID, FabricBlockSettings.copyOf(Blocks.POTTED_BLUE_ORCHID)));
    public static final Block FLORADIC_ALTAR = registerBlock("floradic_altar", new FloradicAltarBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool()), ItemGroups.BUILDING_BLOCKS);
    public static final Block SAVANNA_SOIL = registerBlock("savanna_soil", new Block(FabricBlockSettings.copyOf(Blocks.DIRT)), ItemGroups.NATURAL);
    public static final Block TROPICAL_ACACIA_SAPLING = registerBlock("tropical_acacia_sapling", new TropicalAcaciaSapling(new SaplingGenerator("tropical_acacia", Optional.empty(), Optional.of(ModConfiguredFeatures.TROPICAL_ACACIA_TREE_KEY), Optional.empty()),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)), ItemGroups.NATURAL);
    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Frogvasion.MOD_ID, name), block);
    }
    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> group) {
        registerItem(name, new BlockItem(block, new FabricItemSettings()), group);
        return Registry.register(Registries.BLOCK, new Identifier(Frogvasion.MOD_ID, name), block);
    }
    private static Block registerOrchidBlock(String name, OrchidBlock block) {
        registerItem(name, new OrchidBlockItem(block, new FabricItemSettings(), block.getOrchidType(), block.getOrchidIntensity()), ItemGroups.NATURAL);
        return Registry.register(Registries.BLOCK, new Identifier(Frogvasion.MOD_ID, name), block);
    }
    private static void registerItem(String name, BlockItem block, RegistryKey<ItemGroup> tab) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Frogvasion.MOD_ID, name), block);
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(item));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.FROGVASION_KEY).register(entries -> entries.add(item));
    }
    public static void registerModBlocks() {}
    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}