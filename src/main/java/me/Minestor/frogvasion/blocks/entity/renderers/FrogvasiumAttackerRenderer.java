package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogvasiumAttackerBlock;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumActiveBlock;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumAttackerBlockEntity;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class FrogvasiumAttackerRenderer<T extends BlockEntity & FrogvasiumActiveBlock> implements BlockEntityRenderer<T> {
    private final ModelPart attacker;
    public FrogvasiumAttackerRenderer(BlockEntityRendererFactory.Context ctx) {
        this.attacker = ctx.getLayerModelPart(ModModelLayers.FROGVASIUM_ATTACKER).getChild("attacker");
        attacker.translate(new Vector3f(8f, -24f, 8f));
        attacker.rotate(new Vector3f((float) Math.PI,0,0));
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData attacker = modelPartData.addChild("attacker", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = attacker.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -16.0F, 16.0F, 9.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 0).mirrored().cuboid(2.0F, -10.0F, -13.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).mirrored().cuboid(2.0F, -10.0F, -13.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).cuboid(-5.0F, -10.0F, -13.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 8.0F));

        ModelPartData body = attacker.addChild("body", ModelPartBuilder.create().uv(0, 25).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

        ModelPartData tongue = body.addChild("tongue", ModelPartBuilder.create().uv(-10, 54).cuboid(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 5.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);

        if (state != null && state.isOf(ModBlocks.FROGVASIUM_ATTACKER)) {
            switch (state.get(FrogvasiumAttackerBlock.FACING)) {
                case NORTH -> {
                    matrices.translate(1,0,1);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                }
                case EAST -> {
                    matrices.translate(0,0,1);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));

                }
                case WEST -> {
                    matrices.translate(1,0,0);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                }
                case UP -> {
                    matrices.translate(0,0,1);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
                }
                case DOWN -> {
                    matrices.translate(0,1,0);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                }
            }

            FrogvasiumAttackerBlockEntity be = (FrogvasiumAttackerBlockEntity) entity;
            ModelPart head = attacker.getChild("head");
            ModelPart tongue = attacker.getChild("body").getChild("tongue");

            if(be.canPlay(world, pos)) {
                float cd = be.cooldown + tickDelta;
                head.pitch = (float) Math.toRadians(cd <= 5 ? -3*cd : -3*(10-cd));

                tongue.zScale = (float) (1 + (cd <= 5 ? 0.36*cd : 0.36*(10-cd)));
            } else {
                head.pitch = 0;
                tongue.zScale = 1;
            }
        }

        attacker.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier(Frogvasion.MOD_ID, "textures/block/frogvasium_attacker.png"))), light, overlay);
        matrices.pop();
    }
}