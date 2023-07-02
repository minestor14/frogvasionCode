package me.Minestor.frogvasion.util.entity;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;


public class ModDamageSources {

    public static final DamageType FROGVASIUM_ATTACK_TYPE = new DamageType("frogvasium_attack", DamageScaling.ALWAYS, 0.1f);
    public static final DamageType MAGIC_REPAY_TYPE = new DamageType("frogvasium_attack", DamageScaling.ALWAYS, 0.1f);
    public static final RegistryKey<DamageType> FROGVASIUM_ATTACK_KEY = key("frogvasium_attack");
    public static final RegistryKey<DamageType> MAGIC_REPAY_KEY = key("magic_repay");

    public static void registerDamageSources() {
        
    }
    public static RegistryKey<DamageType> key(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Frogvasion.MOD_ID, name));
    }
}
