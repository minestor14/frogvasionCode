package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.util.entity.IBookProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "onResolutionChanged", at = @At("HEAD"))
    private void adjustAltarManualGui(CallbackInfo ci) {
        if(player != null) {
            ((IBookProvider) player).frogvasion$setAltarScreen(null);
            ((IBookProvider) player).frogvasion$setGuide(null);
        }
    }
}
