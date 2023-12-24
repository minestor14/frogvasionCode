package me.Minestor.frogvasion.networking.packets;

import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ModPackets {
    public static PacketByteBuf createQuestUpdate(int amount, int originalAmount, String type, String item, String block, String target, boolean completed, boolean active) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeByte(amount);
        buf.writeByte(originalAmount);
        buf.writeString(type);
        buf.writeString(item);
        buf.writeString(block);
        buf.writeString(target);
        buf.writeBoolean(completed);
        buf.writeBoolean(active);

        return PacketByteBufs.duplicate(buf);
    }
    public static PacketByteBuf createQuestUpdate(Quest quest) {
        ExtraQuestData data = quest.getData();

        return createQuestUpdate(data.getAmount(), data.getOriginalAmount(), data.getType().toString(), data.getItemString(), data.getBlockString(), data.getTargetString(), data.isCompleted(), data.isActive());
    }
    public static PacketByteBuf createTrapUpdate(BlockPos pos, int amount) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(amount);
        buf.writeBlockPos(pos);
        return PacketByteBufs.duplicate(buf);
    }
    public static PacketByteBuf createFloradicUpdate(BlockPos pos, DefaultedList<ItemStack> inv, int progress) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeBlockPos(pos);
        buf.writeInt(progress);
        buf.writeInt(inv.size());
        for (ItemStack itemStack : inv) {
            buf.writeItemStack(itemStack);
        }


        return PacketByteBufs.duplicate(buf);
    }
    public static PacketByteBuf grenadeExplosion(double x, double y, double z, ItemStack stack) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeItemStack(stack);

        return PacketByteBufs.duplicate(buf);
    }
}
