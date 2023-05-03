package me.Minestor.frogvasion;

import me.Minestor.frogvasion.Recipe.ModRecipes;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.commands.SetMessageCommand;
import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.effects.potion.ModPotions;
import me.Minestor.frogvasion.enchantments.ModEnchantments;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.screen.ModScreenHandlers;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.*;
import me.Minestor.frogvasion.worldgen.features.ModFeaturesPlacing;
import me.Minestor.frogvasion.worldgen.spawing.ModEntitySpawning;
import me.Minestor.frogvasion.worldgen.structures.ModStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class Frogvasion implements ModInitializer {
	public static final String MOD_ID = "frogvasion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModFeaturesPlacing.registerPlacedFeatures();
		ModStructures.registerStructures();

		ModEntityGroups.registerEntityGroups();
		ModArmorMaterials.registerArmorMaterials();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModThrowables.registerThrowables();
		ModBlocks.registerModBlocks();

		initAttributes();
		initWorldGen();
		registerCommands();
		ModSounds.initSounds();
		ModEffects.initEffects();
		ModPotions.registerPotions();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModEnchantments.registerEnchantments();

		ModRecipes.registerRecipes();
		ModLootTableModifiers.modifyLootTables();

		ModDamageSources.registerDamageSources();
		ModMessages.registerC2SPackets();
		LOGGER.info("Frogvasion initialized");
	}

	private static void initWorldGen() {
		ModEntitySpawning.addEntitySpawning();
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

	}

	private static void registerCommands() {
		CommandRegistrationCallback.EVENT.register(SetMessageCommand::register);
	}
	//todo make frogs breed w/ frogvasium
	//todo capture update, maybe capture nets
	//todo custom dimention w/ glass frogs (w/ society)
}
