package me.Minestor.frogvasion.enchantments.custom;

import me.Minestor.frogvasion.entities.custom.ModFrog;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;

public class FrogFriend extends ProtectionEnchantment {
    public FrogFriend() {
        super(Rarity.UNCOMMON, Type.ALL, EquipmentSlot.HEAD);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    public boolean canAccept(Enchantment other) {
        return !(other instanceof ProtectionEnchantment);
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        if(source.getAttacker() instanceof ModFrog) {
            return level * 2;
        }
        return 0;
    }
}
