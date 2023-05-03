package me.Minestor.frogvasion.worldgen.features;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogTowerChestBlock;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> TOWER_CRATES_KEY = registerKey("tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> NETHER_TOWER_CRATES_KEY = registerKey("nether_tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> END_TOWER_CRATES_KEY = registerKey("end_tower_chest_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> FROGVASIUM_ORE_KEY = registerKey("frogvasium_ore_key");
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
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Frogvasion.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
