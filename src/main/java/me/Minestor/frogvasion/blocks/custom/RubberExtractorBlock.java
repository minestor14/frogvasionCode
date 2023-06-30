package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubberExtractorBlock extends Block {
    public RubberExtractorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.add(0,1,0)).isOf(ModBlocks.RUBBER_LOG)) {
            world.spawnEntityAndPassengers(new ItemEntity(world,pos.getX() + 0.5,pos.getY() - 0.1, pos.getZ() + 0.5, ModItems.UNPROCESSED_RUBBER.getDefaultStack(),0,0,0));
            world.setBlockState(pos.add(0,1,0), ModBlocks.STRIPPED_RUBBER_LOG.getDefaultState());
        }
        if(world.getBlockState(pos.add(0,1,0)).isOf(ModBlocks.RUBBER_WOOD)) {
            world.spawnEntityAndPassengers(new ItemEntity(world,pos.getX() + 0.5,pos.getY() - 0.1, pos.getZ() + 0.5, ModItems.UNPROCESSED_RUBBER.getDefaultStack(),0,0,0));
            world.setBlockState(pos.add(0,1,0), ModBlocks.STRIPPED_RUBBER_WOOD.getDefaultState());
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Place this block under a Rubber Log/Wood to generate Unprocessed Rubber").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
}
