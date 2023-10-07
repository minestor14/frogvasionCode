package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ModTreeFrogModel;
import me.Minestor.frogvasion.entities.custom.NormalTreeFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class NormalTreeFrogRenderer extends MobEntityRenderer<NormalTreeFrog, ModTreeFrogModel<NormalTreeFrog>> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/tree_frog.png");
    public NormalTreeFrogRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ModTreeFrogModel<>(ctx.getPart(ModModelLayers.NORMAL_TREE_FROG)), 0.3f);
    }

    @Override
    public Identifier getTexture(NormalTreeFrog entity) {
        return TEXTURE;
    }
}
