package me.Minestor.frogvasion.effects;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.effects.custom.FrogCamouflageEffect;
import me.Minestor.frogvasion.effects.custom.ImproverEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect IMPROVER = registerEffect("improver", new ImproverEffect());
    public static final StatusEffect FROG_CAMOUFLAGE = registerEffect("frog_camouflage", new FrogCamouflageEffect());

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Frogvasion.MOD_ID,name), effect);
    }
    public static void initEffects() {}
}
