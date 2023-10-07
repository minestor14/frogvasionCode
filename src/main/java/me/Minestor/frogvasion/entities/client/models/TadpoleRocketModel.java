package me.Minestor.frogvasion.entities.client.models;

import me.Minestor.frogvasion.entities.animations.ModAnimations;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class TadpoleRocketModel extends SinglePartEntityModel<TadpoleRocket> {
    private final ModelPart tadpole;
    public TadpoleRocketModel(ModelPart root) {
        this.tadpole = root.getChild("tadpole");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData tadpole = modelPartData.addChild("tadpole", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData tail = tadpole.addChild("tail", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 1.5F));

        ModelPartData body = tadpole.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 21.0F, -3.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-1.7F, 20.4F, -2.0F, 3.4F, 1.6F, 2.1F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -23.0F, 1.5F));
        return TexturedModelData.of(modelData, 16, 16);
    }
    @Override
    public ModelPart getPart() {
        return tadpole;
    }

    @Override
    public void setAngles(TadpoleRocket entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.updateAnimation(entity.moveAnimationState, ModAnimations.TADPOLE_ROCKET_MOVE, animationProgress, 1f);
        this.updateAnimation(entity.idleAnimationState, ModAnimations.TADPOLE_ROCKET_IDLE, animationProgress, 1f);
    }
}
