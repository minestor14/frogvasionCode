package me.Minestor.frogvasion.screen;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.screen.custom.BoostingPlateScreenHandler;
import me.Minestor.frogvasion.screen.custom.ConversionPedestalScreenHandler;
import me.Minestor.frogvasion.screen.custom.QuestBlockScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<ConversionPedestalScreenHandler> CONVERSION_PEDESTAL_SCREEN_HANDLER = register("conversion_pedestal_screen", new ScreenHandlerType<>(ConversionPedestalScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static ScreenHandlerType<QuestBlockScreenHandler> QUEST_BLOCK_SCREEN_HANDLER = register("quest_block_screen", new ScreenHandlerType<>(QuestBlockScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static ScreenHandlerType<BoostingPlateScreenHandler> BOOSTING_PLATE_SCREEN_HANDLER = register("boosting_plate_screen", new ScreenHandlerType<>(BoostingPlateScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static void registerScreenHandlers() {}
    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType<T> type) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(Frogvasion.MOD_ID, name), type);
    }
}