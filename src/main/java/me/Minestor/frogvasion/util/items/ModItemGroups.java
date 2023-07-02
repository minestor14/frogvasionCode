package me.Minestor.frogvasion.util.items;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> FROGVASION_KEY = register("frogvasion_group");
    public static final ItemGroup FROGVASION_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Frogvasion.MOD_ID, "frogvasion_group"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.FROG_HELMET_ITEM)).entries((displayContext, entries) -> {}).displayName(Text.translatable("itemGroup.frogvasion.frogvasion_group"))
            .build());

    public static void registerItemGroups() {}
    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Frogvasion.MOD_ID, id));
    }
}
