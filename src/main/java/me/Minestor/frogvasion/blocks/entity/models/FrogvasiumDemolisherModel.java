package me.Minestor.frogvasion.blocks.entity.models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumDemolisherBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrogvasiumDemolisherModel extends DefaultedBlockGeoModel<FrogvasiumDemolisherBlockEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier(Frogvasion.MOD_ID, "frogvasium_demolisher"));
    private final Identifier ANIMATIONS = buildFormattedAnimationPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));

    public FrogvasiumDemolisherModel() {
        super(new Identifier(Frogvasion.MOD_ID, ""));
    }


    @Override
    public Identifier getAnimationResource(FrogvasiumDemolisherBlockEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public Identifier getModelResource(FrogvasiumDemolisherBlockEntity animatable) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(FrogvasiumDemolisherBlockEntity animatable) {
        return TEXTURE;
    }

    @Override
    public RenderLayer getRenderType(FrogvasiumDemolisherBlockEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
}
