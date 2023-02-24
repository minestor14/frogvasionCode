package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.IceFrog;
import me.Minestor.frogvasion.entities.custom.SoldierFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class IceFrogModel extends GeoModel<IceFrog> {
    @Override
    public Identifier getModelResource(IceFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/soldier_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/ice_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/soldier_frog.png");
    }

    @Override
    public Identifier getAnimationResource(IceFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
