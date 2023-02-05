package me.Minestor.frogvasion.items.Custom.renderers;

import me.Minestor.frogvasion.items.Custom.FrogHelmetItem;
import me.Minestor.frogvasion.items.Custom.models.FrogHelmetModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FrogHelmetRenderer extends GeoArmorRenderer<FrogHelmetItem> {
    public FrogHelmetRenderer() {
        super(new FrogHelmetModel());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, FrogHelmetItem animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head = model.getBone("frog_helmet").get();
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
