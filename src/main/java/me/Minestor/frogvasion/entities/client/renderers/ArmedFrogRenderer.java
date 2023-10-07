package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ArmedFrogModel;
import me.Minestor.frogvasion.entities.custom.ArmedFrog;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ArmedFrogRenderer extends MobEntityRenderer<ArmedFrog, ArmedFrogModel> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/armed_frog.png");
    public static final Identifier INFUSED_TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/armed_frog_infused.png");

    public ArmedFrogRenderer(EntityRendererFactory.Context context) {
        super(context, new ArmedFrogModel(context.getPart(ModModelLayers.ARMED_FROG)), 0.4f);
    }

    @Override
    public Identifier getTexture(ArmedFrog entity) {
        return entity.isInfused() ? INFUSED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(ArmedFrog mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isInfused()) i = ModFrog.INFUSED_BRIGHTNESS;
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
