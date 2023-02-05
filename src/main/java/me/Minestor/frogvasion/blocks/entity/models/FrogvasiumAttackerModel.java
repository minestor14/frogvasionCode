package me.Minestor.frogvasion.blocks.entity.models;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumAttackerBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrogvasiumAttackerModel extends DefaultedBlockGeoModel<FrogvasiumAttackerBlockEntity> {
    private final Identifier MODEL = buildFormattedModelPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));
    private final Identifier TEXTURE = buildFormattedTexturePath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));
    private final Identifier ANIMATIONS = buildFormattedAnimationPath(new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"));

    public FrogvasiumAttackerModel() {
        super(new Identifier(Frogvasion.MOD_ID, ""));
    }

    @Override
    public Identifier getAnimationResource(FrogvasiumAttackerBlockEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public Identifier getModelResource(FrogvasiumAttackerBlockEntity animatable) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(FrogvasiumAttackerBlockEntity animatable) {
        return TEXTURE;
    }

    @Override
    public RenderLayer getRenderType(FrogvasiumAttackerBlockEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }

}
