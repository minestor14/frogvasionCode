package me.Minestor.frogvasion.blocks.entity.renderers;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.FrogvasiumDemolisherBlock;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumActiveBlock;
import me.Minestor.frogvasion.blocks.entity.FrogvasiumDemolisherBlockEntity;
import me.Minestor.frogvasion.entities.layer.ModModelLayers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelPart;
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

public class FrogvasiumDemolisherRenderer<T extends BlockEntity & FrogvasiumActiveBlock> implements BlockEntityRenderer<T> {
    private final ModelPart attacker;
    public FrogvasiumDemolisherRenderer(BlockEntityRendererFactory.Context ctx) {
        this.attacker = ctx.getLayerModelPart(ModModelLayers.FROGVASIUM_DEMOLISHER).getChild("attacker");
        attacker.translate(new Vector3f(8f, -24f, 8f));
        attacker.rotate(new Vector3f((float) Math.PI,0,0));
    }
    
    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        final World world = entity.getWorld();
        final BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);

        if (state != null && state.isOf(ModBlocks.FROGVASIUM_DEMOLISHER)) {
            switch (state.get(FrogvasiumDemolisherBlock.FACING)) {
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

            FrogvasiumDemolisherBlockEntity be = (FrogvasiumDemolisherBlockEntity) entity;
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

        attacker.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier(Frogvasion.MOD_ID, "textures/block/frogvasium_demolisher.png"))), light, overlay);
        matrices.pop();
    }
}
