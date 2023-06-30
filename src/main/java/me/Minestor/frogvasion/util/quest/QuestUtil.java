package me.Minestor.frogvasion.util.quest;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Random;

public abstract class QuestUtil {
    public static final List<Item> POSSIBLE_ITEMS = List.of(
            ModItems.RAW_FROGVASIUM,
            ModItems.ENHANCED_MUTAGEN,
            ModItems.ICE_SPIKE,
            ModItems.UNPROCESSED_RUBBER,
            ModItems.RUBBER,
            ModItems.FROG_LEGS
    );
    public static final List<EntityType<? extends Entity>> POSSIBLE_TARGETS = List.of(
            EntityType.CHICKEN,
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.DONKEY,
            EntityType.GOAT,
            EntityType.ENDERMITE,
            EntityType.LLAMA,
            EntityType.SPIDER,
            EntityType.SQUID,
            EntityType.PIGLIN,
            EntityType.WITCH,
            ModEntities.ENDER_FROG_ENTITY,
            ModEntities.EXPLOSIVE_FROG_ENTITY,
            ModEntities.SOLDIER_FROG_ENTITY,
            ModEntities.NORMAL_TREE_FROG_ENTITY
    );
    public static final List<Block> POSSIBLE_BLOCKS = List.of(
            ModBlocks.FROGVASIUM_ORE,
            ModBlocks.STRIPPED_RUBBER_LOG,
            ModBlocks.KAURI_LOG,
            ModBlocks.ORCHID,
            Blocks.AZALEA,
            Blocks.SHROOMLIGHT,
            Blocks.DEAD_BUSH
    );
    public static final List<Item> POSSIBLE_CRAFTS = List.of(
            Items.FISHING_ROD,
            Items.CRAFTING_TABLE,
            Items.GOLDEN_PICKAXE,
            Items.BLUE_CONCRETE_POWDER,
            ModItems.FROGVASIUM_NUGGET,
            ModBlocks.KAURI_PLANKS.asItem(),
            ModBlocks.RUBBER_WOOD.asItem()
    );
    public static Item getRandomCollectable() {
        return POSSIBLE_ITEMS.get(new Random().nextInt(POSSIBLE_ITEMS.size()));
    }
    public static Item getRandomCraftable() {
        return POSSIBLE_CRAFTS.get(new Random().nextInt(POSSIBLE_CRAFTS.size()));
    }
    public static Block getRandomMineable() {
        return POSSIBLE_BLOCKS.get(new Random().nextInt(POSSIBLE_BLOCKS.size()));
    }
    public static EntityType<? extends Entity> getRandomTargetable() {
        return POSSIBLE_TARGETS.get(new Random().nextInt(POSSIBLE_TARGETS.size()));
    }
}
