package me.Minestor.frogvasion.worldgen.features.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.Minestor.frogvasion.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FrogTowerFeature extends Feature<FrogTowerFeature.FrogTowerFeatureConfig> {
    public FrogTowerFeature(Codec<FrogTowerFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<FrogTowerFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        FrogTowerFeatureConfig config = context.getConfig();

        Random rand = context.getRandom();

        int height = config.height();
        int width = config.width();
        Identifier blockID = config.blockID();

        BlockState blockState = Registries.BLOCK.get(blockID).getDefaultState();
        if (blockState == null) throw new IllegalStateException(blockID + " could not be parsed to a valid block identifier!");

        BlockPos testPos = new BlockPos(origin);
        for (int y = 0; y < world.getHeight(); y++) {
            testPos = testPos.up();
            if (world.getBlockState(testPos).isFullCube(world, testPos) && world.getBlockState(testPos.up()).isOf(Blocks.AIR)
                    && world.getBlockState(testPos).getBlock() != Blocks.BEDROCK) {
                drawStructure(testPos,world.toServerWorld(),blockState,height,width,true, rand);
                return true;
            }
        }
//        the game couldn't find a place to put the feature
        return false;
    }

    public static void drawStructure(BlockPos center, ServerWorld world, BlockState state, int height, int width , boolean fillWithAir,Random rand) {
        for (int i = 0; i <= height; i++) { //height
            BlockPos pos;
            for(int j = 0; j <360; j+=3) { //degrees for circle
                if(i == 0) {
                    for(int k = 0; k <= width;k++) { //making bottom layer solid
                        pos = center.add(Math.sin(Math.toRadians(j)) * k +0.5, 0, Math.cos(Math.toRadians(j)) * k +0.5);
                        world.setBlockState(pos, state, 0x10);
                    }
                    continue;
                }
                if(i == height - 1) {
                    for(int k = 0; k <= width;k++) { //making top layer a ring
                        pos = center.add(Math.sin(Math.toRadians(j)) * k +0.5, 0, Math.cos(Math.toRadians(j)) * k +0.5);
                        world.setBlockState(pos, k< 3 ? ModBlocks.CONCENTRATED_SLIME.getDefaultState() : state, 0x10);
                    }
                    continue;
                }
                double v = Math.sin(Math.toRadians(j)) * width, u = Math.cos(Math.toRadians(j)) * width;
                if(i == height) { //fences ring at top level
                    pos = center.add(v +0.5,0, u +0.5);
                    world.setBlockState(pos,Blocks.MANGROVE_FENCE.getDefaultState(), 0x10);
                    world.setBlockState(center, ModBlocks.FROG_TOWER_CHEST.getDefaultState(), 0x10);
                    continue;
                }
                for(int k = 0; k <= width - 1;k++) { //adding froglights inside and making layer hollow if fillWithAir
                    pos = center.add(Math.sin(Math.toRadians(j)) * k +0.5, 0, Math.cos(Math.toRadians(j)) * k +0.5);
                    if(fillWithAir) {world.setBlockState(pos, rand.nextFloat() < 0.05f ? getRandomFrogLights(rand) : Blocks.AIR.getDefaultState(), 0x10);}
                    else if(rand.nextFloat() < 0.05f) {world.setBlockState(pos,getRandomFrogLights(rand), 0x10);}
                }

                pos = center.add(v +0.5,0, u +0.5);
                world.setBlockState(pos,getRandomComparableBlocks(rand),0x10);
            }
            //vertical beams of state.getBlock()
            world.setBlockState(center.add(width,0,0), state,0x10);
            world.setBlockState(center.add(-width,0,0), state,0x10);
            world.setBlockState(center.add(0,0,width), state,0x10);
            world.setBlockState(center.add(0,0,-width), state,0x10);
            center = center.up();
            if (center.getY() >= world.getTopY()) break;
        }
    }

    private static BlockState getRandomComparableBlocks(Random rand) {
        int r = rand.nextInt(100);
        if(r < 10) return Blocks.MOSS_BLOCK.getDefaultState();
        if(r < 20) return Blocks.MOSSY_COBBLESTONE.getDefaultState();
        if(r < 45) return Blocks.MUD.getDefaultState();
        if(r < 70) return Blocks.MUDDY_MANGROVE_ROOTS.getDefaultState();
        return Blocks.MUD_BRICKS.getDefaultState();
    }
    private static BlockState getRandomFrogLights(Random rand) {
        int r = rand.nextInt(100);
        if(r < 45) return Blocks.OCHRE_FROGLIGHT.getDefaultState();
        if(r < 73) return Blocks.VERDANT_FROGLIGHT.getDefaultState();
        return Blocks.PEARLESCENT_FROGLIGHT.getDefaultState();
    }

    public record FrogTowerFeatureConfig(int height, int width, Identifier blockID) implements FeatureConfig {
        public FrogTowerFeatureConfig(int height, int width, Identifier blockID) {
            this.blockID = blockID;
            this.height = height;
            this.width = width;
            if(height <= 0 || width <=2) throw new IllegalArgumentException(height <= 0 ? "Height cannot be equal or lower than 0" : "Width cannot be equal or lower than 2");
        }

        public static Codec<FrogTowerFeatureConfig> CODEC = RecordCodecBuilder.create(
                instance ->
                        instance.group(
                                        Codecs.POSITIVE_INT.fieldOf("height").forGetter(FrogTowerFeatureConfig::height),
                                        Codecs.POSITIVE_INT.fieldOf("width").forGetter(FrogTowerFeatureConfig::width),
                                        Identifier.CODEC.fieldOf("blockID").forGetter(FrogTowerFeatureConfig::blockID))
                                .apply(instance, FrogTowerFeatureConfig::new));

        public int height() {
            return height;
        }

        @Override
        public int width() {
            return width;
        }

        public Identifier blockID() {
            return blockID;
        }
    }
}
