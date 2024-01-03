package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuideToFrogsItem extends Item {
    public GuideToFrogsItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(!world.isClient) {
            ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessages.UPDATE_GUIDE, ModPackets.guideUpdate((IEntityDataSaver) user));
            ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessages.GUIDE_TO_FROGS_S2C, PacketByteBufs.create());
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.guide_to_frogs").formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
