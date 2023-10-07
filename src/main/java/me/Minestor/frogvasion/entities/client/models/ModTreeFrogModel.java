package me.Minestor.frogvasion.entities.client.models;

import me.Minestor.frogvasion.entities.animations.ModAnimations;
import me.Minestor.frogvasion.entities.custom.ModTreeFrog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ModTreeFrogModel<T extends ModTreeFrog> extends SinglePartEntityModel<T> {
    private final ModelPart tree_frog;
    private final ModelPart head;
    public ModTreeFrogModel(ModelPart root) {
        this.tree_frog = root.getChild("tree_frog");
        this.head = tree_frog.getChild("body").getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData tree_frog = modelPartData.addChild("tree_frog", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData left_arm = tree_frog.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -3.0F, -4.0F));

        ModelPartData left_arm_upper = left_arm.addChild("left_arm_upper", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData left_arm_lower = left_arm.addChild("left_arm_lower", ModelPartBuilder.create().uv(14, 18).mirrored().cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 12).cuboid(-2.0F, 1.0F, -3.0F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 2.0F));

        ModelPartData right_arm = tree_frog.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -3.0F, -4.0F));

        ModelPartData right_arm_upper = right_arm.addChild("right_arm_upper", ModelPartBuilder.create().uv(0, 19).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData right_arm_lower = right_arm.addChild("right_arm_lower", ModelPartBuilder.create().uv(14, 18).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(12, 12).cuboid(-2.0F, 1.0F, -3.0F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 2.0F));

        ModelPartData right_leg = tree_frog.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -2.0F, 0.0F));

        ModelPartData right_upper = right_leg.addChild("right_upper", ModelPartBuilder.create().uv(14, 14).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_lower = right_leg.addChild("right_lower", ModelPartBuilder.create().uv(15, 0).mirrored().cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 12).cuboid(-2.0F, 1.0F, -4.0F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 3.0F));

        ModelPartData left_leg = tree_frog.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -2.0F, 0.0F));

        ModelPartData left_upper = left_leg.addChild("left_upper", ModelPartBuilder.create().uv(14, 14).mirrored().cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_lower = left_leg.addChild("left_lower", ModelPartBuilder.create().uv(15, 0).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 12).cuboid(-2.0F, 1.0F, -4.0F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 3.0F));

        ModelPartData body = tree_frog.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -6.0F, 4.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 2.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).mirrored().cuboid(-1.9F, -2.0F, -1.2F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).cuboid(0.9F, -2.0F, -1.2F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, -5.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        tree_frog.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return tree_frog;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(entity, headYaw, headPitch, animationProgress);

        this.updateAnimation(entity.moveAnimationState, ModAnimations.TREE_FROG_WALK, animationProgress, 1f);
        this.updateAnimation(entity.idleAnimationState, ModAnimations.TREE_FROG_IDLE, animationProgress, 1f);
        this.updateAnimation(entity.jumpAnimationState, ModAnimations.TREE_FROG_JUMP, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, ModAnimations.TREE_FROG_ATTACK, animationProgress, 1f);
    }
    private void setHeadAngles(T entity, float headYaw, float headPitch, float animationProgress) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}
