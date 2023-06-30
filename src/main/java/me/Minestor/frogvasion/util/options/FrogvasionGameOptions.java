package me.Minestor.frogvasion.util.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.function.Function;

import static net.minecraft.client.option.GameOptions.getGenericValueText;
@Environment(EnvType.CLIENT)
public class FrogvasionGameOptions {
    public static final SimpleOption<Double> FROG_VOLUME = createSoundVolumeOption("frog_volume");
    public static final SimpleOption<Integer> CROAK_DENSITY = createAmountCroaks("croak_density");
    public static SimpleOption<Double> createSoundVolumeOption(String key) {
        return new SimpleOption<>(key, SimpleOption.constantTooltip(Text.literal("Adjusts the volume of the sounds made by the mod Frogvasion. \nThanks to lil_potacho for recommending this feature.")),
                (prefix, value) ->
                        value == 0.0 ? getGenericValueText(prefix, ScreenTexts.OFF) : getPercentValueText(prefix, value),
                SimpleOption.DoubleSliderCallbacks.INSTANCE, 1.0,
                (value) -> MinecraftClient.getInstance().getSoundManager().reloadSounds());
    }
    public static SimpleOption<Integer> createAmountCroaks(String key) {
        return new SimpleOption<>(key, SimpleOption.constantTooltip(Text.literal("Controls how often a frog croaks: 10 is normal, 0 is never")),
                (optionText, value) ->
                        getGenericValueText(optionText, Text.translatable("text.options.frogvasion.croak_density", value)),
                new SimpleOption.ValidatingIntSliderCallbacks(0, 10), 10,
                (value) -> MinecraftClient.getInstance().getSoundManager().reloadSounds());
    }
    public static Text getPercentValueText(Text prefix, double value) {
        return Text.translatable("options.percent_value", prefix, (int)(value * 100.0));
    }
    public static boolean isTrue(String value) {
        return "true".equals(value);
    }
    public static boolean isFalse(String value) {
        return "false".equals(value);
    }
    public static float getFrogVolume() {
        return FROG_VOLUME.getValue().floatValue();
    }
    public static int getCroakDensity() {
        return CROAK_DENSITY.getValue();
    }

    @Environment(EnvType.CLIENT)
    public interface Visitor {
        <T> void accept(String key, SimpleOption<T> option);

        int visitInt(String key, int current);

        boolean visitBoolean(String key, boolean current);

        String visitString(String key, String current);

        float visitFloat(String key, float current);

        <T> T visitObject(String key, T current, Function<String, T> decoder, Function<T, String> encoder);
    }
}
