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
    public static final SimpleOption<Boolean> SILLY_MODE = createSillyMode("silly_mode");
    public static final SimpleOption<Boolean> SHOW_TOOLTIPS = createShowTooltips("show_tooltips");
    public static SimpleOption<Double> createSoundVolumeOption(String key) {
        return new SimpleOption<>(key, SimpleOption.constantTooltip(Text.translatable("text.options.frogvasion.frog_volume_tt")),
                (prefix, value) ->
                        value == 0.0 ? getGenericValueText(prefix, ScreenTexts.OFF) : getPercentValueText(prefix, value),
                SimpleOption.DoubleSliderCallbacks.INSTANCE, 1.0,
                (value) -> MinecraftClient.getInstance().getSoundManager().reloadSounds());
    }
    public static SimpleOption<Integer> createAmountCroaks(String key) {
        return new SimpleOption<>(key, SimpleOption.constantTooltip(Text.translatable("text.options.frogvasion.croak_density_tt")),
                (prefix, value) ->
                        getGenericValueText(prefix, Text.translatable("text.options.frogvasion.croak_density", value)),
                new SimpleOption.ValidatingIntSliderCallbacks(0, 10), 10,
                (value) -> MinecraftClient.getInstance().getSoundManager().reloadSounds());
    }
    public static SimpleOption<Boolean> createSillyMode(String key) {
        return SimpleOption.ofBoolean(key, SimpleOption.constantTooltip(Text.translatable("text.options.frogvasion.silly_mode_tt")),
                (prefix, value) ->
                        Text.translatable("text.options.frogvasion.silly_mode", value ? "§5§lOn" : "Off"),false,
                (value) -> MinecraftClient.getInstance().worldRenderer.reload());
    }
    public static SimpleOption<Boolean> createShowTooltips(String key) {
        return SimpleOption.ofBoolean(key, SimpleOption.constantTooltip(Text.translatable("text.options.frogvasion.show_tooltips_tt")),
                (prefix, value) -> Text.translatable("text.options.frogvasion.show_tooltips", value ? "On" : "Off"), true,
                value -> {});
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
    public static float getFrogVolume() {return FROG_VOLUME.getValue().floatValue();}
    public static int getCroakDensity() {return CROAK_DENSITY.getValue();}
    public static boolean getSillyMode() {return SILLY_MODE.getValue();}
    public static boolean getShowTooltips() {return SHOW_TOOLTIPS.getValue();}

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
