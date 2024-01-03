package me.Minestor.frogvasion.util.items;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> ORCHIDS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Frogvasion.MOD_ID, "orchids"));
    public static final TagKey<Block> QUEST_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Frogvasion.MOD_ID, "quest_blocks"));
    public static final TagKey<Block> POTTED_ORCHIDS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Frogvasion.MOD_ID, "potted_orchids"));
    public static final TagKey<Block> KAURI_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Frogvasion.MOD_ID, "kauri_logs"));
    public static final TagKey<Block> RUBBER_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Frogvasion.MOD_ID, "rubber_logs"));

    public static final TagKey<Item> QUEST_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(Frogvasion.MOD_ID, "quest_items"));
    public static final TagKey<Item> ORCHID_MAGIC_SOURCES = TagKey.of(RegistryKeys.ITEM, new Identifier(Frogvasion.MOD_ID, "orchid_magic_sources"));

    public static final TagKey<EntityType<?>> QUEST_TARGETS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "quest_targets"));
    public static final TagKey<EntityType<?>> HOSTILE_FROGS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "hostile_frogs"));

}
