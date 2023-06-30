package me.Minestor.frogvasion.networking.packets;

import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class UpdateQuestPacket {
    public static PacketByteBuf createUpdate(int amount, int originalAmount, String type, String item, String block, String target, boolean completed, boolean active) {
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
    public static PacketByteBuf createUpdate(Quest quest) {
        ExtraQuestData data = quest.getData();

        return createUpdate(data.getAmount(), data.getOriginalAmount(), data.getType().toString(), data.getItemString(), data.getBlockString(), data.getTargetString(), data.isCompleted(), data.isActive());
    }
}
