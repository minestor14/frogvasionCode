package me.Minestor.frogvasion.commands;

import com.mojang.brigadier.CommandDispatcher;
import me.Minestor.frogvasion.items.Custom.AddressCardItem;
import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class SetMessageCommand {



    private static boolean isAddressCard(ItemStack stack) {
        return stack.getItem() == ModItems.ADDRESS_CARD;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("setmessage").then(CommandManager.argument("address", BlockPosArgumentType.blockPos()).then(CommandManager.argument("message", MessageArgumentType.message()).executes(
                (context) -> {
                    Text signedMessage = MessageArgumentType.getMessage(context, "message");
                    BlockPos pos = BlockPosArgumentType.getBlockPos(context,"address");
                    ServerCommandSource serverCommandSource = context.getSource();
                    if(!serverCommandSource.isExecutedByPlayer()) return -1;

                    PlayerEntity sp = serverCommandSource.getPlayer();
                    if(!isAddressCard(sp.getInventory().getMainHandStack())) {
                        sp.sendMessage(Text.literal("You need to hold an Address Card item"));
                        return 1;
                    }
                    AddressCardItem item = (AddressCardItem) sp.getInventory().getMainHandStack().getItem();
                    sp.getInventory().insertStack(item.setNBTData(pos.getX(),pos.getY(),pos.getZ(), sp.getName().getString() + " wrote: " + signedMessage.getString()));
                    sp.clearActiveItem();
                    sp.sendMessage(Text.literal("You were given an Address Card").formatted(Formatting.GREEN));
                    return 1;
                }
        ))));
    }
}
