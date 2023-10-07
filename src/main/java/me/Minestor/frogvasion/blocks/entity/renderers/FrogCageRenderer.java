package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogCageBlock;
import me.Minestor.frogvasion.blocks.entity.FrogCageBlockEntity;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.client.renderers.*;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class FrogCageRenderer implements BlockEntityRenderer<FrogCageBlockEntity> {
    final BlockEntityRendererFactory.Context ctx;
    World world;
    SoldierFrog soldier;
    ArmedFrog armed;
    BossSoldierFrog boss_soldier;
    EnderFrog ender;
    ExplosiveFrog explosive;
    GrapplingFrog grappling;
    GrowingFrog growing;
    IceFrog ice;
    TadpoleRocket rocket;
    public FrogCageRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
        initFrogs(null);
    }
    private void initFrogs(World world1) {
        if(world1 == null && MinecraftClient.getInstance().player != null){
            world = MinecraftClient.getInstance().player.getWorld();
            initFrogs();
        } else if (MinecraftClient.getInstance().player != null){
            world = world1;
            initFrogs();
        }
    }

    private void initFrogs() {
        soldier = new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, world);
        armed = new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, world);
        boss_soldier = new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world);
        ender = new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world);
        explosive = new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, world);
        grappling = new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, world);
        growing = new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world);
        ice = new IceFrog(ModEntities.ICE_FROG_ENTITY, world);
        rocket = new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, world);
    }
    @Override
    public void render(FrogCageBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);

        if (state != null && state.isOf(ModBlocks.FROG_CAGE)) {
            if(this.world == null) {
                initFrogs(world);
            }
            MinecraftClient mc = MinecraftClient.getInstance();

            int lightAbove = WorldRenderer.getLightmapCoordinates(world, pos.up());
            double y = Math.sin((world.getTime() + tickDelta) / 8d) / 10;

            matrices.translate(0.5, 0.2 + y, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((world.getTime() + tickDelta) * 4));

            if(!state.get(FrogCageBlock.LOADED)){
                matrices.scale(0.5f,0.5f,0.5f);
                int defaultedFrog = state.get(FrogCageBlock.FROG);

                if (FrogvasionGameOptions.getSillyMode()) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
                }
                EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();

                if (defaultedFrog == 1) {
                    entityRenderDispatcher.getRenderer(soldier).render(soldier, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 2) {
                    entityRenderDispatcher.getRenderer(boss_soldier).render(boss_soldier, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 3) {
                    entityRenderDispatcher.getRenderer(armed).render(armed, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 4) {
                    entityRenderDispatcher.getRenderer(ender).render(ender, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 5) {
                    entityRenderDispatcher.getRenderer(explosive).render(explosive, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 6) {
                    entityRenderDispatcher.getRenderer(grappling).render(grappling, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 7) {
                    entityRenderDispatcher.getRenderer(growing).render(growing, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 8) {
                    entityRenderDispatcher.getRenderer(rocket).render(rocket, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                } else if (defaultedFrog == 9) {
                    entityRenderDispatcher.getRenderer(ice).render(ice, 0, tickDelta, matrices, vertexConsumers, lightAbove);
                }
            } else {
                mc.getItemRenderer().renderItem(Items.SLIME_BALL.getDefaultStack(), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV,
                        matrices, vertexConsumers, world, 0);
            }

        }
        matrices.pop();
    }

}
