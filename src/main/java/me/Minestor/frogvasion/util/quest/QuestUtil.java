package me.Minestor.frogvasion.util.quest;

import me.Minestor.frogvasion.util.items.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class QuestUtil {
    public static Item getRandomCollectable() {
        List<Item> POSSIBLE_ITEMS = new ArrayList<>();
        for (RegistryEntry<Item> item : Registries.ITEM.iterateEntries(ModTags.QUEST_ITEMS)) {
            POSSIBLE_ITEMS.add(item.value());
        }

        return POSSIBLE_ITEMS.get(new Random().nextInt(POSSIBLE_ITEMS.size()));
    }
    public static Item getRandomCraftable() {
        List<Item> POSSIBLE_CRAFTS = new ArrayList<>();
        for (RegistryEntry<Item> item : Registries.ITEM.iterateEntries(ModTags.QUEST_CRAFTS)) {
            POSSIBLE_CRAFTS.add(item.value());
        }

        return POSSIBLE_CRAFTS.get(new Random().nextInt(POSSIBLE_CRAFTS.size()));
    }
    public static Block getRandomMineable() {
        List<Block> POSSIBLE_BLOCKS = new ArrayList<>();
        for (RegistryEntry<Block> block : Registries.BLOCK.iterateEntries(ModTags.QUEST_BLOCKS)) {
            POSSIBLE_BLOCKS.add(block.value());
        }

        return POSSIBLE_BLOCKS.get(new Random().nextInt(POSSIBLE_BLOCKS.size()));
    }
    public static EntityType<? extends Entity> getRandomTargetable() {
        List<EntityType<?>> POSSIBLE_TARGETS = new ArrayList<>();
        for (RegistryEntry<EntityType<?>> entity : Registries.ENTITY_TYPE.iterateEntries(ModTags.QUEST_TARGETS)) {
            POSSIBLE_TARGETS.add(entity.value());
        }

        return POSSIBLE_TARGETS.get(new Random().nextInt(POSSIBLE_TARGETS.size()));
    }
}
