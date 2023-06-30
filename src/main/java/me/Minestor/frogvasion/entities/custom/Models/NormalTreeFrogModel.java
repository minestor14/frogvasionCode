package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.NormalTreeFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
@Environment(EnvType.CLIENT)
public class NormalTreeFrogModel extends GeoModel<NormalTreeFrog> {
    @Override
    public Identifier getModelResource(NormalTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "geo/tree_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(NormalTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "textures/entity/tree_frog.png");
    }

    @Override
    public Identifier getAnimationResource(NormalTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/tree_frog.animation.json");
    }
}
