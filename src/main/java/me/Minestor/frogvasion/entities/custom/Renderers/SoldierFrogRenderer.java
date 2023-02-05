package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.Models.SoldierFrogModel;
import me.Minestor.frogvasion.entities.custom.SoldierFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class SoldierFrogRenderer extends GeoEntityRenderer<SoldierFrog> {
    public SoldierFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SoldierFrogModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureLocation(SoldierFrog animatable) {
        return animatable.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog.png");
    }

    @Override
    public void render(SoldierFrog entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isInfused()) packedLight = ModFrog.INFUSED_BRIGHTNESS;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
