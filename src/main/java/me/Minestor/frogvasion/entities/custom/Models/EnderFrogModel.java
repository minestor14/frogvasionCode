package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.EnderFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class EnderFrogModel extends GeoModel<EnderFrog> {
    @Override
    public Identifier getModelResource(EnderFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/soldier_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(EnderFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/ender_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/ender_frog.png");
    }

    @Override
    public Identifier getAnimationResource(EnderFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
