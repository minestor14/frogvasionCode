package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.ExplosiveFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.Models.ExplosiveFrogModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class ExplosiveFrogRenderer extends GeoEntityRenderer<ExplosiveFrog> {
    public ExplosiveFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ExplosiveFrogModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public Identifier getTextureLocation(ExplosiveFrog animatable) {
        return animatable.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/explosive_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/explosive_frog.png");
    }

    @Override
    public void render(ExplosiveFrog entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isInfused()) packedLight = ModFrog.INFUSED_BRIGHTNESS;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
