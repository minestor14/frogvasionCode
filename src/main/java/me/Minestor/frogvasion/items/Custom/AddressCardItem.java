package me.Minestor.frogvasion.items.Custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.EnderFrog;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
            tooltip.add(Text.literal("Use the /setmessage command to set a message and a destination").formatted(Formatting.AQUA));
            tooltip.add(Text.literal("Right click on an ender frog to teleport it there with their name as the message").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity.getType() != ModEntities.ENDER_FROG_ENTITY) return super.useOnEntity(stack, user, entity, hand);
        if(hasXYZNbt(stack)) {
            BlockPos des = getPos(stack).add(0.5,0,0.5);
            entity.teleport(des.getX(), des.getY(), des.getZ());
            EnderFrog frog = (EnderFrog)entity;
            frog.setAiDisabled(true);
            frog.setCustomName(Text.literal(getMessage(stack)));
            frog.setCustomNameVisible(true);
            frog.setBodyYaw(0);
            frog.setHeadYaw(0);
            frog.setPitch(0);
            stack.decrement(1);
        } else {
            user.sendMessage(Text.literal("Destination not set!").formatted(Formatting.RED));
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    public boolean hasXYZNbt(ItemStack stack) {
        return stack.getNbt() != null && !stack.getNbt().isEmpty();
    }

    @Nullable
    public String getMessage(ItemStack stack) {
        if(stack.getNbt() == null) return null;
        return stack.getNbt().getString("message");
    }
    public BlockPos getPos(ItemStack stack) {
        if(stack.getNbt() == null) return null;
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
}
