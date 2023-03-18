package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.SoldierFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class SoldierFrogModel extends GeoModel<SoldierFrog> {

    @Override
    public Identifier getModelResource(SoldierFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/soldier_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(SoldierFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog.png");
    }

    @Override
    public Identifier getAnimationResource(SoldierFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
