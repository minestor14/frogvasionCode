package me.Minestor.frogvasion.util.entity;

import me.Minestor.frogvasion.entities.custom.FrogTypes;
import net.minecraft.nbt.NbtCompound;

public class GuideUnlocked {
    public static void modifyUnlocked(IEntityDataSaver player, FrogTypes type, boolean unlocked) {
        NbtCompound nbt = player.frogvasion$getPersistentData();
        nbt.putBoolean("guide." +type.toString().toLowerCase(), unlocked);
    }
    public static boolean hasType(IEntityDataSaver player, FrogTypes type) {
        NbtCompound nbt = player.frogvasion$getPersistentData();
        return nbt.contains("guide."+type.toString().toLowerCase()) && nbt.getBoolean("guide."+type.toString().toLowerCase());
    }
}
