package me.Minestor.frogvasion.items.Custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.util.entity.ModDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogStaffItem extends ToolItem implements Vanishable {
    public FrogStaffItem(Settings settings) {
        super(ToolMaterials.WOOD, settings);
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
        if(otherStack.getItem() == Items.WOODEN_SWORD || otherStack.getItem() == Items.STONE_SWORD
                ||otherStack.getItem() == Items.IRON_SWORD ||otherStack.getItem() == Items.GOLDEN_SWORD
                ||otherStack.getItem() == Items.DIAMOND_SWORD ||otherStack.getItem() == Items.NETHERITE_SWORD) {
            player.getInventory().setStack(slot.getIndex(), ModItems.COMBAT_FROG_STAFF.getDefaultStack());
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,1f,1f);
            otherStack.setCount(0);
        }
        if(otherStack.getItem() == Items.RABBIT_FOOT || otherStack.getItem() == Items.RABBIT_HIDE
                ||otherStack.getItem() == Items.RABBIT ||otherStack.getItem() == Items.RABBIT_STEW
                ||otherStack.getItem() == Items.COOKED_RABBIT) {
            player.getInventory().setStack(slot.getIndex(), ModItems.JUMP_FROG_STAFF.getDefaultStack());
            player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,1f,1f);
            otherStack.setCount(0);
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(user.getItemCooldownManager().isCoolingDown(stack.getItem())) return super.useOnEntity(stack, user, entity, hand);
        if(user.getWorld().isClient) return super.useOnEntity(stack, user, entity, hand);

        World world = user.getWorld();
        if(user.getOffHandStack().getItem() == Items.WOODEN_SWORD || user.getOffHandStack().getItem() == Items.STONE_SWORD
                ||user.getOffHandStack().getItem() == Items.IRON_SWORD ||user.getOffHandStack().getItem() == Items.GOLDEN_SWORD
                ||user.getOffHandStack().getItem() == Items.DIAMOND_SWORD ||user.getOffHandStack().getItem() == Items.NETHERITE_SWORD) {
            if(user.isSneaking() && entity.getType() != EntityType.PLAYER) {
                entity.damage(world.getDamageSources().playerAttack(user), stack.getMaxDamage() - stack.getDamage() -1);
                stack.setDamage(stack.getMaxDamage()-1);
                user.getItemCooldownManager().set(stack.getItem(),100);
                user.damage(world.getDamageSources().create(ModDamageSources.MAGIC_REPAY_KEY), 3);
            } else {
                entity.damage(world.getDamageSources().playerAttack(user), 1);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand)); //important
                user.getItemCooldownManager().set(stack.getItem(),20);
            }
        }

        if(user.getOffHandStack().getItem() == Items.RABBIT_FOOT || user.getOffHandStack().getItem() == Items.RABBIT_HIDE
                ||user.getOffHandStack().getItem() == Items.RABBIT ||user.getOffHandStack().getItem() == Items.RABBIT_STEW
                ||user.getOffHandStack().getItem() == Items.COOKED_RABBIT) {
            if(user.isSneaking()) {
                entity.addVelocity(new Vec3d(0,1.8,0));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 30,0,false, false));
                stack.damage(stack.getMaxDamage()/2 -1, user, (p) -> p.sendToolBreakStatus(hand));
                user.getItemCooldownManager().set(stack.getItem(),100);
                user.damage(world.getDamageSources().create(ModDamageSources.MAGIC_REPAY_KEY), 3);
            } else {
                entity.addVelocity(new Vec3d(0,0.6,0));
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                user.getItemCooldownManager().set(stack.getItem(),20);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(world.isClient) return super.useOnBlock(context);
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getPlayer().getInventory().getMainHandStack();
        if(state.isOf(Blocks.POLISHED_BLACKSTONE)) {
            world.setBlockState(pos, ModBlocks.FROGVASIUM_EMBEDDED_POLISHED_BLACKSTONE.getDefaultState());
            world.playSoundAtBlockCenter(pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1f,1f,true);
            world.addBlockBreakParticles(pos, state);
            stack.damage(1, context.getPlayer(), (p) -> p.sendToolBreakStatus(context.getHand()));
            context.getPlayer().getItemCooldownManager().set(stack.getItem(),20);
        }

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!(this instanceof CombatFrogStaff || this instanceof JumpFrogStaff)){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.literal("Use with a sword in offhand to deal damage, sneak for maximum damage").formatted(Formatting.AQUA));
                tooltip.add(Text.literal("Use with any rabbit-related item in offhand to make an entity jump,").formatted(Formatting.DARK_AQUA));
                tooltip.add(Text.literal("sneak for a more powerful jump").formatted(Formatting.DARK_AQUA));
                tooltip.add(Text.literal("Use on a Polished Blackstone block to embed it with frogvasium").formatted(Formatting.LIGHT_PURPLE));
                tooltip.add(Text.literal("You can combine the offhand-items with this to lock their spells").formatted(Formatting.DARK_PURPLE));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
            tooltip.add(Text.literal("Recharge this staff with clicking frog-drops on this item in the inventory").formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}
