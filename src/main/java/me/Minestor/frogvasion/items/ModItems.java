package me.Minestor.frogvasion.items;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.items.custom.*;
import me.Minestor.frogvasion.items.custom.frog_ghosts.EmptyFrogGhostItem;
import me.Minestor.frogvasion.items.custom.frog_ghosts.ModFrogGhostItem;
import me.Minestor.frogvasion.util.armor.ModArmorMaterials;
import me.Minestor.frogvasion.util.items.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item ICE_SPIKE = registerItem("ice_spike", new IceSpikeItem(new FabricItemSettings().maxCount(16)), ItemGroups.COMBAT);
    public static final Item GRAPPLING_TONGUE = registerItem("grappling_tongue",new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item SLIME_LIKE_GOO = registerItem("slime_like_goo", new SlimeLikeGooItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item FROG_HELMET_ITEM = registerItem("frog_helmet", new FrogHelmetItem(ArmorMaterials.LEATHER, new FabricItemSettings()), ItemGroups.COMBAT);
    public static final Item FROG_HIDE = registerItem("frog_hide", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item ENHANCED_MUTAGEN = registerItem("enhanced_mutagen", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item SPINE = registerItem("spine", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item EMPTY_FROG_GHOST_FRAGMENT = registerItem("empty_frog_ghost_fragment", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item EMPTY_FROG_GHOST = registerItem("empty_frog_ghost", new EmptyFrogGhostItem(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item GROWING_FROG_GHOST = registerItem("growing_frog_ghost", new ModFrogGhostItem.GrowingFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item SOLDIER_FROG_GHOST = registerItem("soldier_frog_ghost", new ModFrogGhostItem.SoldierFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item ARMED_FROG_GHOST = registerItem("armed_frog_ghost", new ModFrogGhostItem.ArmedFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item GRAPPLING_FROG_GHOST = registerItem("grappling_frog_ghost", new ModFrogGhostItem.GrapplingFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item BOSS_SOLDIER_FROG_GHOST = registerItem("boss_soldier_frog_ghost", new ModFrogGhostItem.BossSoldierFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item ENDER_FROG_GHOST = registerItem("ender_frog_ghost", new ModFrogGhostItem.EnderFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item EXPLOSIVE_FROG_GHOST = registerItem("explosive_frog_ghost", new ModFrogGhostItem.ExplosiveFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item ICE_FROG_GHOST = registerItem("ice_frog_ghost", new ModFrogGhostItem.IceFrogGhostItem(new FabricItemSettings()), ItemGroups.FUNCTIONAL);
    public static final Item ADDRESS_CARD = registerItem("address_card", new AddressCardItem(new FabricItemSettings().maxCount(1)), ItemGroups.TOOLS);
    public static final Item RAW_FROGVASIUM = registerItem("raw_frogvasium", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROGVASIUM_INGOT = registerItem("frogvasium_ingot", new FrogvasiumIngotItem(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROGVASIUM_NUGGET = registerItem("frogvasium_nugget", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FROG_STAFF = registerItem("frog_staff", new FrogStaffItem(new FabricItemSettings().maxCount(1).maxDamage(50)), ItemGroups.TOOLS);
    public static final Item COMBAT_FROG_STAFF = registerItem("combat_frog_staff", new CombatFrogStaff(new FabricItemSettings().maxCount(1).maxDamage(75)), ItemGroups.COMBAT);
    public static final Item JUMP_FROG_STAFF = registerItem("jump_frog_staff", new JumpFrogStaff(new FabricItemSettings().maxCount(1).maxDamage(75)), ItemGroups.FUNCTIONAL);
    public static final Item GHOST_FRAGMENT_HELMET = registerItem("ghost_fragment_helmet", new ArmorItem(ModArmorMaterials.GHOST_FRAGMENT, ArmorItem.Type.HELMET, new Item.Settings()), ItemGroups.COMBAT);
    public static final Item GHOST_FRAGMENT_CHESTPLATE = registerItem("ghost_fragment_chestplate", new ArmorItem(ModArmorMaterials.GHOST_FRAGMENT, ArmorItem.Type.CHESTPLATE, new Item.Settings()), ItemGroups.COMBAT);
    public static final Item GHOST_FRAGMENT_LEGGINGS = registerItem("ghost_fragment_leggings", new ArmorItem(ModArmorMaterials.GHOST_FRAGMENT, ArmorItem.Type.LEGGINGS, new Item.Settings()), ItemGroups.COMBAT);
    public static final Item GHOST_FRAGMENT_BOOTS = registerItem("ghost_fragment_boots", new ArmorItem(ModArmorMaterials.GHOST_FRAGMENT, ArmorItem.Type.BOOTS, new Item.Settings()), ItemGroups.COMBAT);
    public static final Item FROG_LEGS = registerItem("frog_legs", new Item(new FabricItemSettings().food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(0.2f).build())), ItemGroups.FOOD_AND_DRINK);
    public static final Item COOKED_FROG_LEGS = registerItem("cooked_frog_legs", new Item(new FabricItemSettings().food(new FoodComponent.Builder().meat().hunger(4).saturationModifier(0.3f).build())), ItemGroups.FOOD_AND_DRINK);
    public static final Item UNPROCESSED_RUBBER = registerItem("unprocessed_rubber", new Item(new FabricItemSettings()), ItemGroups.NATURAL);
    public static final Item RUBBER = registerItem("rubber", new RubberItem(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item JUMPY_TOTEM = registerItem("jumpy_totem", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)), ItemGroups.TOOLS);
    public static final Item FROG_FIRE_CHARGE = registerItem("frog_firecharge", new BlockItem(ModBlocks.FROG_FLAME, new FabricItemSettings()), ItemGroups.TOOLS);
    public static final Item SALI_TYSSE_SEEDS = registerItem("sali_tysse_seeds", new AliasedBlockItem(ModBlocks.SALI_TYSSE_CROP,new FabricItemSettings()), ItemGroups.FOOD_AND_DRINK);
    public static final Item SALI_TYSSE = registerItem("sali_tysse", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(3).build())), ItemGroups.FOOD_AND_DRINK);

    public static final Item PINK_ORCHID_BOUQUET = registerItem("pink_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.PINK), ItemGroups.INGREDIENTS);
    public static final Item BLUE_ORCHID_BOUQUET = registerItem("blue_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.BLUE), ItemGroups.INGREDIENTS);
    public static final Item PURPLE_ORCHID_BOUQUET = registerItem("purple_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.PURPLE), ItemGroups.INGREDIENTS);
    public static final Item DARK_PURPLE_ORCHID_BOUQUET = registerItem("dark_purple_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.DARK_PURPLE), ItemGroups.INGREDIENTS);
    public static final Item DARK_RED_ORCHID_BOUQUET = registerItem("dark_red_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.DARK_RED), ItemGroups.INGREDIENTS);
    public static final Item WHITE_ORCHID_BOUQUET = registerItem("white_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.WHITE), ItemGroups.INGREDIENTS);
    public static final Item BLACK_ORCHID_BOUQUET = registerItem("black_orchid_bouquet", new OrchidBouquetItem(new FabricItemSettings(), OrchidType.BLACK), ItemGroups.INGREDIENTS);
    public static final Item ORCHID_MIX = registerItem("orchid_mix", new OrchidMixItem(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item PINK_ORCHID_POWDER = registerItem("pink_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.PINK), ItemGroups.INGREDIENTS);
    public static final Item BLUE_ORCHID_POWDER = registerItem("blue_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.BLUE), ItemGroups.INGREDIENTS);
    public static final Item PURPLE_ORCHID_POWDER = registerItem("purple_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.PURPLE), ItemGroups.INGREDIENTS);
    public static final Item DARK_PURPLE_ORCHID_POWDER = registerItem("dark_purple_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.DARK_PURPLE), ItemGroups.INGREDIENTS);
    public static final Item DARK_RED_ORCHID_POWDER = registerItem("dark_red_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.DARK_RED), ItemGroups.INGREDIENTS);
    public static final Item WHITE_ORCHID_POWDER = registerItem("white_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.WHITE), ItemGroups.INGREDIENTS);
    public static final Item BLACK_ORCHID_POWDER = registerItem("black_orchid_powder", new OrchidPowderItem(new FabricItemSettings(), OrchidType.BLACK), ItemGroups.INGREDIENTS);
    public static final Item ORCHID_GRENADE_SHELL = registerItem("orchid_grenade_shell", new Item(new FabricItemSettings()), ItemGroups.COMBAT);
    public static final Item ORCHID_GRENADE = registerItem("orchid_grenade", new OrchidGrenadeItem(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(8)), ItemGroups.COMBAT);
    public static final Item ALTAR_MANUAL = registerItem("altar_manual", new AltarManualItem(new FabricItemSettings().maxCount(1).fireproof()), ItemGroups.TOOLS);
    //todo recipes

    //spawn eggs
    public static final Item SOLDIER_FROG_SPAWN_EGG = registerItem("soldier_frog_spawn_egg", new SpawnEggItem(ModEntities.SOLDIER_FROG_ENTITY, 16741227, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item BOSS_SOLDIER_FROG_SPAWN_EGG = registerItem("boss_soldier_frog_spawn_egg", new SpawnEggItem(ModEntities.BOSS_SOLDIER_FROG_ENTITY, 16726077, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item EXPLOSIVE_FROG_SPAWN_EGG = registerItem("explosive_frog_spawn_egg", new SpawnEggItem(ModEntities.EXPLOSIVE_FROG_ENTITY, 12325158, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item GROWING_FROG_SPAWN_EGG = registerItem("growing_frog_spawn_egg", new SpawnEggItem(ModEntities.GROWING_FROG_ENTITY,8585215, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item ARMED_FROG_SPAWN_EGG = registerItem("armed_frog_spawn_egg", new SpawnEggItem(ModEntities.ARMED_FROG_ENTITY, 16741179, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item TADPOLE_ROCKET_SPAWN_EGG = registerItem("tadpole_rocket_spawn_egg", new SpawnEggItem(ModEntities.TADPOLE_ROCKET_ENTITY, 5454115, 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item ENDER_FROG_SPAWN_EGG = registerItem("ender_frog_spawn_egg", new SpawnEggItem(ModEntities.ENDER_FROG_ENTITY,786689 , 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item GRAPPLING_FROG_SPAWN_EGG = registerItem("grappling_frog_spawn_egg", new SpawnEggItem(ModEntities.GRAPPLING_FROG_ENTITY,14049821 , 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item ICE_FROG_SPAWN_EGG = registerItem("ice_frog_spawn_egg", new SpawnEggItem(ModEntities.ICE_FROG_ENTITY,9807304 , 16751544, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item NORMAL_TREE_FROG_SPAWN_EGG = registerItem("normal_tree_frog_spawn_egg", new SpawnEggItem(ModEntities.NORMAL_TREE_FROG_ENTITY,9941578 , 5793327, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    public static final Item GLIDING_TREE_FROG_SPAWN_EGG = registerItem("gliding_tree_frog_spawn_egg", new SpawnEggItem(ModEntities.GLIDING_TREE_FROG_ENTITY,15390158 , 5793327, new FabricItemSettings()), ItemGroups.SPAWN_EGGS);
    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.FROGVASION_KEY).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(Frogvasion.MOD_ID, name), item);
    }
    public static void registerModItems() {}
}