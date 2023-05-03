package me.Minestor.frogvasion;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.entity.FrogTrapBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.blocks.entity.renderers.*;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.Renderers.*;
import me.Minestor.frogvasion.events.JoinEvent;
import me.Minestor.frogvasion.items.Custom.IceSpikeItemEntity;
import me.Minestor.frogvasion.items.Custom.renderers.IceSpikeEntityRenderer;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.screen.ConversionPedestalScreen;
import me.Minestor.frogvasion.screen.ModScreenHandlers;
import me.Minestor.frogvasion.util.ModThrowables;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


@Environment(EnvType.CLIENT)
public class FrogvasionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initBIRenderers();
        EntityRendererRegistry.register(ModEntities.SOLDIER_FROG_ENTITY, SoldierFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOSS_SOLDIER_FROG_ENTITY, BossSoldierFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.EXPLOSIVE_FROG_ENTITY, ExplosiveFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.GROWING_FROG_ENTITY, GrowingFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMED_FROG_ENTITY, ArmedFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.TADPOLE_ROCKET_ENTITY, TadpoleRocketRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRAPPLING_FROG_ENTITY, GrapplingFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.ENDER_FROG_ENTITY, EnderFrogRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICE_FROG_ENTITY, IceFrogRenderer::new);

        EntityRendererRegistry.register(ModThrowables.ICE_SPIKE_ITEM_ENTITY_TYPE, IceSpikeEntityRenderer::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_ATTACKER_TYPE, context -> new FrogvasiumAttackerRenderer());
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_DEMOLISHER_TYPE, context -> new FrogvasiumDemolisherRenderer());
        BlockEntityRendererRegistry.register(ModBlockEntities.FROGVASIUM_GRAPPLER_TYPE, context -> new FrogvasiumGrapplerRenderer());
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_TRAP_TYPE, FrogTrapRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FROG_CAGE_TYPE, FrogCageRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.MAILBOX_TYPE, MailBoxBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.CONVERSION_PEDESTAL_SCREEN_HANDLER, ConversionPedestalScreen::new);

        initClientEvents();
        ModMessages.registerS2CPackets();
    }
    private static void initClientEvents() {
        ClientPlayConnectionEvents.JOIN.register(new JoinEvent());
    }
    private static void initBIRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CONCENTRATED_SLIME, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_LAYER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROGVASIUM_RAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROG_CAGE, RenderLayer.getCutout());
    }
}
