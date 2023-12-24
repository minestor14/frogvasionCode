package me.Minestor.frogvasion.items.custom.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.items.custom.IceSpikeItemEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class IceSpikeEntityRenderer extends ProjectileEntityRenderer<IceSpikeItemEntity> {
    public static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/entity/throwables/ice_spike.png");
    public IceSpikeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(IceSpikeItemEntity entity) {
        return TEXTURE;
    }

}
