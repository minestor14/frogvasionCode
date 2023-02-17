package me.Minestor.frogvasion.items.Custom;

import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.util.ModDamageSources;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.Vanishable;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JumpFrogStaff extends ToolItem implements Vanishable {
    public JumpFrogStaff(Settings settings) {
        super(ToolMaterials.WOOD, settings);
    }
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(user.getItemCooldownManager().isCoolingDown(stack.getItem())) return super.useOnEntity(stack, user, entity, hand);
        if(user.getWorld().isClient) return super.useOnEntity(stack, user, entity, hand);
        if(user.isSneaking()) {
            entity.addVelocity(new Vec3d(0,2.4,0));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 50,0,false, false));
            stack.damage(stack.getMaxDamage()/2 -1, user, (p) -> p.sendToolBreakStatus(hand));
            user.getItemCooldownManager().set(stack.getItem(),100);
            user.damage(ModDamageSources.MAGIC_REPAY, 3);
        } else {
            entity.addVelocity(new Vec3d(0,0.6,0));
            stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
            user.getItemCooldownManager().set(stack.getItem(),20);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(otherStack.getItem() == ModItems.FROG_HIDE || otherStack.getItem() == ModItems.SPINE) {
            stack.setDamage(Math.max(stack.getDamage() - otherStack.getCount(), 0));
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,1f,1f);
            otherStack.setCount(0);
        }
        if(otherStack.getItem() == ModItems.ENHANCED_MUTAGEN || otherStack.getItem() == ModItems.GRAPPLING_TONGUE) {
            stack.setDamage(Math.max(stack.getDamage() - (otherStack.getCount()*5), 0));
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,1f,1f);
            otherStack.setCount(0);
        }
        if(otherStack.getItem() == ModItems.FROG_HELMET_ITEM) {
            stack.setDamage(Math.max(stack.getDamage() - (otherStack.getCount()*20), 0));
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,1f,1f);
            otherStack.setCount(0);
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Use to make an entity jump, sneak for a more powerful jump").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }
        tooltip.add(Text.literal("Recharge this staff with clicking frog-drops on this item in the inventory").formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
