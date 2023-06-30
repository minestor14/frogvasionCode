package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.Models.NormalTreeFrogModel;
import me.Minestor.frogvasion.entities.custom.NormalTreeFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class NormalTreeFrogRenderer extends GeoEntityRenderer<NormalTreeFrog> {
    public NormalTreeFrogRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new NormalTreeFrogModel());
        this.shadowRadius = 0.3f;
    }
    @Override
    public Identifier getTextureLocation(NormalTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "textures/entity/tree_frog.png");
    }
}
