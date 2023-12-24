package me.Minestor.frogvasion.recipe;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Frogvasion.MOD_ID, ConversionPedestalRecipe.Serializer.ID),
                    ConversionPedestalRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Frogvasion.MOD_ID, ConversionPedestalRecipe.Type.ID),
                    ConversionPedestalRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Frogvasion.MOD_ID, FloradicAltarRecipe.Serializer.ID),
                FloradicAltarRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Frogvasion.MOD_ID, FloradicAltarRecipe.Type.ID),
                FloradicAltarRecipe.Type.INSTANCE);
    }
}
