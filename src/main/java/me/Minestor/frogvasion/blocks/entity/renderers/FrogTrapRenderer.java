package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.custom.FrogTrapBlock;
import me.Minestor.frogvasion.blocks.entity.FrogTrapBlockEntity;
import me.Minestor.frogvasion.networking.packets.UpdateTrapPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class FrogTrapRenderer implements BlockEntityRenderer<FrogTrapBlockEntity> {
    final BlockEntityRendererFactory.Context ctx;
    public static final Map<String,Integer> map = new HashMap<>();

    public FrogTrapRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(FrogTrapBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        if(entity.getWorld().getBlockState(entity.getPos()) != null && !entity.getWorld().getBlockState(entity.getPos()).isOf(Blocks.AIR)) {
            boolean l = entity.getWorld().getBlockState(entity.getPos()).get(FrogTrapBlock.LOADED);

            ItemStack stack;
            if(l) {
                 stack = new ItemStack(Items.SLIME_BALL);
            } else {
                stack = new ItemStack(Items.AIR);
            }

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

            final ItemRenderer itemRenderer= MinecraftClient.getInstance().getItemRenderer();
            final TextRenderer textRenderer = ctx.getTextRenderer();

            matrices.translate(0.5, 0.4, 0);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

            matrices.translate(0, 0, 1);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(0.5, 0, -0.5);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

            matrices.translate(0, 0, 1);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

            matrices.translate(0,0.601,-0.5);
            matrices.scale(0.04f,0.04f,0.04f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            textRenderer.draw(matrices, Text.literal(String.valueOf(map.getOrDefault(entity.getPos().toString(),0))).formatted(Formatting.UNDERLINE),0,0,l ? 15322006 : 12125489);
        }
        matrices.pop();
    }

}
