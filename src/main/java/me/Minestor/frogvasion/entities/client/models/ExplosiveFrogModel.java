package me.Minestor.frogvasion.entities.client.models;

import me.Minestor.frogvasion.entities.animations.ModAnimations;
import me.Minestor.frogvasion.entities.custom.ExplosiveFrog;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

import static me.Minestor.frogvasion.entities.client.models.ModFrogModel.getAttackAnimation;

public class ExplosiveFrogModel extends SinglePartEntityModel<ExplosiveFrog> {
    private final ModelPart frog;
    private final ModelPart eyes;
    public ExplosiveFrogModel(ModelPart root) {
        this.frog = root.getChild("frog");
        this.eyes = frog.getChild("head").getChild("eyes");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData frog = modelPartData.addChild("frog", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_leg = frog.addChild("right_leg", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(18, 32).cuboid(-6.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -3.0F, 4.0F));

        ModelPartData left_leg = frog.addChild("left_leg", ModelPartBuilder.create().uv(14, 25).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(2, 32).cuboid(-2.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -3.0F, 4.0F));

        ModelPartData right_arm = frog.addChild("right_arm", ModelPartBuilder.create().uv(0, 38).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(2, 40).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -3.0F, -2.5F));

        ModelPartData left_arm = frog.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(18, 40).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -3.0F, -2.5F));

        ModelPartData croaking_body = frog.addChild("croaking_body", ModelPartBuilder.create().uv(26, 5).cuboid(-3.5F, -0.1F, -2.9F, 7.0F, 2.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, -3.0F, -1.0F));

        ModelPartData head = frog.addChild("head", ModelPartBuilder.create().uv(23, 13).cuboid(-3.5F, -1.0F, -7.0F, 7.0F, 0.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 13).cuboid(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 3.0F));

        ModelPartData eyes = head.addChild("eyes", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -8.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 5).cuboid(1.0F, -4.0F, -8.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 0.0F, 2.0F));

        ModelPartData fuse = head.addChild("fuse", ModelPartBuilder.create().uv(44, 45).cuboid(-1.0F, -8.0F, 2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(44, 45).cuboid(-1.0F, -9.0F, 3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -3.0F));

        ModelPartData body = frog.addChild("body", ModelPartBuilder.create().uv(3, 1).cuboid(-3.5F, -2.0F, -8.0F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F))
                .uv(23, 22).cuboid(-3.5F, -1.0F, -8.0F, 7.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 4.0F));

        ModelPartData tongue = frog.addChild("tongue", ModelPartBuilder.create().uv(17, 13).cuboid(-2.0F, 0.0F, -7.1F, 4.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.1F, 5.0F));
        return TexturedModelData.of(modelData, 48, 48);
    }
    @Override
    public ModelPart getPart() {
        return frog;
    }

    @Override
    public void setAngles(ExplosiveFrog entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
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

    private void setHeadAngles(ExplosiveFrog entity, float headYaw, float headPitch, float animationProgress) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.eyes.yaw = headYaw * 0.017453292F;
        this.eyes.pitch = headPitch * 0.017453292F;
    }
}
