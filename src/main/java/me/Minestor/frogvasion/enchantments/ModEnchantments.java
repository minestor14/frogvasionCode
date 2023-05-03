package me.Minestor.frogvasion.enchantments;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.enchantments.custom.FrogFriend;
import me.Minestor.frogvasion.enchantments.custom.FrogSlasher;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final Enchantment FROG_SLASHER = register("frog_slasher", new FrogSlasher());
    public static final Enchantment FROG_FRIEND = register("frog_friend", new FrogFriend());

    private static Enchantment register(String name, Enchantment ench) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Frogvasion.MOD_ID, name), ench);
    }

    public static void registerEnchantments() {}
}