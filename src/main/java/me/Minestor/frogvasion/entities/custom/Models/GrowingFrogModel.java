package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GrowingFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class GrowingFrogModel extends GeoModel<GrowingFrog> {

    @Override
    public Identifier getModelResource(GrowingFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/growing_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(GrowingFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/growing_frog.png");
    }

    @Override
    public Identifier getAnimationResource(GrowingFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
