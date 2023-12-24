package me.Minestor.frogvasion.worldgen.tree.tropical_acacia;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.Minestor.frogvasion.worldgen.tree.ModTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TropicalAcaciaTrunkPlacer extends TrunkPlacer {
    public static final Codec<TropicalAcaciaTrunkPlacer> CODEC = RecordCodecBuilder.create(tropicalAcaciaTrunkPlacer ->
            fillTrunkPlacerFields(tropicalAcaciaTrunkPlacer).apply(tropicalAcaciaTrunkPlacer, TropicalAcaciaTrunkPlacer::new));
    public TropicalAcaciaTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeGeneration.TROPICAL_ACACIA_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);
        List<FoliagePlacer.TreeNode> list = new ArrayList<>();

        int height1 = height + random.nextBetween(firstRandomHeight, secondRandomHeight);
        int splits = random.nextBetween(2,4);
        final int rotCte = random.nextInt(3);

        for (int i = 0; i < splits; i++) {
            Direction main = getDirection(i);
            for (int j = 0; j < rotCte; j++) {
                main = main.rotateYClockwise();
            }
            BlockPos prev = startPos;
            getAndSetState(world, replacer, random, prev, config);

            for (int k = 0; k < height1; k++) {
                Direction general = random.nextFloat() < 0.4f ? Direction.UP : main;
                BlockPos current = prev.offset(general);
                getAndSetState(world, replacer, random, current, config, (state) -> state.withIfExists(PillarBlock.AXIS, general.getAxis()));

                if(k % 3 == 0 && random.nextBoolean() && k >= 7) {
                    Direction dir = Direction.Type.HORIZONTAL.random(random);

                    for (int l = 0; l < 3; l++) {
                        getAndSetState(world, replacer, random, current.offset(dir, l + 1), config, (state) -> state.withIfExists(PillarBlock.AXIS, dir.getAxis()));
                    }
                    list.add(new FoliagePlacer.TreeNode(current.up().offset(dir, 3), 1, false));

                    getAndSetState(world, replacer, random, current.up(), config);
                    current = current.up();
                }
                prev = current;
            }
            list.add(new FoliagePlacer.TreeNode(prev, 3, false));
        }
        return list;
    }
    private Direction getDirection(int a) {
       return switch (a) {
           case 0 -> Direction.EAST;
           case 1 -> Direction.SOUTH;
           case 2 -> Direction.WEST;
           default -> Direction.NORTH;
       };
    }
}
