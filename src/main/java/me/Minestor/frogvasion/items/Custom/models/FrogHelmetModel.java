package me.Minestor.frogvasion.items.Custom.models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.items.Custom.FrogHelmetItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FrogHelmetModel extends GeoModel<FrogHelmetItem> {
    @Override
    public Identifier getModelResource(FrogHelmetItem object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/frog_helmet.geo.json");
    }

    @Override
    public Identifier getTextureResource(FrogHelmetItem object) {
        return new Identifier(Frogvasion.MOD_ID, "textures/armor/frog_helmet.png");
    }

    @Override
    public Identifier getAnimationResource(FrogHelmetItem animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog_helmet.animation.json");
    }
}
