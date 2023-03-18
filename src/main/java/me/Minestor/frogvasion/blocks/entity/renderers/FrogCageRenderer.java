package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.custom.FrogCageBlock;
import me.Minestor.frogvasion.blocks.entity.FrogCageBlockEntity;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.entities.custom.Models.*;
import me.Minestor.frogvasion.entities.custom.Renderers.ModFrogRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class FrogCageRenderer implements BlockEntityRenderer<FrogCageBlockEntity> {
    final BlockEntityRendererFactory.Context ctx;
    public FrogCageRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(FrogCageBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();
        if (world.getBlockState(pos) != null && !world.getBlockState(pos).isOf(Blocks.AIR) && !world.getBlockState(pos).get(FrogCageBlock.LOADED)) {

            final EntityRendererFactory.Context cont = new EntityRendererFactory.Context(ctx.getEntityRenderDispatcher(), ctx.getItemRenderer(),
                    ctx.getRenderManager(), new HeldItemRenderer(MinecraftClient.getInstance(), ctx.getEntityRenderDispatcher(), ctx.getItemRenderer())
                    , MinecraftClient.getInstance().getResourceManager(), ctx.getLayerRenderDispatcher(), ctx.getTextRenderer());

            int lightAbove = WorldRenderer.getLightmapCoordinates(world, pos.up());
            double y = Math.sin(world.getTime() / 8d) / 10;
            matrices.translate(0.5, 0.2 + y, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((world.getTime() + tickDelta) * 4));
            matrices.scale(0.5f,0.5f,0.5f);

            int defaultedFrog = world.getBlockState(pos).get(FrogCageBlock.FROG);

            if(defaultedFrog == 1) {
                new ModFrogRenderer<>(cont, new SoldierFrogModel()).render(new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if(defaultedFrog == 2) {
                new ModFrogRenderer<>(cont, new BossSoldierFrogModel()).render(new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 3) {
                new ModFrogRenderer<>(cont, new ArmedFrogModel()).render(new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 4) {
                new ModFrogRenderer<>(cont, new EnderFrogModel()).render(new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 5) {
                new ModFrogRenderer<>(cont, new ExplosiveFrogModel()).render(new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 6) {
                new ModFrogRenderer<>(cont, new GrapplingFrogModel()).render(new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 7) {
                new ModFrogRenderer<>(cont, new GrowingFrogModel()).render(new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 8) {
                new ModFrogRenderer<>(cont, new TadpoleRocketModel()).render(new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            } else if (defaultedFrog == 9) {
                new ModFrogRenderer<>(cont, new IceFrogModel()).render(new IceFrog(ModEntities.ICE_FROG_ENTITY, world), 0, tickDelta, matrices, vertexConsumers, lightAbove);
            }

        }
        matrices.pop();
    }

}
