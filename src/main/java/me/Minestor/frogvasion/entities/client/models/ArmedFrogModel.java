package me.Minestor.frogvasion.entities.client.models;

import me.Minestor.frogvasion.entities.animations.ModAnimations;
import me.Minestor.frogvasion.entities.custom.ArmedFrog;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

import static me.Minestor.frogvasion.entities.client.models.ModFrogModel.getAttackAnimation;

public class ArmedFrogModel extends SinglePartEntityModel<ArmedFrog> {
    private final ModelPart frog;
    private final ModelPart eyes;
    public ArmedFrogModel(ModelPart root) {
        this.frog = root.getChild("frog");
        this.eyes = frog.getChild("eyes");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData frog = modelPartData.addChild("frog", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_leg = frog.addChild("right_leg", ModelPartBuilder.create().uv(35, 35).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(-6.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -3.0F, 4.0F));

        ModelPartData left_leg = frog.addChild("left_leg", ModelPartBuilder.create().uv(0, 39).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(16, 24).cuboid(-2.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -3.0F, 4.0F));

        ModelPartData right_arm = frog.addChild("right_arm", ModelPartBuilder.create().uv(26, 40).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(29, 0).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -3.0F, -2.5F));

        ModelPartData left_arm = frog.addChild("left_arm", ModelPartBuilder.create().uv(36, 42).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(29, 8).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -3.0F, -2.5F));

        ModelPartData croaking_body = frog.addChild("croaking_body", ModelPartBuilder.create().uv(15, 35).cuboid(-3.5F, -0.1F, -2.9F, 7.0F, 2.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, -3.0F, -1.0F));

        ModelPartData body = frog.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -2.0F, -8.0F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F))
                .uv(14, 0).cuboid(-3.5F, -1.0F, -8.0F, 7.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 4.0F));

        ModelPartData tongue = frog.addChild("tongue", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -7.1F, 4.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.1F, 5.0F));

        ModelPartData eyes = frog.addChild("eyes", ModelPartBuilder.create().uv(14, 40).cuboid(-3.0F, -4.0F, -8.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(37, 16).cuboid(1.0F, -4.0F, -8.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -4.0F, 5.0F));

        ModelPartData head = frog.addChild("head", ModelPartBuilder.create().uv(14, 12).cuboid(-3.5F, -1.0F, -7.0F, 7.0F, 0.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 3.0F));

        ModelPartData gun = head.addChild("gun", ModelPartBuilder.create().uv(0, 46).cuboid(-1.5F, -1.5F, -9.0F, 3.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -1.5F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public ModelPart getPart() {
        return frog;
    }

    @Override
    public void setAngles(ArmedFrog entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if(FrogvasionGameOptions.getSillyMode()){
            this.setHeadAngles(entity, headYaw, headPitch, animationProgress);
        }

        this.updateAnimation(entity.moveAnimationState, ModAnimations.FROG_WALK, animationProgress, 1f);
        this.updateAnimation(entity.idleAnimationState, ModAnimations.FROG_IDLE, animationProgress, 1f);
        this.updateAnimation(entity.jumpAnimationState, ModAnimations.FROG_JUMP, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, getAttackAnimation(entity.getFrogType()), animationProgress, 1f);
        this.updateAnimation(entity.swimAnimationState, ModAnimations.FROG_SWIM, animationProgress, 1f);
        this.updateAnimation(entity.croakAnimationState, ModAnimations.FROG_CROAK, animationProgress, 1f);
    }
    private void setHeadAngles(ArmedFrog entity, float headYaw, float headPitch, float animationProgress) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.eyes.yaw = headYaw * 0.017453292F;
        this.eyes.pitch = headPitch * 0.017453292F;
    }
}
