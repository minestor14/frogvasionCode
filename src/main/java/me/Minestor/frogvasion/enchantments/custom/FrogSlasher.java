package me.Minestor.frogvasion.enchantments.custom;

import me.Minestor.frogvasion.items.custom.FrogStaffItem;
import me.Minestor.frogvasion.util.entity.ModEntityGroups;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class FrogSlasher extends DamageEnchantment {
    public FrogSlasher() {
        super(Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if(group == ModEntityGroups.FROGS) {
            return 1f + level * 2.5f;
        }
        return super.getAttackDamage(level, group);
    }
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || stack.getItem() instanceof SwordItem || stack.getItem() instanceof FrogStaffItem;
    }
}
