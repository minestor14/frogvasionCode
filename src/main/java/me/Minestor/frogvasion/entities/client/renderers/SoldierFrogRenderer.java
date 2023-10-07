package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ModFrogModel;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.SoldierFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class SoldierFrogRenderer extends MobEntityRenderer<SoldierFrog, ModFrogModel<SoldierFrog>> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog.png");
    public static final Identifier INFUSED_TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog_infused.png");
    public SoldierFrogRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ModFrogModel<>(ctx.getPart(ModModelLayers.SOLDIER_FROG)), 0.4f);
    }

    @Override
    public Identifier getTexture(SoldierFrog entity) {
        return entity.isInfused() ? INFUSED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(SoldierFrog mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isInfused()) i = ModFrog.INFUSED_BRIGHTNESS;
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
