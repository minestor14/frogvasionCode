package me.Minestor.frogvasion.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<ConversionPedestalScreenHandler> CONVERSION_PEDESTAL_SCREEN_HANDLER;
        //TODO fix this shit
    public static void registerScreenHandlers() {
        CONVERSION_PEDESTAL_SCREEN_HANDLER = new ScreenHandlerType<>(ConversionPedestalScreenHandler::new);
    }
}
