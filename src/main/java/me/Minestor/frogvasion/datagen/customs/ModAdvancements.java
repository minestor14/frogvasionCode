package me.Minestor.frogvasion.datagen.customs;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.worldgen.dimension.ModDimensions;
import me.Minestor.frogvasion.worldgen.structures.ModStructures;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.Blocks;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.FluidPredicate;
import net.minecraft.predicate.LightPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModAdvancements implements Consumer<Consumer<Advancement>>{
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement rootAdvancement = Advancement.Builder.create()
                .display(ModBlocks.LAVA_INFUSED_FROGLIGHT,
                        Text.literal("Frogvasion"),
                        Text.literal("Make you ready to fight an endless army of hostile frogs"),
                        new Identifier(Frogvasion.MOD_ID, "textures/gui/advancements/backgrounds/frogvasion.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false)
                .criterion("soldier", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.SOLDIER_FROG_ENTITY).build()))
                .criterion("boss_soldier", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.BOSS_SOLDIER_FROG_ENTITY).build()))
                .criterion("explosive", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.EXPLOSIVE_FROG_ENTITY).build()))
                .criterion("ender", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.ENDER_FROG_ENTITY).build()))
                .criterion("armed", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.ARMED_FROG_ENTITY).build()))
                .criterion("growing", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.GROWING_FROG_ENTITY).build()))
                .criterion("grappling", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.GRAPPLING_FROG_ENTITY).build()))
                .criterion("ice", OnKilledCriterion.Conditions.createPlayerKilledEntity(new EntityPredicate.Builder().type(ModEntities.ICE_FROG_ENTITY).build()))
                .requirements(new String[][]{{"soldier","boss_soldier","explosive","ender","armed","growing","grappling","ice"}})
                .build(consumer, "frogvasion" + "/root");
        Advancement aMAZEing = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        Blocks.MUD_BRICKS,
                        Text.literal("aMAZEing"),
                        Text.literal("Enter the Frog Maze, the almost endless collection of hallways"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("in_maze", TickCriterion.Conditions.createLocation(new LocationPredicate(
                        NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY,
                        null, ModStructures.FROG_MAZE_KEY, null, null,
                        LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY)))
                .build(consumer, "frogvasion/amazeing");
        Advancement did_rail_jump = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.FROGVASIUM_RAIL,
                        Text.literal("Did this rail just let me jump?"),
                        Text.literal("Ride with a minecart of a Frogvasium Rail"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false)
                .criterion("rail", EnterBlockCriterion.Conditions.block(ModBlocks.FROGVASIUM_RAIL))
                .build(consumer, "frogvasion/did_rail_let_me_jump");
        Advancement frogvasium_ingot = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModItems.FROGVASIUM_INGOT,
                        Text.literal("Frogvasium Ingot"),
                        Text.literal("Obtain a Frogvasium Ingot"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                )
                .criterion("ingot", InventoryChangedCriterion.Conditions.items(ModItems.FROGVASIUM_INGOT))
                .build(consumer, "frogvasion/frogvasion_ingot");
        Advancement tame_frog = Advancement.Builder.create().parent(rootAdvancement)
                .display(ModItems.EMPTY_FROG_GHOST,
                        Text.literal("Best Frogs Forever"),
                        Text.literal("Tame a frog by using a Frog Ghost on an unsuspecting frog"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false)
                .criterion("soldier", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.SOLDIER_FROG_ENTITY).build()))
                .criterion("boss_soldier", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.BOSS_SOLDIER_FROG_ENTITY).build()))
                .criterion("explosive", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.EXPLOSIVE_FROG_ENTITY).build()))
                .criterion("ender", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ENDER_FROG_ENTITY).build()))
                .criterion("armed", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ARMED_FROG_ENTITY).build()))
                .criterion("growing", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.GROWING_FROG_ENTITY).build()))
                .criterion("grappling", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.GRAPPLING_FROG_ENTITY).build()))
                .criterion("ice", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ICE_FROG_ENTITY).build()))
                .requirements(new String[][]{{"soldier","boss_soldier","explosive","ender","armed","growing","grappling","ice"}})
                .build(consumer, "frogvasion/tame_frog");
        Advancement tame_frog_army = Advancement.Builder.create().parent(tame_frog)
                .display(ModItems.SOLDIER_FROG_GHOST,
                        Text.literal("Tame an army... of frogs?"),
                        Text.literal("Tame all types of frogs"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false)
                .criterion("soldier", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.SOLDIER_FROG_ENTITY).build()))
                .criterion("boss_soldier", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.BOSS_SOLDIER_FROG_ENTITY).build()))
                .criterion("explosive", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.EXPLOSIVE_FROG_ENTITY).build()))
                .criterion("ender", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ENDER_FROG_ENTITY).build()))
                .criterion("armed", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ARMED_FROG_ENTITY).build()))
                .criterion("growing", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.GROWING_FROG_ENTITY).build()))
                .criterion("grappling", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.GRAPPLING_FROG_ENTITY).build()))
                .criterion("ice", TameAnimalCriterion.Conditions.create(new EntityPredicate.Builder().type(ModEntities.ICE_FROG_ENTITY).build()))
                .requirements(new String[][]{{"soldier"},{"boss_soldier"},{"explosive"}, {"ender"}, {"armed"}, {"growing"}, {"grappling"},{"ice"}})
                .build(consumer, "frogvasion/tame_frog_army");
        Advancement you_have_mail = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.MAILBOX,
                        Text.literal("You have mail"),
                        Text.literal("Use an Address Card on an Ender Frog"),
                        null,
                        AdvancementFrame.GOAL,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(100))
                .criterion("mail", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create().items(ModItems.ADDRESS_CARD),
                        EntityPredicate.Extended.create(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().type(ModEntities.ENDER_FROG_ENTITY).build()).build())))
                .build(consumer, "frogvasion/you_have_mail");
        Advancement green_fire = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModItems.FROG_FIRE_CHARGE,
                        Text.literal("Green Fire???"),
                        Text.literal("Light a Frog Flame by using a Frog Fire Charge on a Frogvasium Block"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(150))
                .criterion("flame", PlacedBlockCriterion.Conditions.block(ModBlocks.FROG_FLAME))
                .build(consumer, "frogvasion/green_flame");
        Advancement arrived_in_greenwood = Advancement.Builder.create().parent(green_fire)
                .display(
                        ModBlocks.ORCHID,
                        Text.literal("You have arrived in Greenwood"),
                        Text.literal("Enter the Greenwood dimension by lighting a Greenwood Portal (throw a Frog Ghost in a Frog Flame)"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(150))
                .criterion("greenwood", ChangedDimensionCriterion.Conditions.to(ModDimensions.GREENWOOD_DIMENSION_KEY))
                .build(consumer, "frogvasion/arrived_in_greenwood");
        Advancement embed_frogvasium = Advancement.Builder.create().parent(frogvasium_ingot)
                .display(
                        ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE,
                        Text.literal("A new coating"),
                        Text.literal("Use a Frogvasium Ingot on a Polished Blackstone block"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                ).criterion("embed", ItemCriterion.Conditions.create(new LocationPredicate.Builder().block(BlockPredicate.Builder.create().blocks(Blocks.POLISHED_BLACKSTONE).build()),
                        ItemPredicate.Builder.create().items(ModItems.FROGVASIUM_INGOT)))
                .build(consumer, "frogvasion/embed_frogvasium");
    }
}
