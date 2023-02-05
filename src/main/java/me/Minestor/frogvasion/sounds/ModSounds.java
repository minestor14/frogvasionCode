package me.Minestor.frogvasion.sounds;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CROAK = registerSoundEvent("croak");
    public static final SoundEvent CHARGE = registerSoundEvent("charge");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Frogvasion.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }
    public static void initSounds() {}
}
