package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GlidingTreeFrog;
import me.Minestor.frogvasion.entities.custom.Models.GlidingTreeFrogModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class GlidingTreeFrogRenderer extends GeoEntityRenderer<GlidingTreeFrog> {
    public GlidingTreeFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GlidingTreeFrogModel());
    }
    @Override
    public Identifier getTextureLocation(GlidingTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "textures/entity/gliding_tree_frog.png");
    }
}
