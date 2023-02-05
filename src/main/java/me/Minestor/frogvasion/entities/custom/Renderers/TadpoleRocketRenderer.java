package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.Models.TadpoleRocketModel;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class TadpoleRocketRenderer extends GeoEntityRenderer<TadpoleRocket> {
    public TadpoleRocketRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new TadpoleRocketModel());
        this.shadowRadius = 0.1f;
    }
    @Override
    public Identifier getTextureLocation(TadpoleRocket animatable) {
        return animatable.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket.png");
    }

    @Override
    public void render(TadpoleRocket entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isInfused()) packedLight = ModFrog.INFUSED_BRIGHTNESS;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
