package me.Minestor.frogvasion.worldgen.features;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogTowerChestBlock;
import me.Minestor.frogvasion.blocks.custom.MudFarmlandBlock;
import me.Minestor.frogvasion.blocks.custom.SaliTysseCropBlock;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.BiasedToBottomIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> TOWER_CRATES_KEY = registerKey("tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> NETHER_TOWER_CRATES_KEY = registerKey("nether_tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> END_TOWER_CRATES_KEY = registerKey("end_tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> FROGVASIUM_ORE_KEY = registerKey("frogvasium_ore_key");
    public static final RegistryKey<ConfiguredFeature<?,?>> RUBBER_TREE_KEY = registerKey("rubber_tree");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLOWER_ORCHID_KEY = registerKey("flower_orchid");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLOWER_BROMELIAD_KEY = registerKey("flower_bromeliad");
    public static final RegistryKey<ConfiguredFeature<?,?>> KAURI_TREE_KEY = registerKey("kauri_tree");
    public static final RegistryKey<ConfiguredFeature<?,?>> MARSH_COLUMN_KEY = registerKey("marsh_column");
    public static final RegistryKey<ConfiguredFeature<?,?>> MARSH_DISK_KEY = registerKey("marsh_disk");
    public static final RegistryKey<ConfiguredFeature<?,?>> MARSH_LAKE_KEY = registerKey("marsh_lake");
    public static final RegistryKey<ConfiguredFeature<?,?>> MARSH_DELTA_KEY = registerKey("marsh_delta");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLOWER_HONEY_FUNGUS_KEY = registerKey("flower_honey_fungus");
    public static final RegistryKey<ConfiguredFeature<?,?>> SALI_TYSSE_PLANT_KEY = registerKey("sali_tysse_plant");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        var placedFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest blackstoneReplaceables = new BlockMatchRuleTest(Blocks.BLACKSTONE);
        RuleTest endstoneReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldTowerChests =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.FROG_TOWER_CHEST.getDefaultState().with(FrogTowerChestBlock.LEVEL,1)),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.FROG_TOWER_CHEST.getDefaultState().with(FrogTowerChestBlock.LEVEL,2)));
        List<OreFeatureConfig.Target> netherTowerChests =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.FROG_TOWER_CHEST.getDefaultState().with(FrogTowerChestBlock.LEVEL,3)),
                        OreFeatureConfig.createTarget(blackstoneReplaceables, ModBlocks.FROG_TOWER_CHEST.getDefaultState().with(FrogTowerChestBlock.LEVEL,4)));
        List<OreFeatureConfig.Target> endTowerChests = List.of(OreFeatureConfig.createTarget(endstoneReplaceables, ModBlocks.FROG_TOWER_CHEST.getDefaultState().with(FrogTowerChestBlock.LEVEL,4)));
        List<OreFeatureConfig.Target> frogvasiumOre =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.FROGVASIUM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_FROGVASIUM_ORE.getDefaultState()));

        register(context, TOWER_CRATES_KEY, Feature.ORE, new OreFeatureConfig(overworldTowerChests, 3));
        register(context, NETHER_TOWER_CRATES_KEY, Feature.ORE, new OreFeatureConfig(netherTowerChests, 3));
        register(context, END_TOWER_CRATES_KEY, Feature.ORE, new OreFeatureConfig(endTowerChests,2));
        register(context, FROGVASIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(frogvasiumOre, 6));

        register(context, RUBBER_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.RUBBER_LOG),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.of(ModBlocks.RUBBER_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, FLOWER_ORCHID_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.ORCHID)))));
        register(context, FLOWER_BROMELIAD_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(50, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BROMELIAD)))));

        register(context, KAURI_TREE_KEY, Feature.TREE,
                (new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.KAURI_LOG), new MegaJungleTrunkPlacer(20, 2, 19),
                        BlockStateProvider.of(ModBlocks.KAURI_LEAVES), new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
                        new TwoLayersFeatureSize(1, 1, 2))).build());


        register(context, MARSH_COLUMN_KEY, Feature.BLOCK_PILE, new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.PACKED_MUD)));
        register(context, MARSH_DISK_KEY, Feature.DISK, new DiskFeatureConfig(new PredicatedStateProvider(BlockStateProvider.of(Blocks.MUD), List.of()),
                BlockPredicate.anyOf(BlockPredicate.matchingBlocks(Blocks.MUD), BlockPredicate.matchingBlockTag(BlockTags.CONVERTABLE_TO_MUD)), BiasedToBottomIntProvider.create(4,8), 2));

        register(context, MARSH_LAKE_KEY, Feature.LAKE, new LakeFeature.Config(BlockStateProvider.of(Blocks.WATER), BlockStateProvider.of(Blocks.MUD)));
        register(context, MARSH_DELTA_KEY, Feature.DELTA_FEATURE, new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.MUD.getDefaultState(), UniformIntProvider.create(3,7), UniformIntProvider.create(1,2)));

        register(context, FLOWER_HONEY_FUNGUS_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(30, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.HONEY_FUNGUS)))));

        register(context, SALI_TYSSE_PLANT_KEY, Feature.BLOCK_COLUMN, new BlockColumnFeatureConfig(
                List.of(new BlockColumnFeatureConfig.Layer(ConstantIntProvider.create(1), BlockStateProvider.of(ModBlocks.MUD_FARMLAND.getDefaultState().with(MudFarmlandBlock.MOISTURE, 7))),
                        new BlockColumnFeatureConfig.Layer(ConstantIntProvider.create(1), BlockStateProvider.of(ModBlocks.SALI_TYSSE_CROP.getDefaultState().with(SaliTysseCropBlock.AGE, 5)))),
                Direction.UP, BlockPredicate.insideWorldBounds(new Vec3i(0,1,0)),
                true));
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Frogvasion.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
