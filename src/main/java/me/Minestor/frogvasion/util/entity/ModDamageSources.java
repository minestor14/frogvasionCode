package me.Minestor.frogvasion.util.entity;

import net.minecraft.entity.damage.DamageSource;


public class ModDamageSources {
    public static final DamageSource FROGVASIUM_ATTACK = new DamageSource("frogvasium_attack");
    public static final DamageSource MAGIC_REPAY = new DamageSource("magic_repay").setUsesMagic().setBypassesProtection();

    public static void registerDamageSources() {}
}
