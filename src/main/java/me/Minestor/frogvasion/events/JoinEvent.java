package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.entities.custom.Renderers.GrapplingFrogRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
@Environment(EnvType.CLIENT)
public class JoinEvent implements ClientPlayConnectionEvents.Join{
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        GrapplingFrogRenderer.resetAngle = false;
    }
}
