package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FloradicAltarBlock;
import me.Minestor.frogvasion.blocks.entity.FloradicAltarBlockEntity;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class FloradicAltarRenderer implements BlockEntityRenderer<FloradicAltarBlockEntity> {
    public FloradicAltarRenderer(BlockEntityRendererFactory.Context ctx) {}
    @Override
    public void render(FloradicAltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);

        if(world.getBlockState(pos).isOf(ModBlocks.FLORADIC_ALTAR)) {
            if(state.get(FloradicAltarBlock.CRAFTING) && entity.isEmpty() && tickDelta == 0) {
                ClientPlayNetworking.send(ModMessages.FLORADIC_C2S, ModPackets.createFloradicUpdate(pos, entity.getItems(), entity.getProgress()));
            }

            matrices.translate(0.5,0.6 + Math.sin((world.getTime() + tickDelta) / 8d) / 10,0.5);
            float offset;

            if(state.get(FloradicAltarBlock.CRAFTING)) {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) ((entity.getProgress() + tickDelta) * 3.6)));
                offset = (100 - (entity.getProgress() + tickDelta)) / 100;
            } else offset = 1;

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(0,0,0.3125 * offset);
            itemRenderer.renderItem(entity.getStack(0), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.translate(0,0,-0.6250 * offset);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            itemRenderer.renderItem(entity.getStack(2), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.translate(0,0,0.3125 * offset);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));

            matrices.translate(0.6250 * offset,0,0.3125 * offset);
            itemRenderer.renderItem(entity.getStack(1), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.translate(0,0,-0.6250 * offset);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            itemRenderer.renderItem(entity.getStack(3), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.translate(0,0,0.3125 * offset);
        }
        matrices.pop();
    }
}
