package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrogvasiumIngotItem extends Item {
    public FrogvasiumIngotItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if(state.isOf(Blocks.POLISHED_BLACKSTONE)) {
            if(!world.isClient) Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) context.getPlayer(), pos, context.getStack());
            world.setBlockState(pos, ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE.getDefaultState());
            world.playSoundAtBlockCenter(pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1f,1f,true);
            world.addBlockBreakParticles(pos, state);
            context.getStack().decrement(1);
        }
        return super.useOnBlock(context);
    }
}
