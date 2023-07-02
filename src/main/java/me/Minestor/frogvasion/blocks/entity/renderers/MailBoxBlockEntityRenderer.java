package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.custom.MailBoxBlock;
import me.Minestor.frogvasion.blocks.entity.MailBoxBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MailBoxBlockEntityRenderer implements BlockEntityRenderer<MailBoxBlockEntity> {
    final BlockEntityRendererFactory.Context ctx;
    public MailBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(MailBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();

        Text mail = Text.literal("You have mail!");

        if (!world.getBlockState(pos).isOf(Blocks.AIR) && world.getBlockState(pos).get(MailBoxBlock.MAIL) > 0) {

            matrices.translate(0.5F, 1.2, 0.5F);
            matrices.multiply(ctx.getEntityRenderDispatcher().getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);

            TextRenderer textRenderer = ctx.getTextRenderer();
            float h = (float)(-textRenderer.getWidth(mail) / 2);

            int lightAbove = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up());
            int color = Math.sin(world.getTimeOfDay() / 3f) > 0 ? 16776960 : 16711680;

            textRenderer.draw(mail,h,0, color, false, matrices.peek().getPositionMatrix(), vertexConsumers,
                    TextRenderer.TextLayerType.NORMAL, 0, lightAbove);
        }
        matrices.pop();
    }
}
