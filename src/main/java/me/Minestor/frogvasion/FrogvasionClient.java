package me.Minestor.frogvasion;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.blocks.entity.renderers.*;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.client.models.*;
import me.Minestor.frogvasion.entities.client.renderers.*;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import me.Minestor.frogvasion.events.JoinEvent;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.items.custom.renderers.FrogHelmetItemRenderer;
import me.Minestor.frogvasion.items.custom.renderers.IceSpikeEntityRenderer;
import me.Minestor.frogvasion.networking.ClientReceiver;
import me.Minestor.frogvasion.screen.ConversionPedestalScreen;
import me.Minestor.frogvasion.screen.ModScreenHandlers;
import me.Minestor.frogvasion.screen.QuestBlockScreen;
import me.Minestor.frogvasion.util.items.ModThrowables;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class FrogvasionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initBIRenderers();
        registerEntityRenderers();
        initBlockEntityRenderers();

        initClientEvents();
        ClientReceiver.init();

        HandledScreens.register(ModScreenHandlers.CONVERSION_PEDESTAL_SCREEN_HANDLER, ConversionPedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.QUEST_BLOCK_SCREEN_HANDLER, QuestBlockScreen::new);
    }
    private static void initClientEvents() {
        ClientPlayConnectionEvents.JOIN.register(new JoinEvent());
    }
    private static void initBlockEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_ATTACKER, FrogvasiumAttackerRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_DEMOLISHER, FrogvasiumAttackerRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_GRAPPLER, FrogvasiumAttackerRenderer::getTexturedModelData);

        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_ATTACKER_TYPE, FrogvasiumAttackerRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_DEMOLISHER_TYPE, FrogvasiumDemolisherRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_GRAPPLER_TYPE, FrogvasiumGrapplerRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_TRAP_TYPE, FrogTrapRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_CAGE_TYPE, FrogCageRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.MAILBOX_TYPE, MailBoxRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FLORADIC_ALTAR_TYPE, FloradicAltarRenderer::new);
    }
    private static void initBIRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CONCENTRATED_SLIME, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_LAYER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROGVASIUM_RAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_CAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBBER_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROMELIAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KAURI_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.KAURI_SAPLING, ModBlocks.RUBBER_SAPLING, ModBlocks.TROPICAL_ACACIA_SAPLING);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_FLAME, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREENWOOD_PORTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.FROG_STATUE, ModBlocks.GOLDEN_FROG_STATUE);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HONEY_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SALI_TYSSE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.PURPLE_ORCHID, ModBlocks.DARK_PURPLE_ORCHID, ModBlocks.DARK_RED_ORCHID,
                ModBlocks.WHITE_ORCHID, ModBlocks.BLACK_ORCHID);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.POTTED_PURPLE_ORCHID, ModBlocks.POTTED_DARK_PURPLE_ORCHID, ModBlocks.POTTED_DARK_RED_ORCHID,
                ModBlocks.POTTED_WHITE_ORCHID, ModBlocks.POTTED_BLACK_ORCHID);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                    if(FrogvasionGameOptions.getSillyMode() && world != null && pos != null) {
                        return new Color((float) (Math.sin(pos.getX() / 10f) * 0.4 + 0.6), (float) (Math.sin(pos.getY() / 10f) * 0.4 + 0.6), (float) (Math.sin(pos.getZ() / 10f) * 0.4 + 0.6)).getRGB();
                    }
                    return 16777215;
                }
        , ModBlocks.CHROMA_CLUMP);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos !=null ? BiomeColors.getFoliageColor(world, pos)
                : FoliageColors.getDefaultColor(), ModBlocks.KAURI_LEAVES, ModBlocks.RUBBER_LEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), ModBlocks.KAURI_LEAVES, ModBlocks.RUBBER_LEAVES);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROG_HELMET, FrogHelmetItemRenderer::getTexturedModelData);
        ArmorRenderer.register(new FrogHelmetItemRenderer(), ModItems.FROG_HELMET_ITEM);
    }
    private static void registerEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.NORMAL_TREE_FROG, ModTreeFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GLIDING_TREE_FROG, ModTreeFrogModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.NORMAL_TREE_FROG_ENTITY, NormalTreeFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.GLIDING_TREE_FROG_ENTITY, GlidingTreeFrogRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SOLDIER_FROG, ModFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BOSS_SOLDIER_FROG, ModFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ENDER_FROG, ModFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GROWING_FROG, ModFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GRAPPLING_FROG, ModFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.EXPLOSIVE_FROG, ExplosiveFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ARMED_FROG, ArmedFrogModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TADPOLE_ROCKET, TadpoleRocketModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ICE_FROG, ModFrogModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SOLDIER_FROG_ENTITY, SoldierFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOSS_SOLDIER_FROG_ENTITY, BossSoldierFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.ENDER_FROG_ENTITY, EnderFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.GROWING_FROG_ENTITY, GrowingFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRAPPLING_FROG_ENTITY, GrapplingFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.EXPLOSIVE_FROG_ENTITY, ExplosiveFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMED_FROG_ENTITY, ArmedFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.TADPOLE_ROCKET_ENTITY, TadpoleRocketRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICE_FROG_ENTITY, IceFrogRenderer::new);

        EntityRendererRegistry.register(ModThrowables.ICE_SPIKE_ITEM_ENTITY_TYPE, IceSpikeEntityRenderer::new);
        EntityRendererRegistry.register(ModThrowables.ORCHID_GRENADE_ITEM_ENTITY_TYPE, FlyingItemEntityRenderer::new);
    }
}