package me.Minestor.frogvasion.items.custom.frog_ghosts;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyFrogGhostItem extends Item {
    public EmptyFrogGhostItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof ModFrog frog && !(entity instanceof TadpoleRocket)) {
            stack.decrement(1);
            switch (frog.getFrogType()) {
                case ARMED -> user.giveItemStack(new ItemStack(ModItems.ARMED_FROG_GHOST));
                case GROWING -> user.giveItemStack(new ItemStack(ModItems.GROWING_FROG_GHOST));
                case BOSS_SOLDIER -> user.giveItemStack(new ItemStack(ModItems.BOSS_SOLDIER_FROG_GHOST));
                case ENDER -> user.giveItemStack(new ItemStack(ModItems.ENDER_FROG_GHOST));
                case EXPLOSIVE -> user.giveItemStack(new ItemStack(ModItems.EXPLOSIVE_FROG_GHOST));
                case GRAPPLING -> user.giveItemStack(new ItemStack(ModItems.GRAPPLING_FROG_GHOST));
                case SOLDIER -> user.giveItemStack(new ItemStack(ModItems.SOLDIER_FROG_GHOST));
                case ICE -> user.giveItemStack(new ItemStack(ModItems.ICE_FROG_GHOST));
                default -> user.giveItemStack(new ItemStack(ModItems.EMPTY_FROG_GHOST));
            }
            frog.damage(user.getWorld().getDamageSources().magic(), 3);
        }
        if(!user.getWorld().isClient){
            Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger((ServerPlayerEntity) user, stack, entity);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.empty_frog_ghost", Text.translatable("block.frogvasion.conversion_pedestal")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        if(entity.getBlockStateAtPos().isOf(ModBlocks.FROG_FLAME) && !entity.getWorld().isClient) {
            entity.getWorld().setBlockState(entity.getBlockPos(), ModBlocks.GREENWOOD_PORTAL.getDefaultState());
        }
        super.onItemEntityDestroyed(entity);
    }
}
