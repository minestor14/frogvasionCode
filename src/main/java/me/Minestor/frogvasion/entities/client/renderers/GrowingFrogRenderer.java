package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ModFrogModel;
import me.Minestor.frogvasion.entities.custom.GrowingFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GrowingFrogRenderer extends MobEntityRenderer<GrowingFrog, ModFrogModel<GrowingFrog>> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog.png");
    public static final Identifier INFUSED_TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog_infused.png");
    public GrowingFrogRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ModFrogModel<>(ctx.getPart(ModModelLayers.GROWING_FROG)), 0.4f);
    }

    @Override
    public Identifier getTexture(GrowingFrog entity) {
        return entity.isInfused() ? INFUSED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(GrowingFrog mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        if(mobEntity.isInfused()) i = ModFrog.INFUSED_BRIGHTNESS;
        this.shadowRadius = 0.25F * (float)mobEntity.getSize();

        float s = (float) (0.5*mobEntity.getSize()+0.5);
        matrixStack.scale(s,s,s);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
