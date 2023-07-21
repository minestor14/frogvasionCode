package me.Minestor.frogvasion;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.effects.potion.ModPotions;
import me.Minestor.frogvasion.enchantments.ModEnchantments;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.events.*;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.recipe.ModRecipes;
import me.Minestor.frogvasion.screen.ModScreenHandlers;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.armor.ModArmorMaterials;
import me.Minestor.frogvasion.util.blocks.ModFlammables;
import me.Minestor.frogvasion.util.entity.ModDamageSources;
import me.Minestor.frogvasion.util.entity.ModEntityGroups;
import me.Minestor.frogvasion.util.items.ModItemGroups;
import me.Minestor.frogvasion.util.items.ModLootTableModifiers;
import me.Minestor.frogvasion.util.items.ModThrowables;
import me.Minestor.frogvasion.util.quest.ServerQuestProgression;
import me.Minestor.frogvasion.worldgen.dimension.ModDimensions;
import me.Minestor.frogvasion.worldgen.features.ModFeaturesPlacing;
import me.Minestor.frogvasion.worldgen.spawing.ModEntitySpawning;
import me.Minestor.frogvasion.worldgen.structures.ModStructures;
import me.Minestor.frogvasion.worldgen.tree.ModTreeGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class Frogvasion implements ModInitializer {
	public static final String MOD_ID = "frogvasion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		initWorldGen();

		ModEntityGroups.registerEntityGroups();
		ModArmorMaterials.registerArmorMaterials();

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModThrowables.registerThrowables();

		ModBlocks.registerModBlocks();
		StrippableBlockRegistry.register(ModBlocks.RUBBER_LOG, ModBlocks.STRIPPED_RUBBER_LOG);
		StrippableBlockRegistry.register(ModBlocks.RUBBER_WOOD, ModBlocks.STRIPPED_RUBBER_WOOD);
		StrippableBlockRegistry.register(ModBlocks.KAURI_LOG, ModBlocks.STRIPPED_KAURI_LOG);
		StrippableBlockRegistry.register(ModBlocks.KAURI_WOOD, ModBlocks.STRIPPED_KAURI_WOOD);
		ModFlammables.register();

		initAttributes();

		registerEvents();
		ModSounds.initSounds();

		ModEffects.initEffects();
		ModPotions.registerPotions();

		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerEnchantments();

		ModRecipes.registerRecipes();
		ModLootTableModifiers.modifyLootTables();

		ModDamageSources.registerDamageSources();
		ModMessages.registerC2SPackets();

		ModScreenHandlers.registerScreenHandlers();
		LOGGER.info("Frogvasion initialized");
	}

	private static void initWorldGen() {
		ModFeaturesPlacing.registerPlacedFeatures();
		ModStructures.registerStructures();
		ModEntitySpawning.addEntitySpawning();
		ModDimensions.register();
		ModTreeGeneration.generateTrees();
	}

	private static void initAttributes() {
		GeckoLib.initialize();
		FabricDefaultAttributeRegistry.register(ModEntities.SOLDIER_FROG_ENTITY, SoldierFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BOSS_SOLDIER_FROG_ENTITY, BossSoldierFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.EXPLOSIVE_FROG_ENTITY, ExplosiveFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GROWING_FROG_ENTITY, GrowingFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARMED_FROG_ENTITY, ArmedFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TADPOLE_ROCKET_ENTITY, TadpoleRocket.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GRAPPLING_FROG_ENTITY, GrapplingFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ENDER_FROG_ENTITY, EnderFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ICE_FROG_ENTITY, IceFrog.setAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.NORMAL_TREE_FROG_ENTITY, NormalTreeFrog.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GLIDING_TREE_FROG_ENTITY, GlidingTreeFrog.setAttributes());
	}

	private static void registerEvents() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new DamageEvent());
		PlayerBlockBreakEvents.BEFORE.register(new BlockBreakEvent());
		ServerLivingEntityEvents.ALLOW_DEATH.register(new DeathEvent());
		ServerQuestProgression.IQuestProgressionEvent.PROGRESS.register(new QuestProgressionEvent());
		ServerQuestProgression.IQuestCompletionEvent.COMPLETION.register(new QuestCompletionEvent());
	}
	//todo new biome in greenwood, swamplike, very humid, lots of mushrooms/mold, check chatGPT
	//todo new structures, maybe polevillages w/ bridges in between
}