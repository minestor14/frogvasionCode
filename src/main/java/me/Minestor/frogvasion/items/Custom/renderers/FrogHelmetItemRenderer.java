package me.Minestor.frogvasion.items.Custom.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrogHelmetItemRenderer implements ArmorRenderer {
    private ModelPart frog_helmet;
    public FrogHelmetItemRenderer() {}
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData frog_helmet = modelPartData.addChild("frog_helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData eyeR = frog_helmet.addChild("eyeR", ModelPartBuilder.create().uv(54, 0).mirrored().cuboid(-4.0F, -2.5F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

        ModelPartData eyeL = frog_helmet.addChild("eyeL", ModelPartBuilder.create().uv(54, 0).cuboid(1.0F, -2.5F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        matrices.push();
        MinecraftClient mc = MinecraftClient.getInstance();

        if(frog_helmet == null){
            this.frog_helmet = mc.getEntityModelLoader().getModelPart(ModModelLayers.FROG_HELMET).getChild("frog_helmet");
        }

        if(slot == EquipmentSlot.HEAD) {
            float t = mc.getTickDelta();
            contextModel.getHead().rotate(matrices);

            ModelPart eyeR = frog_helmet.getChild("eyeR");
            ModelPart eyeL = frog_helmet.getChild("eyeL");

            float a = (int) (entity.getWorld().getTime() % 200) + t;
            if(a >= 0 && a <= 10) {
                eyeL.yScale = (float) (1 + (a <= 5 ? -0.1 * a : -0.1 * (10-a)));
                eyeR.yScale = (float) (1 + (a <= 5 ? -0.1 * a : -0.1 * (10-a)));
            } else {
                eyeL.yScale = 1;
                eyeR.yScale = 1;
            }

            frog_helmet.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(new Identifier(Frogvasion.MOD_ID, "textures/armor/frog_helmet.png"))), light, OverlayTexture.DEFAULT_UV);
        }
        matrices.pop();
    }
}
