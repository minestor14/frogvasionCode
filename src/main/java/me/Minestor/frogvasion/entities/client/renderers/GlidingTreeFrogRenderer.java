package me.Minestor.frogvasion.entities.client.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.client.models.ModTreeFrogModel;
import me.Minestor.frogvasion.entities.custom.GlidingTreeFrog;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class GlidingTreeFrogRenderer extends MobEntityRenderer<GlidingTreeFrog, ModTreeFrogModel<GlidingTreeFrog>> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/gliding_tree_frog.png");
    public GlidingTreeFrogRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ModTreeFrogModel<>(ctx.getPart(ModModelLayers.GLIDING_TREE_FROG)), 0.3f);
    }

    @Override
    public Identifier getTexture(GlidingTreeFrog entity) {
        return TEXTURE;
    }
}
