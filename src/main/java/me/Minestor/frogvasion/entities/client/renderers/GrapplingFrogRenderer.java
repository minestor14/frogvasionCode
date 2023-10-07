package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ModFrogModel;
import me.Minestor.frogvasion.entities.custom.GrapplingFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GrapplingFrogRenderer extends MobEntityRenderer<GrapplingFrog, ModFrogModel<GrapplingFrog>> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/grappling_frog.png");
    public static final Identifier INFUSED_TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/grappling_frog_infused.png");
    public GrapplingFrogRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ModFrogModel<>(ctx.getPart(ModModelLayers.GRAPPLING_FROG)), 0.4f);
    }

    @Override
    public Identifier getTexture(GrapplingFrog entity) {
        return entity.isInfused() ? INFUSED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(GrapplingFrog mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isInfused()) i = ModFrog.INFUSED_BRIGHTNESS;

        ModelPart tongue = this.getModel().getPart().getChild("tongue");
        tongue.setAngles(mobEntity.getAngle(), tongue.yaw, 0);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
