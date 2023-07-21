package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(PositionedSoundInstance.class)
public class PositionedSoundInstanceMixin extends AbstractSoundInstance implements SoundInstance{
    protected PositionedSoundInstanceMixin(SoundEvent sound, SoundCategory category, Random random) {
        super(sound, category, random);
    }

    @Inject(method = "<init>(Lnet/minecraft/util/Identifier;Lnet/minecraft/sound/SoundCategory;FFLnet/minecraft/util/math/random/Random;ZILnet/minecraft/client/sound/SoundInstance$AttenuationType;DDDZ)V", at = @At("TAIL"))
    private void instance(Identifier id, SoundCategory category, float volume, float pitch, Random random, boolean repeat, int repeatDelay, SoundInstance.AttenuationType attenuationType, double x, double y, double z, boolean relative, CallbackInfo ci) {
        if(Objects.equals(id.getNamespace(), Frogvasion.MOD_ID)) {
            this.volume *= FrogvasionGameOptions.getFrogVolume();
        }
    }
}
