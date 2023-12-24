package me.Minestor.frogvasion.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogStatueBlock extends FallingBlock {
    public static final MapCodec<FallingBlock> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(createSettingsCodec()).apply(instance, FrogStatueBlock::new));

    public FrogStatueBlock(Settings settings) {
        super(settings);
    }
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(4.0F, 80);
    }
    public MapCodec<FallingBlock> getCodec() {
        return CODEC;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.block.frog_statue").formatted(Formatting.AQUA));

            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

}
