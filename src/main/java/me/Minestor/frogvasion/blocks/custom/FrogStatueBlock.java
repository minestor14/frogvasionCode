package me.Minestor.frogvasion.blocks.custom;

import net.minecraft.block.FallingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogStatueBlock extends FallingBlock {

    public FrogStatueBlock(Settings settings) {
        super(settings);
    }
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(4.0F, 80);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.block.frog_statue").formatted(Formatting.AQUA));

        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

}
