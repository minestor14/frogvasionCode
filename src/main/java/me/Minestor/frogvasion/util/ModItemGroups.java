package me.Minestor.frogvasion.util;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup FROGVASION_GROUP = FabricItemGroup.builder(new Identifier(Frogvasion.MOD_ID, "frogvasion_group"))
            .icon(() -> new ItemStack(ModItems.FROG_HELMET_ITEM))
            .build();

    public static void registerItemGroups() {}
}
