package me.Minestor.frogvasion.screen;

import me.Minestor.frogvasion.Frogvasion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<ConversionPedestalScreenHandler> CONVERSION_PEDESTAL_SCREEN_HANDLER;
    public static ScreenHandlerType<QuestBlockScreenHandler> QUEST_BLOCK_SCREEN_HANDLER;
    public static void registerScreenHandlers() {
        CONVERSION_PEDESTAL_SCREEN_HANDLER = new ScreenHandlerType<>(ConversionPedestalScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        QUEST_BLOCK_SCREEN_HANDLER = new ScreenHandlerType<>(QuestBlockScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Frogvasion.MOD_ID, "conversion_pedestal_screen"), CONVERSION_PEDESTAL_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Frogvasion.MOD_ID, "quest_block_screen"), QUEST_BLOCK_SCREEN_HANDLER);
    }
}
