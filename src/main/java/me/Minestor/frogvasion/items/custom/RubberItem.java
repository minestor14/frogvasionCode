package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.effects.potion.ModPotions;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubberItem extends Item {
    public RubberItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.rubber1", Text.translatable("entity.frogvasion.normal_tree_frog"),
                        Text.translatable("item.frogvasion.rubber")).formatted(Formatting.AQUA));
                tooltip.add(Text.translatable("text.item.rubber2", Text.translatable("block.minecraft.anvil"),
                        Text.translatable("item.minecraft.elytra")).formatted(Formatting.AQUA));
                tooltip.add(Text.translatable("text.item.rubber3", Text.translatable("block.frogvasion.greenwood_portal")));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if(entity.getType() == ModEntities.NORMAL_TREE_FROG_ENTITY && stack.getCount() == 64 && !player.getWorld().isClient) {
            stack.setCount(0);
            player.playSound(SoundEvents.ENTITY_FROG_EAT, 0.7f, 1f);

            ItemStack stackToDrop;
            if(player.getWorld().getRandom().nextBetween(0,3) == 2) {
                stackToDrop = ModItems.JUMPY_TOTEM.getDefaultStack();
            } else {
                stackToDrop = PotionUtil.setPotion(new ItemStack(Items.POTION), ModPotions.FROG_CAMOUFLAGE_POTION);
            }
            entity.dropStack(stackToDrop);
        }
        return super.useOnEntity(stack, player, entity, hand);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        if(entity.getBlockStateAtPos().isOf(ModBlocks.FROG_FLAME)) {
            if(entity.getBlockPos().getY() >= 2 && entity.getBlockPos().getY() <=255){
                entity.getWorld().setBlockState(entity.getBlockPos(), ModBlocks.GREENWOOD_PORTAL.getDefaultState());
            } else {
                entity.getWorld().setBlockState(entity.getBlockPos(), Blocks.AIR.getDefaultState());
                if(entity.getOwner() != null) entity.getOwner().sendMessage(Text.translatable("text.block.warning_greenwood_portal").formatted(Formatting.RED));
            }
        }
        super.onItemEntityDestroyed(entity);
    }
}
