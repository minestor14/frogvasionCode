package me.Minestor.frogvasion.worldgen.dimension;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    public static final RegistryKey<World> GREENWOOD_DIMENSION_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Frogvasion.MOD_ID,"greenwood"));
    public static final RegistryKey<DimensionType> GREENWOOD_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, GREENWOOD_DIMENSION_KEY.getValue());

    public static void register() {}
}
