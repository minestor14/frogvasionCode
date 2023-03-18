package me.Minestor.frogvasion.blocks;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block CONCENTRATED_SLIME = registerBlock("concentrated_slime",new ConcentratedSlimeBlock(FabricBlockSettings.copy(Blocks.SLIME_BLOCK).breakInstantly().nonOpaque()), ItemGroups.NATURAL);
    public static final Block SLIME_LAYER = registerBlockNoItem("slime_layer",new SlimeLayerBlock(FabricBlockSettings.of(Material.SNOW_LAYER).breakInstantly().noCollision().nonOpaque()));
    public static final Block CONVERSION_PEDESTAL = registerBlock("conversion_pedestal", new ConversionPedestalBlock(FabricBlockSettings.copy(Blocks.ENCHANTING_TABLE).strength(4f).requiresTool()), ItemGroups.FUNCTIONAL);
    public static final Block FROG_TOWER_CHEST = registerBlock("frog_tower_chest", new FrogTowerChestBlock(FabricBlockSettings.of(Material.NETHER_WOOD).sounds(BlockSoundGroup.WOOD)), ItemGroups.BUILDING_BLOCKS);
    public static final Block LAVA_INFUSED_FROGLIGHT = registerBlock("lava_infused_froglight", new LavaInfusedFroglightBlock(FabricBlockSettings.copyOf(Blocks.MAGMA_BLOCK).luminance(15)), ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_ORE = registerBlock("frogvasium_ore", new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)), ItemGroups.NATURAL);
    public static final Block DEEPSLATE_FROGVASIUM_ORE = registerBlock("deepslate_frogvasium_ore", new ExperienceDroppingBlock(FabricBlockSettings.copyOf(FROGVASIUM_ORE).requiresTool().strength(3.0F, 3.0F).mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE)), ItemGroups.NATURAL);
    public static final Block FROGVASIUM_BLOCK = registerBlock("frogvasium_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().strength(4f,4f).jumpVelocityMultiplier(1.2f)), ItemGroups.BUILDING_BLOCKS);
    public static final Block RAW_FROGVASIUM_BLOCK = registerBlock("raw_frogvasium_block", new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).requiresTool().strength(4f,4f)), ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_RAIL = registerBlock("frogvasium_rail", new FrogvasiumRailBlock(FabricBlockSettings.copyOf(Blocks.RAIL).requiresTool()), ItemGroups.FUNCTIONAL);
    //(#A18594) 2x124
    public static final Block FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE = registerBlock("frogvasium_embedded_polished_blackstone", new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE).requiresTool()),ItemGroups.BUILDING_BLOCKS);
    public static final Block FROGVASIUM_ATTACKER = registerBlock("frogvasium_attacker", new FrogvasiumAttackerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()) ,ItemGroups.REDSTONE);
    public static final Block FROGVASIUM_DEMOLISHER = registerBlock("frogvasium_demolisher", new FrogvasiumDemolisherBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()), ItemGroups.REDSTONE);
    public static final Block FROGVASIUM_GRAPPLER = registerBlock("frogvasium_grappler", new FrogvasiumGrapplerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER).requiresTool().nonOpaque()), ItemGroups.REDSTONE);
    public static final Block FROG_TRAP = registerBlock("frog_trap", new FrogTrapBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).requiresTool().strength(3.5f)), ItemGroups.REDSTONE);
    public static final Block FROG_CAGE = registerBlock("frog_cage", new FrogCageBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOL)), ItemGroups.REDSTONE);
    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Frogvasion.MOD_ID, name), block);
    }
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerItem(name, new BlockItem(block, new FabricItemSettings()), group);
        return Registry.register(Registries.BLOCK, new Identifier(Frogvasion.MOD_ID, name), block);
    }
    private static Item registerItem(String name, BlockItem block, ItemGroup tab) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Frogvasion.MOD_ID, name), block);
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(item));
        return item;
    }
    public static void registerModBlocks() {}
}
