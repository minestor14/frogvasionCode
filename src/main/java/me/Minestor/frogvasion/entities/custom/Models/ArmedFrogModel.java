package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.ArmedFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class ArmedFrogModel extends GeoModel<ArmedFrog> {
    @Override
    public Identifier getModelResource(ArmedFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/armed_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmedFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/armed_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/armed_frog.png");
    }

    @Override
    public Identifier getAnimationResource(ArmedFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
