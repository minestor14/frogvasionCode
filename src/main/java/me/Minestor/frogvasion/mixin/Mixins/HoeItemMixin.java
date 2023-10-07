package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.blocks.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void useOnMud(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();

        if(world.getBlockState(blockPos).isOf(Blocks.MUD) && world.getBlockState(blockPos.up()).isOf(Blocks.AIR)) {
            world.setBlockState(blockPos, ModBlocks.MUD_FARMLAND.getDefaultState(), 11);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), ModBlocks.MUD_FARMLAND.getDefaultState()));

            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClient) {
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, (p) -> p.sendToolBreakStatus(context.getHand()));
                }
            }
        }
        cir.setReturnValue(ActionResult.success(world.isClient));
    }
}
