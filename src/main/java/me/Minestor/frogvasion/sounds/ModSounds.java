package me.Minestor.frogvasion.sounds;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CROAK = registerSoundEvent("croak");
    public static final SoundEvent GUARANTEED_CROAK = registerSoundEvent("guaranteed_croak");
    public static final SoundEvent CHARGE = registerSoundEvent("charge");
    public static final SoundEvent ICE_SPIKE_BREAK = registerSoundEvent("ice_spike_break");
    public static final SoundEvent TREE_FROG_CROAK = registerSoundEvent("tree_frog_croak");
    public static final SoundEvent QUEST_COMPLETED = registerSoundEvent("quest_completed");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Frogvasion.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void initSounds() {}
}
