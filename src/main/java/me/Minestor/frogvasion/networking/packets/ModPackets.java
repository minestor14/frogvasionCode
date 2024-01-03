package me.Minestor.frogvasion.networking.packets;

import me.Minestor.frogvasion.entities.custom.FrogTypes;
import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.entity.GuideUnlocked;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ModPackets {
    public static PacketByteBuf questUpdate(int amount, int originalAmount, String type, String item, String block, String target, boolean completed, boolean active) {
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

    public static PacketByteBuf questUpdate(Quest quest) {
        ExtraQuestData data = quest.getData();
        return questUpdate(data.getAmount(), data.getOriginalAmount(), data.getType().toString(), data.getItemString(), data.getBlockString(), data.getTargetString(), data.isCompleted(), data.isActive());
    }

    public static PacketByteBuf trapUpdate(BlockPos pos, int amount) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(amount);
        buf.writeBlockPos(pos);
        return PacketByteBufs.duplicate(buf);
    }

    public static PacketByteBuf floradicUpdate(BlockPos pos, DefaultedList<ItemStack> inv, int progress) {
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

    public static PacketByteBuf guideUpdate(IEntityDataSaver player) {
        PacketByteBuf buf = PacketByteBufs.create();
        for (FrogTypes type : FrogTypes.values()) {
            buf.writeBoolean(GuideUnlocked.hasType(player, type));
        }
        return PacketByteBufs.duplicate(buf);
    }

    public static PacketByteBuf tttPlayerMove(int syncId, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(syncId);
        buf.writeInt(index);

        return PacketByteBufs.duplicate(buf);
    }
}
