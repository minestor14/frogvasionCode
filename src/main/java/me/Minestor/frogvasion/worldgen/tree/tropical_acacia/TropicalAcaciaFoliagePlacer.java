package me.Minestor.frogvasion.worldgen.tree.tropical_acacia;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.Minestor.frogvasion.worldgen.tree.ModTreeGeneration;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class TropicalAcaciaFoliagePlacer extends FoliagePlacer {
    public static final Codec<TropicalAcaciaFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> fillFoliagePlacerFields(instance)
            .and(Codec.intRange(0, 12).fieldOf("height").forGetter((placer) -> placer.height)).apply(instance, TropicalAcaciaFoliagePlacer::new));
    private final int height;
    public TropicalAcaciaFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModTreeGeneration.TROPICAL_ACACIA_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        int frad = treeNode.getFoliageRadius();
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter());
        generateSquare(world, placer, random, config, treeNode.getCenter().east(), frad - 1, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().west(), frad - 1, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().north(), frad - 1, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().south(), frad - 1, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().up(), Math.max(0, frad - 2), 0, treeNode.isGiantTrunk());
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().up().offset(Direction.EAST, frad - 1));
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().up().offset(Direction.NORTH, frad - 1));
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().up().offset(Direction.WEST, frad - 1));
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().up().offset(Direction.SOUTH, frad - 1));
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
