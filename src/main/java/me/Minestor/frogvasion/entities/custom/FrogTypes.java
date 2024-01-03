package me.Minestor.frogvasion.entities.custom;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum FrogTypes {
    SOLDIER(1, "entity.frogvasion.soldier_frog"),
    BOSS_SOLDIER(2, "entity.frogvasion.boss_soldier_frog"),
    ARMED(3, "entity.frogvasion.armed_frog"),
    ENDER(4, "entity.frogvasion.ender_frog"),
    EXPLOSIVE(5, "entity.frogvasion.explosive_frog"),
    GRAPPLING(6, "entity.frogvasion.grappling_frog"),
    GROWING(7, "entity.frogvasion.growing_frog"),
    TADPOLE_ROCKET(8, "entity.frogvasion.tadpole_rocket"),
    ICE(9, "entity.frogvasion.ice_frog");
    final int id;
    final String translationKey;
    FrogTypes(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public int getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    public MutableText getTranslation() {
        return Text.translatable(translationKey);
    }
    public static FrogTypes getById(int id) {
        for (FrogTypes type : values()) {
            if(type.getId() == id) return type;
        }
        return SOLDIER;
    }
}
