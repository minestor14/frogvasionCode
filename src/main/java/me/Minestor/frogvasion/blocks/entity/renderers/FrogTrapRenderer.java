package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.custom.FrogTrapBlock;
import me.Minestor.frogvasion.blocks.entity.FrogTrapBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class FrogTrapRenderer implements BlockEntityRenderer<FrogTrapBlockEntity> {
    public FrogTrapRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(FrogTrapBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        if(entity.getWorld().getBlockState(entity.getPos()) != null && !entity.getWorld().getBlockState(entity.getPos()).isOf(Blocks.AIR) && entity.getWorld().getBlockState(entity.getPos()).get(FrogTrapBlock.LOADED)) {
            ItemStack stack = entity.getStack(0);

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

            matrices.translate(0.5, 0.4, 0);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.translate(0, 0, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(0.5, 0, -0.5);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.translate(0, 0, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, lightAbove, overlay, matrices, vertexConsumers, 0);
        }
        matrices.pop();

    }
}
