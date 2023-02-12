package me.Minestor.frogvasion.blocks.entity.models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumGrapplerBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrogvasiumGrapplerModel extends DefaultedBlockGeoModel<FrogvasiumGrapplerBlockEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier(Frogvasion.MOD_ID, "frogvasium_grappler"));
    private final Identifier ANIMATIONS = buildFormattedAnimationPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));

    public FrogvasiumGrapplerModel() {
        super(new Identifier(Frogvasion.MOD_ID, ""));
    }

    @Override
    public Identifier getAnimationResource(FrogvasiumGrapplerBlockEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public Identifier getModelResource(FrogvasiumGrapplerBlockEntity animatable) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(FrogvasiumGrapplerBlockEntity animatable) {
        return TEXTURE;
    }
}
