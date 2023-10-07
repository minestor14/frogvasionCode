package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.TadpoleRocketModel;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TadpoleRocketRenderer extends MobEntityRenderer<TadpoleRocket, TadpoleRocketModel> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket.png");
    public static final Identifier INFUSED_TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket_infused.png");
    public TadpoleRocketRenderer(EntityRendererFactory.Context context) {
        super(context, new TadpoleRocketModel(context.getPart(ModModelLayers.TADPOLE_ROCKET)), 0.2f);
    }

    @Override
    public Identifier getTexture(TadpoleRocket entity) {
        return entity.isInfused() ? INFUSED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(TadpoleRocket mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isInfused()) i = ModFrog.INFUSED_BRIGHTNESS;
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
