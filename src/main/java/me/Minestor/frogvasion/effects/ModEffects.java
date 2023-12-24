package me.Minestor.frogvasion.effects;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.effects.custom.FrogCamouflageEffect;
import me.Minestor.frogvasion.effects.custom.ImproverEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect IMPROVER = registerEffect("improver", new ImproverEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "befd39fa-90b2-4760-ae93-e017f175bfee", 2, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "784512de-0164-4924-ad6e-c76c98aabcb1", 0.1, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "301a0405-860c-450e-9df0-77f9083b4dc8", 1.5, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect FROG_CAMOUFLAGE = registerEffect("frog_camouflage", new FrogCamouflageEffect());

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Frogvasion.MOD_ID,name), effect);
    }
    public static void initEffects() {}
}
