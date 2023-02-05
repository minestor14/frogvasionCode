package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.ExplosiveFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
@Environment(EnvType.CLIENT)
public class ExplosiveFrogModel extends GeoModel<ExplosiveFrog> {
    @Override
    public Identifier getModelResource(ExplosiveFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/explosive_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(ExplosiveFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/explosive_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/explosive_frog.png");
    }

    @Override
    public Identifier getAnimationResource(ExplosiveFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
