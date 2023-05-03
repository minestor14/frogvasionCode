package me.Minestor.frogvasion.items.Custom;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.blocks.custom.MailBoxBlock;
import me.Minestor.frogvasion.blocks.entity.MailBoxBlockEntity;
import me.Minestor.frogvasion.entities.ModEntities;
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

public class AddressCardItem extends Item {
    public AddressCardItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Right click a Mailbox to set a location").formatted(Formatting.AQUA));
            tooltip.add(Text.literal("Right click on an ender frog to send this letter to the Mailbox").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
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

            NbtCompound nbt = new NbtCompound();
            nbt.putInt("x", pos.getX());
            nbt.putInt("y", pos.getY());
            nbt.putInt("z", pos.getZ());
            stack.setNbt(nbt);

            ServerPlayerEntity sp = (ServerPlayerEntity) context.getPlayer();
            int s = sp.getInventory().selectedSlot;
            sp.getInventory().setStack(s,stack);

            String name = sp.getActiveItem().getName().getString() == "Air" ? Text.translatable("item.frogvasion.address_card").getString() : sp.getActiveItem().getName().getString();
            sp.networkHandler.sendPacket(
                    new OverlayMessageS2CPacket(Text.literal("Linked " + name + " to §b" + pos.toShortString()))
            );
        }
        return super.useOnBlock(context);
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
                        be.addMail("§e"+user.getDisplayName().getString() + " wrote: §r" + stack.getName().getString());
                        entity.discard();
                        stack.decrement(1);
                    } else {
                        user.sendMessage(Text.literal("The mailbox of that location is full!").formatted(Formatting.RED));
                    }
                }
            } else {
                user.sendMessage(Text.literal("No valid mailbox at set location!").formatted(Formatting.RED));
            }

        } else {
            user.sendMessage(Text.literal("Destination not set!").formatted(Formatting.RED));
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public static boolean hasNbt(ItemStack stack) {
        return stack.getNbt() != null && !stack.getNbt().isEmpty();
    }

    public static BlockPos getPos(ItemStack stack) {
        if(stack.getNbt() == null) return new BlockPos(0,0,0);
        return new BlockPos(stack.getNbt().getInt("x"),stack.getNbt().getInt("y"),stack.getNbt().getInt("z"));
    }

    public ItemStack setNBTData(int x, int y, int z, String message) {
        ItemStack stack = this.getDefaultStack();
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("message", message);
        nbt.putInt("x",x);
        nbt.putInt("y",y);
        nbt.putInt("z",z);
        stack.setNbt(nbt);
        return stack;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return hasNbt(stack);
    }
}
