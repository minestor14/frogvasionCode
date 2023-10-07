package me.Minestor.frogvasion.util.options;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class FrogvasionOptionsScreen extends GameOptionsScreen {
    private OptionListWidget optionButtons;
    public static final Text NAME = Text.translatable("text.options.frogvasion.title");

    public FrogvasionOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, NAME);
    }
    @Override
    protected void init() {
        this.optionButtons = new OptionListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.optionButtons.addSingleOptionEntry(FrogvasionGameOptions.FROG_VOLUME);
        this.optionButtons.addSingleOptionEntry(FrogvasionGameOptions.CROAK_DENSITY);
        this.optionButtons.addSingleOptionEntry(FrogvasionGameOptions.SILLY_MODE);
        this.addSelectableChild(this.optionButtons);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            MinecraftClient.getInstance().options.write();
            MinecraftClient.getInstance().setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        this.render(context, this.optionButtons, mouseX, mouseY, tickDelta);
    }
}
