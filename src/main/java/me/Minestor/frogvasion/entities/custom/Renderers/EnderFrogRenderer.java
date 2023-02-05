package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.EnderFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.Models.EnderFrogModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class EnderFrogRenderer extends GeoEntityRenderer<EnderFrog> {

    public EnderFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EnderFrogModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureLocation(EnderFrog animatable) {
        return animatable.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/ender_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/ender_frog.png");
    }

    @Override
    public void render(EnderFrog entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isInfused()) packedLight = ModFrog.INFUSED_BRIGHTNESS;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
