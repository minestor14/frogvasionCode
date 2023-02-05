package me.Minestor.frogvasion.effects.potion;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.mixin.Mixins.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static Potion IMPROVER_POTION;

    public static Potion registerPotion(String name, StatusEffect effect, int duration, int amplifier) {
        return Registry.register(Registries.POTION,new Identifier(Frogvasion.MOD_ID,name),new Potion(new StatusEffectInstance(effect,duration,amplifier)));
    }

    public static void registerPotions() {
        IMPROVER_POTION = registerPotion("improver_potion", ModEffects.IMPROVER,100,0);
        registerPotionRecipes();
    }
    private static void registerPotionRecipes() {
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.ENHANCED_MUTAGEN, ModPotions.IMPROVER_POTION);
    }
}
