package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GrapplingFrog;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GrapplingFrogModel extends GeoModel<GrapplingFrog> {
    @Override
    public Identifier getModelResource(GrapplingFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/soldier_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(GrapplingFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/grappling_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/grappling_frog.png");
    }

    @Override
    public Identifier getAnimationResource(GrapplingFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }

}