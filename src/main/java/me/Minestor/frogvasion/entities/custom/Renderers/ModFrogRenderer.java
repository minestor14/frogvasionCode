package me.Minestor.frogvasion.entities.custom.Renderers;

import me.Minestor.frogvasion.entities.custom.ModFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
@Environment(EnvType.CLIENT)
public class ModFrogRenderer <E extends ModFrog> extends GeoEntityRenderer<E> {
    private final GeoModel<E> model;
    public ModFrogRenderer(EntityRendererFactory.Context renderManager, GeoModel<E> model) {
        super(renderManager, model);
        this.model = model;
    }

    @Override
    public Identifier getTextureLocation(E animatable) {
        return model.getTextureResource(animatable);
    }
}
