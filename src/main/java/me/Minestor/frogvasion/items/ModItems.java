package me.Minestor.frogvasion.items;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.items.Custom.*;
import me.Minestor.frogvasion.items.Custom.frog_ghosts.EmptyFrogGhostItem;
import me.Minestor.frogvasion.items.Custom.frog_ghosts.ModFrogGhostItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item ICE_SPIKE = registerItem("ice_spike", new Item(new FabricItemSettings()),ItemGroups.COMBAT);
    public static final Item SOLDIER_FROG_SPAWN_EGG = registerItem("soldier_frog_spawn_egg", new SpawnEggItem(ModEntities.SOLDIER_FROG_ENTITY, 16741227, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item BOSS_SOLDIER_FROG_SPAWN_EGG = registerItem("boss_soldier_frog_spawn_egg", new SpawnEggItem(ModEntities.BOSS_SOLDIER_FROG_ENTITY, 16726077, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item EXPLOSIVE_FROG_SPAWN_EGG = registerItem("explosive_frog_spawn_egg", new SpawnEggItem(ModEntities.EXPLOSIVE_FROG_ENTITY, 12325158, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item GROWING_FROG_SPAWN_EGG = registerItem("growing_frog_spawn_egg", new SpawnEggItem(ModEntities.GROWING_FROG_ENTITY,8585215, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item ARMED_FROG_SPAWN_EGG = registerItem("armed_frog_spawn_egg", new SpawnEggItem(ModEntities.ARMED_FROG_ENTITY, 16741179, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item TADPOLE_ROCKET_SPAWN_EGG = registerItem("tadpole_rocket_spawn_egg", new SpawnEggItem(ModEntities.TADPOLE_ROCKET_ENTITY, 5454115, 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item ENDER_FROG_SPAWN_EGG = registerItem("ender_frog_spawn_egg", new SpawnEggItem(ModEntities.ENDER_FROG_ENTITY,786689 , 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item GRAPPLING_FROG_SPAWN_EGG = registerItem("grappling_frog_spawn_egg", new SpawnEggItem(ModEntities.GRAPPLING_FROG_ENTITY,14049821 , 16751544, new FabricItemSettings()),ItemGroups.SPAWN_EGGS);
    public static final Item GRAPPLING_TONGUE = registerItem("grappling_tongue",new Item(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item SLIME_LIKE_GOO = registerItem("slime_like_goo", new SlimeLikeGooItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item FROG_HELMET_ITEM = registerItem("frog_helmet", new FrogHelmetItem(ArmorMaterials.LEATHER, new FabricItemSettings()),ItemGroups.COMBAT);
    public static final Item FROG_HIDE = registerItem("frog_hide", new Item(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item ENHANCED_MUTAGEN = registerItem("enhanced_mutagen", new Item(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item SPINE = registerItem("spine", new Item(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item EMPTY_FROG_GHOST_FRAGMENT = registerItem("empty_frog_ghost_fragment", new Item(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item EMPTY_FROG_GHOST = registerItem("empty_frog_ghost", new EmptyFrogGhostItem(new FabricItemSettings()),ItemGroups.INGREDIENTS);
    public static final Item GROWING_FROG_GHOST = registerItem("growing_frog_ghost", new ModFrogGhostItem.GrowingFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item SOLDIER_FROG_GHOST = registerItem("soldier_frog_ghost", new ModFrogGhostItem.SoldierFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item ARMED_FROG_GHOST = registerItem("armed_frog_ghost", new ModFrogGhostItem.ArmedFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item GRAPPLING_FROG_GHOST = registerItem("grappling_frog_ghost", new ModFrogGhostItem.GrapplingFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item BOSS_SOLDIER_FROG_GHOST = registerItem("boss_soldier_frog_ghost", new ModFrogGhostItem.BossSoldierFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item ENDER_FROG_GHOST = registerItem("ender_frog_ghost", new ModFrogGhostItem.EnderFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item EXPLOSIVE_FROG_GHOST = registerItem("explosive_frog_ghost", new ModFrogGhostItem.ExplosiveFrogGhostItem(new FabricItemSettings()),ItemGroups.FUNCTIONAL);
    public static final Item ADDRESS_CARD = registerItem("address_card", new AddressCardItem(new FabricItemSettings()),ItemGroups.TOOLS);
    public static final Item RAW_FROGVASIUM = registerItem("raw_frogvasium", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROGVASIUM_INGOT = registerItem("frogvasium_ingot", new FrogvasiumIngotItem(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROGVASIUM_NUGGET = registerItem("frogvasium_nugget", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROG_STAFF = registerItem("frog_staff", new FrogStaffItem(new FabricItemSettings().maxCount(1).maxDamage(50)), ItemGroups.TOOLS);
    //TODO recipes
    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(Frogvasion.MOD_ID, name), item);
    }
    public static void registerModItems() {}
}