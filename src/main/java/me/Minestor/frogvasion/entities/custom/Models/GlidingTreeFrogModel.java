package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.GlidingTreeFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
@Environment(EnvType.CLIENT)
public class GlidingTreeFrogModel extends GeoModel<GlidingTreeFrog> {
    @Override
    public Identifier getModelResource(GlidingTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "geo/tree_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(GlidingTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "textures/entity/gliding_tree_frog.png");
    }

    @Override
    public Identifier getAnimationResource(GlidingTreeFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/tree_frog.animation.json");
    }
}
