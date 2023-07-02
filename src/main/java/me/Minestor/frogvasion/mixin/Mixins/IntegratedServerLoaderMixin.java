package me.Minestor.frogvasion.mixin.Mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {
    //"borrowed" this code from https://github.com/rdvdev2/DisableCustomWorldsAdvice/tree/1.20
    @ModifyVariable(
            method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V",
            at = @At("HEAD"),
            argsOnly = true,
            index = 4
    )
    private boolean removeAdviceOnLoad(boolean original) {
        return false;
    }

    // Set bypassWarnings = true
    @ModifyVariable(
            method = "tryLoad",
            at = @At("HEAD"),
            argsOnly = true,
            index = 4
    )
    private static boolean removeAdviceOnCreation(boolean original) {
        return true;
    }
}
