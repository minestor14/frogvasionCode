package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GrowingFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.Models.GrowingFrogModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class GrowingFrogRenderer extends GeoEntityRenderer<GrowingFrog> {
    public GrowingFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GrowingFrogModel());
    }

    @Override
    public Identifier getTextureLocation(GrowingFrog animatable) {
        return animatable.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog.png");
    }


    @Override
    public void render(GrowingFrog entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isInfused()) packedLight = ModFrog.INFUSED_BRIGHTNESS;
        this.shadowRadius = 0.25F * (float)entity.getSize();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public GeoEntityRenderer<GrowingFrog> withScale(float scale) {
        return super.withScale(scale);
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, GrowingFrog entity, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        GeoBone bone = model.getBone("everything").get();
        bone.setScaleX((float)(0.5*entity.getSize()+0.5));
        bone.setScaleY((float)(0.5*entity.getSize()+0.5));
        bone.setScaleZ((float)(0.5*entity.getSize()+0.5));
        super.actuallyRender(poseStack, entity, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
