package me.Minestor.frogvasion;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.blocks.entity.renderers.*;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.client.models.*;
import me.Minestor.frogvasion.entities.client.renderers.*;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import me.Minestor.frogvasion.events.JoinEvent;
import me.Minestor.frogvasion.items.Custom.renderers.FrogHelmetItemRenderer;
import me.Minestor.frogvasion.items.Custom.renderers.IceSpikeEntityRenderer;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
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

import java.awt.*;

@Environment(EnvType.CLIENT)
public class FrogvasionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initBIRenderers();
        registerEntityRenderers();
        initBlockEntityRenderers();

        initClientEvents();
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.CONVERSION_PEDESTAL_SCREEN_HANDLER, ConversionPedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.QUEST_BLOCK_SCREEN_HANDLER, QuestBlockScreen::new);
    }
    private static void initClientEvents() {
        ClientPlayConnectionEvents.JOIN.register(new JoinEvent());
    }
    private static void initBlockEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_ATTACKER, FrogvasiumAttackerBlockEntityRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_DEMOLISHER, FrogvasiumAttackerBlockEntityRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FROGVASIUM_GRAPPLER, FrogvasiumAttackerBlockEntityRenderer::getTexturedModelData);

        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_ATTACKER_TYPE, FrogvasiumAttackerBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_DEMOLISHER_TYPE, FrogvasiumDemolisherBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_GRAPPLER_TYPE, FrogvasiumGrapplerBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_TRAP_TYPE, FrogTrapRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_CAGE_TYPE, FrogCageRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.MAILBOX_TYPE, MailBoxBlockEntityRenderer::new);
    }
    private static void initBIRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CONCENTRATED_SLIME, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_LAYER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROGVASIUM_RAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_CAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBBER_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBBER_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROMELIAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KAURI_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KAURI_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_FLAME, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREENWOOD_PORTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_STATUE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOLDEN_FROG_STATUE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HONEY_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SALI_TYSSE_CROP, RenderLayer.getCutout());

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
    }
}