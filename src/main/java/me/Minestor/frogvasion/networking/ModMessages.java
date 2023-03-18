package me.Minestor.frogvasion.networking;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.entity.renderers.FrogTrapRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModMessages {
    public static final Identifier UPDATE_TRAP = new Identifier(Frogvasion.MOD_ID,"update_trap");

    public static void registerC2SPackets() {
    }
    @Environment(EnvType.CLIENT)
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_TRAP, (client, handler, buf, responseSender) -> {
            int amount = buf.readByte();
            BlockPos pos = buf.readBlockPos();
            FrogTrapRenderer.map.put(pos.toString(),amount);
        });
    }
}
