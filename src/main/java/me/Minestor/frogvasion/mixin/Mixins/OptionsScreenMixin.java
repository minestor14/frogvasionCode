package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.util.options.FrogvasionOptionsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen implements Drawable, ParentElement {

    @Shadow @Final private GameOptions settings;
    protected OptionsScreenMixin(Text title) {
        super(title);
    }
    @Inject(method = "init", at = @At("HEAD"))
    protected void initFrogsettings(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(FrogvasionOptionsScreen.NAME, (button) -> {
            FrogvasionOptionsScreen screen = new FrogvasionOptionsScreen(this,this.settings);
            MinecraftClient.getInstance().setScreen(screen);
        }).dimensions(this.width / 2 - 100, this.height / 4 + 150, 200, 20).build());
    }

}
