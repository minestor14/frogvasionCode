package me.Minestor.frogvasion.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class UpdateTrapPacket {
    public static PacketByteBuf createUpdate(BlockPos pos, int amount) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(amount);
        buf.writeBlockPos(pos);
        return PacketByteBufs.duplicate(buf);
    }
}