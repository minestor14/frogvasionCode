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
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModAdvancements implements Consumer<Consumer<Advancement>>{
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement rootAdvancement = Advancement.Builder.create()
                .display(ModBlocks.LAVA_INFUSED_FROGLIGHT,
                        Text.translatable("text.frogvasion.advancement.title.root"),
                        Text.translatable("text.frogvasion.advancement.description.root"),
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
                .build(consumer, pathID("root"));
        Advancement aMAZEing = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        Blocks.MUD_BRICKS,
                        Text.translatable("text.frogvasion.advancement.title.amazeing"),
                        Text.translatable("text.frogvasion.advancement.description.amazeing"),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("in_maze", TickCriterion.Conditions.createLocation(new LocationPredicate(
                        NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY,
                        null, ModStructures.FROG_MAZE_KEY, null, null,
                        LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY)))
                .build(consumer, pathID("amazeing"));
        Advancement did_rail_jump = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.FROGVASIUM_RAIL,
                        Text.translatable("text.frogvasion.advancement.title.did_rail_let_me_jump"),
                        Text.translatable("text.frogvasion.advancement.description.did_rail_let_me_jump", Text.translatable("block.frogvasion.frogvasium_rail")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false)
                .criterion("rail", EnterBlockCriterion.Conditions.block(ModBlocks.FROGVASIUM_RAIL))
                .build(consumer, pathID("did_rail_let_me_jump"));
        Advancement frogvasium_ingot = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModItems.FROGVASIUM_INGOT,
                        Text.translatable("text.frogvasion.advancement.title.frogvasium_ingot"),
                        Text.translatable("text.frogvasion.advancement.description.frogvasium_ingot", Text.translatable("item.frogvasion.frogvasium_ingot")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                )
                .criterion("ingot", InventoryChangedCriterion.Conditions.items(ModItems.FROGVASIUM_INGOT))
                .build(consumer, pathID("frogvasion_ingot"));
        Advancement ghost_science = Advancement.Builder.create().parent(rootAdvancement)
                .display(ModItems.EMPTY_FROG_GHOST_FRAGMENT,
                        Text.translatable("text.frogvasion.advancement.title.ghost_science"),
                        Text.translatable("text.frogvasion.advancement.description.ghost_science", Text.translatable("item.frogvasion.empty_frog_ghost_fragment")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false)
                .criterion("empty_frog_ghost_fragment", InventoryChangedCriterion.Conditions.items(ModItems.EMPTY_FROG_GHOST_FRAGMENT))
                .build(consumer, pathID("ghost_science"));

        NbtCompound nbtFrog = new NbtCompound();
        nbtFrog.putBoolean("Infused", false);
        NbtCompound nbtFrogT = new NbtCompound();
        nbtFrogT.putBoolean("Infused", true);

        Advancement frog_photocopy = Advancement.Builder.create().parent(ghost_science)
                .display(ModItems.EMPTY_FROG_GHOST,
                        Text.translatable("text.frogvasion.advancement.title.frog_photocopy"),
                        Text.translatable("text.frogvasion.advancement.description.frog_photocopy", Text.translatable("item.frogvasion.empty_frog_ghost")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false)
                .criterion("frog_ghost", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create().items(ModItems.EMPTY_FROG_GHOST),
                        LootContextPredicate.create(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, 
                                EntityPredicate.Builder.create().nbt(new NbtPredicate(nbtFrog))).build())))
                .criterion("frog_ghost_inf", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create().items(ModItems.EMPTY_FROG_GHOST),
                        LootContextPredicate.create(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().nbt(new NbtPredicate(nbtFrogT))).build())))
                .requirements(new String[][]{{"frog_ghost", "frog_ghost_inf"}})
                .build(consumer, pathID("frog_photocopy"));
        Advancement ghost_style = Advancement.Builder.create().parent(ghost_science)
                .display(ModItems.GHOST_FRAGMENT_CHESTPLATE,
                        Text.translatable("text.frogvasion.advancement.title.ghost_style"),
                        Text.translatable("text.frogvasion.advancement.description.ghost_style"),
                        null,
                        AdvancementFrame.GOAL,
                        true, true, false)
                .criterion("helmet", InventoryChangedCriterion.Conditions.items(ModItems.GHOST_FRAGMENT_HELMET))
                .criterion("chestplate", InventoryChangedCriterion.Conditions.items(ModItems.GHOST_FRAGMENT_CHESTPLATE))
                .criterion("leggings", InventoryChangedCriterion.Conditions.items(ModItems.GHOST_FRAGMENT_LEGGINGS))
                .criterion("boots", InventoryChangedCriterion.Conditions.items(ModItems.GHOST_FRAGMENT_BOOTS))
                .requirements(new String[][]{{"helmet"}, {"chestplate"}, {"leggings"}, {"boots"}})
                .build(consumer, pathID("ghost_style"));
        Advancement tame_frog = Advancement.Builder.create().parent(ghost_science)
                .display(ModItems.SOLDIER_FROG_GHOST,
                        Text.translatable("text.frogvasion.advancement.title.tame_frog"),
                        Text.translatable("text.frogvasion.advancement.description.tame_frog"),
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
                .build(consumer, pathID("tame_frog"));
        Advancement tame_frog_army = Advancement.Builder.create().parent(tame_frog)
                .display(ModItems.BOSS_SOLDIER_FROG_GHOST,
                        Text.translatable("text.frogvasion.advancement.title.tame_frog_army"),
                        Text.translatable("text.frogvasion.advancement.description.tame_frog_army"),
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
                .build(consumer, pathID("tame_frog_army"));
        Advancement you_have_mail = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.MAILBOX,
                        Text.translatable("text.frogvasion.advancement.title.you_have_mail"),
                        Text.translatable("text.frogvasion.advancement.description.you_have_mail", Text.translatable("item.frogvasion.address_card")),
                        null,
                        AdvancementFrame.GOAL,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(100))
                .criterion("mail", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create().items(ModItems.ADDRESS_CARD),
                        EntityPredicate.asLootContextPredicate(new EntityPredicate.Builder().type(ModEntities.ENDER_FROG_ENTITY).build())))
                .build(consumer, pathID("you_have_mail"));
        Advancement green_fire = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModItems.FROG_FIRE_CHARGE,
                        Text.translatable("text.frogvasion.advancement.title.green_fire"),
                        Text.translatable("text.frogvasion.advancement.description.green_fire", Text.translatable("block.frogvasion.frog_flame"),
                                Text.translatable("block.frogvasion.frogvasium_block")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(150))
                .criterion("flame", ItemCriterion.Conditions.createPlacedBlock(ModBlocks.FROG_FLAME))
                .build(consumer, pathID("green_flame"));
        Advancement arrived_in_greenwood = Advancement.Builder.create().parent(green_fire)
                .display(
                        ModBlocks.ORCHID,
                        Text.translatable("text.frogvasion.advancement.title.arrived_in_greenwood"),
                        Text.translatable("text.frogvasion.advancement.description.arrived_in_greenwood", Text.translatable("block.frogvasion.greenwood_portal")),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true, true, false
                ).rewards(new AdvancementRewards.Builder().setExperience(150))
                .criterion("greenwood", ChangedDimensionCriterion.Conditions.to(ModDimensions.GREENWOOD_DIMENSION_KEY))
                .build(consumer, pathID("arrived_in_greenwood"));
        Advancement embed_frogvasium = Advancement.Builder.create().parent(frogvasium_ingot)
                .display(
                        ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE,
                        Text.translatable("text.frogvasion.advancement.title.embed_frogvasium"),
                        Text.translatable("text.frogvasion.advancement.description.embed_frogvasium", Text.translatable("item.frogvasion.frogvasium_ingot"), Text.translatable("block.minecraft.polished_blackstone")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false
                ).criterion("embed", ItemCriterion.Conditions.createItemUsedOnBlock(new LocationPredicate.Builder().block(BlockPredicate.Builder.create().blocks(Blocks.POLISHED_BLACKSTONE).build()),
                        ItemPredicate.Builder.create().items(ModItems.FROGVASIUM_INGOT)))
                .build(consumer, pathID("embed_frogvasium"));

        NbtCompound nbtVillagerProfession = new NbtCompound();
        NbtCompound prof = new NbtCompound();
        prof.putString("profession", "frogvasion:herpetologist");
        nbtVillagerProfession.put("VillagerData", prof);

        Advancement talk_to_specialist = Advancement.Builder.create().parent(rootAdvancement)
                .display(Items.EMERALD,
                        Text.translatable("text.frogvasion.advancement.title.talk_to_specialist"),
                        Text.translatable("text.frogvasion.advancement.description.talk_to_specialist", Text.translatable("entity.minecraft.villager.herpetologist")
                                , Text.translatable("block.frogvasion.conversion_pedestal")),
                        null,
                        AdvancementFrame.TASK,
                        true, true, false)
                .criterion("trade", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create(),
                        LootContextPredicate.create(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().type(EntityType.VILLAGER)
                                        .nbt(new NbtPredicate(nbtVillagerProfession)).build()).build())))
                .build(consumer, pathID("talk_to_specialist"));
    }
    public static String pathID(String name) {
        return "frogvasion:frogvasion/" + name;
    }
}
