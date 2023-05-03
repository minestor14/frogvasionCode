package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.worldgen.biomes.ModBiomes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
@Environment(EnvType.SERVER)
@Mixin(BiomeSource.class)
public class BiomeSourceMixin {
    @Shadow
    public Set<RegistryEntry<Biome>> biomes;

    @Inject(method = "getBiomes", at = @At("HEAD"))
    private void getBiomes(CallbackInfoReturnable<Set<RegistryEntry<Biome>>> ci) {
        ModBiomes.registerBiomes();
        biomes.add(ModBiomes.RAINFOREST_ENTRY);
    }
    //new SimpleRegistry<Biome>(RegistryKey.ofRegistry(new Identifier(Frogvasion.MOD_ID,"rainforest")), Lifecycle.stable()).createEntry(ModBiomes.RAINFOREST)
}
