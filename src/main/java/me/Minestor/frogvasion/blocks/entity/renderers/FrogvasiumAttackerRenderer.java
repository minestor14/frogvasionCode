package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.blocks.custom.FrogvasiumAttackerBlock;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumAttackerBlockEntity;
import me.Minestor.frogvasion.blocks.entity.models.FrogvasiumAttackerModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FrogvasiumAttackerRenderer extends GeoBlockRenderer<FrogvasiumAttackerBlockEntity> {

    public FrogvasiumAttackerRenderer() {
        super(new FrogvasiumAttackerModel());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, FrogvasiumAttackerBlockEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(animatable.getCachedState().get(FrogvasiumAttackerBlock.FACING) == Direction.DOWN) {
            poseStack.translate(0,0.5,0.5);
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
        if(animatable.getCachedState().get(FrogvasiumAttackerBlock.FACING) == Direction.UP) {
            poseStack.translate(0,0.5,-0.5);
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
