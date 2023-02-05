package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
@Environment(EnvType.CLIENT)
public class TadpoleRocketModel extends GeoModel<TadpoleRocket> {
    @Override
    public Identifier getModelResource(TadpoleRocket object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/tadpole_rocket.geo.json");
    }

    @Override
    public Identifier getTextureResource(TadpoleRocket object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/tadpole_rocket.png");
    }

    @Override
    public Identifier getAnimationResource(TadpoleRocket animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
