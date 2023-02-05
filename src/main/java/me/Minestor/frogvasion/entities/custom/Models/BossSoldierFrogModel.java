package me.Minestor.frogvasion.entities.custom.Models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.custom.BossSoldierFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class BossSoldierFrogModel extends GeoModel<BossSoldierFrog> {

    @Override
    public Identifier getModelResource(BossSoldierFrog object) {
        return new Identifier(Frogvasion.MOD_ID, "geo/soldier_frog.geo.json");
    }

    @Override
    public Identifier getTextureResource(BossSoldierFrog object) {
        return object.isInfused() ? new Identifier(Frogvasion.MOD_ID, "textures/entity/boss_soldier_frog_infused.png") : new Identifier(Frogvasion.MOD_ID, "textures/entity/boss_soldier_frog.png");
    }

    @Override
    public Identifier getAnimationResource(BossSoldierFrog animatable) {
        return new Identifier(Frogvasion.MOD_ID, "animations/frog.animation.json");
    }
}
