package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.MailBoxBlock;
import me.Minestor.frogvasion.blocks.entity.MailBoxBlockEntity;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AddressCardItem extends Item {
    public AddressCardItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.address_card1", Text.translatable("block.frogvasion.mailbox")).formatted(Formatting.AQUA));
                tooltip.add(Text.translatable("text.item.address_card2", Text.translatable("entity.frogvasion.ender_frog"), Text.translatable("block.frogvasion.mailbox")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }

        tooltip.add(Text.literal(getPos(stack).toShortString()).formatted(Formatting.DARK_RED));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(world.getBlockState(context.getBlockPos()).isOf(ModBlocks.MAILBOX) && !world.isClient) {
            BlockPos pos = context.getBlockPos();
            ItemStack stack = context.getStack();

            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.putInt("x", pos.getX());
            nbt.putInt("y", pos.getY());
            nbt.putInt("z", pos.getZ());
            stack.setNbt(nbt);

            ServerPlayerEntity sp = (ServerPlayerEntity) context.getPlayer();
            int s = sp.getInventory().selectedSlot;
            sp.getInventory().setStack(s, stack);

            String name = Objects.equals(sp.getActiveItem().getName().getString(), "Air") ? Text.translatable("item.frogvasion.address_card").getString() : sp.getActiveItem().getName().getString();
            sp.networkHandler.sendPacket(
                    new OverlayMessageS2CPacket(Text.translatable("text.item.address_card3", name , pos.toShortString()))
            );
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity.getType() != ModEntities.ENDER_FROG_ENTITY || user.getWorld().isClient) return super.useOnEntity(stack, user, entity, hand);
        if(hasNbt(stack)) {
            BlockPos des = getPos(stack);
            World world = user.getWorld();
            BlockState state = world.getBlockState(des);

            if(state.isOf(ModBlocks.MAILBOX)) {
                if(world.getBlockEntity(des) instanceof MailBoxBlockEntity be) {
                    if(state.get(MailBoxBlock.MAIL) <10) {
                        Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger((ServerPlayerEntity) user, stack, entity);
                        be.addMail("§e"+user.getDisplayName().getString() + " wrote: §r" + stack.getName().getString());
                        entity.discard();
                        stack.decrement(1);
                    } else {
                        user.sendMessage(Text.translatable("text.item.address_card4", Text.translatable("block.frogvasion.mailbox")).formatted(Formatting.RED));
                    }
                }
            } else {
                user.sendMessage(Text.translatable("text.item.address_card5", Text.translatable("block.frogvasion.mailbox")).formatted(Formatting.RED));
            }

        } else {
            user.sendMessage(Text.translatable("text.item.address_card6").formatted(Formatting.RED));
        }
        return ActionResult.success(user.getWorld().isClient);
    }

    public static boolean hasNbt(ItemStack stack) {
        return stack.getNbt() != null && !stack.getNbt().isEmpty();
    }

    public static BlockPos getPos(ItemStack stack) {
        if(stack.getNbt() == null) return new BlockPos(0,0,0);
        return new BlockPos(stack.getNbt().getInt("x"),stack.getNbt().getInt("y"),stack.getNbt().getInt("z"));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return hasNbt(stack);
    }
}
