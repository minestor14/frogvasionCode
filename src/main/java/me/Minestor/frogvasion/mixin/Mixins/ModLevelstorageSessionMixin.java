package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.entities.custom.Renderers.GrapplingFrogRenderer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelStorage.Session.class)
public class ModLevelstorageSessionMixin {

    @Inject(method = "backupLevelDataFile(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/world/SaveProperties;)V", at = @At("HEAD"))
    public void saveWorld(DynamicRegistryManager registryTracker, SaveProperties saveProperties, CallbackInfo info) {

        GrapplingFrogRenderer.resetAngle = true;
    }
    @Inject(method = "backupLevelDataFile(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/world/SaveProperties;)V", at = @At("TAIL"))
    public void saveWorldEnd(DynamicRegistryManager registryTracker, SaveProperties saveProperties, CallbackInfo info) {
        GrapplingFrogRenderer.resetAngle = false;
    }
}
