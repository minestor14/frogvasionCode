package me.Minestor.frogvasion.mixin.Mixins;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static me.Minestor.frogvasion.Frogvasion.LOGGER;

@Environment(EnvType.CLIENT)
@Mixin(GameOptions.class)
//stole most of this code from GameOptions.class
public abstract class GameOptionsMixin {
    @Unique
    private static final Gson GSON = new Gson();
    @Unique
    private File frogvasionOptionsFile;
    @Shadow @Final private static Splitter COLON_SPLITTER;
    @Shadow protected abstract NbtCompound update(NbtCompound nbt);
    @Shadow protected MinecraftClient client;
    @Shadow public abstract File getOptionsFile();

    public void accept(FrogvasionGameOptions.Visitor visitor) {
        visitor.accept("frog_volume", FrogvasionGameOptions.FROG_VOLUME);
        visitor.accept("croak_density", FrogvasionGameOptions.CROAK_DENSITY);
        visitor.accept("silly_mode", FrogvasionGameOptions.SILLY_MODE);
        visitor.accept("show_tooltips", FrogvasionGameOptions.SHOW_TOOLTIPS);
    }

    @Inject(method = "load", at = @At("TAIL"), cancellable = true)
    private void acceptFrog(CallbackInfo ci) {
        frogvasionOptionsFile = new File(this.getOptionsFile().getParent(), "frogvasion.options.txt");
        try {
            if (!this.frogvasionOptionsFile.exists()) {
                ci.cancel();
            }

            NbtCompound nbtCompound = new NbtCompound();
            BufferedReader bufferedReader = Files.newReader(this.frogvasionOptionsFile, Charsets.UTF_8);

            try {
                bufferedReader.lines().forEach((line) -> {
                    try {
                        Iterator<String> iterator = COLON_SPLITTER.split(line).iterator();
                        nbtCompound.putString(iterator.next(), iterator.next());
                    } catch (Exception var3) {
                        LOGGER.warn("Skipping bad option: {}", line);
                    }

                });
            } catch (Throwable var6) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }

            final NbtCompound nbtCompound2 = this.update(nbtCompound);
            this.accept(new FrogvasionGameOptions.Visitor() {
                @Nullable
                private String find(String key) {
                    return nbtCompound2.contains(key) ? nbtCompound2.getString(key) : null;
                }

                public <T> void accept(String key, SimpleOption<T> option) {
                    String string = this.find(key);
                    if (string != null) {
                        JsonReader jsonReader = new JsonReader(new StringReader(string.isEmpty() ? "\"\"" : string));
                        JsonElement jsonElement = JsonParser.parseReader(jsonReader);
                        DataResult<T> dataResult = option.getCodec().parse(JsonOps.INSTANCE, jsonElement);
                        dataResult.error().ifPresent((partialResult) -> {
                            LOGGER.error("Error parsing option value " + string + " for option " + option + ": " + partialResult.message());
                        });
                        Optional<T> var10000 = dataResult.result();
                        Objects.requireNonNull(option);
                        var10000.ifPresent(option::setValue);
                    }

                }

                public int visitInt(String key, int current) {
                    String string = this.find(key);
                    if (string != null) {
                        try {
                            return Integer.parseInt(string);
                        } catch (NumberFormatException var5) {
                            LOGGER.warn("Invalid integer value for option {} = {}", key, string, var5);
                        }
                    }

                    return current;
                }

                public boolean visitBoolean(String key, boolean current) {
                    String string = this.find(key);
                    return string != null ? FrogvasionGameOptions.isTrue(string) : current;
                }

                public String visitString(String key, String current) {
                    return MoreObjects.firstNonNull(this.find(key), current);
                }

                public float visitFloat(String key, float current) {
                    String string = this.find(key);
                    if (string != null) {
                        if (FrogvasionGameOptions.isTrue(string)) {
                            return 1.0F;
                        }

                        if (FrogvasionGameOptions.isFalse(string)) {
                            return 0.0F;
                        }

                        try {
                            return Float.parseFloat(string);
                        } catch (NumberFormatException var5) {
                            LOGGER.warn("Invalid floating point value for option {} = {}", key, string, var5);
                        }
                    }

                    return current;
                }

                public <T> T visitObject(String key, T current, Function<String, T> decoder, Function<T, String> encoder) {
                    String string = this.find(key);
                    return string == null ? current : decoder.apply(string);
                }
            });

        } catch (Exception var7) {
            LOGGER.error("Failed to load options", var7);
        }
    }
    @Inject(method = "write", at = @At("TAIL"))
    public void writeFrogVolume(CallbackInfo ci) {
        frogvasionOptionsFile = new File(this.getOptionsFile().getParent(), "frogvasion.options.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.frogvasionOptionsFile), StandardCharsets.UTF_8));

            try {
                printWriter.println("version:" + SharedConstants.getGameVersion().getSaveVersion().getId());
                this.accept(new FrogvasionGameOptions.Visitor() {
                    public void print(String key) {
                        printWriter.print(key);
                        printWriter.print(':');
                    }

                    public <T> void accept(String key, SimpleOption<T> option) {
                        DataResult<JsonElement> dataResult = option.getCodec().encodeStart(JsonOps.INSTANCE, option.getValue());
                        dataResult.error().ifPresent((partialResult) -> {
                            LOGGER.error("Error saving option " + option + ": " + partialResult);
                        });
                        dataResult.result().ifPresent((json) -> {
                            this.print(key);
                            printWriter.println(GSON.toJson(json));
                        });
                    }

                    public int visitInt(String key, int current) {
                        this.print(key);
                        printWriter.println(current);
                        return current;
                    }

                    public boolean visitBoolean(String key, boolean current) {
                        this.print(key);
                        printWriter.println(current);
                        return current;
                    }

                    public String visitString(String key, String current) {
                        this.print(key);
                        printWriter.println(current);
                        return current;
                    }

                    public float visitFloat(String key, float current) {
                        this.print(key);
                        printWriter.println(current);
                        return current;
                    }

                    public <T> T visitObject(String key, T current, Function<String, T> decoder, Function<T, String> encoder) {
                        this.print(key);
                        printWriter.println(encoder.apply(current));
                        return current;
                    }
                });
            } catch (Throwable var5) {
                try {
                    printWriter.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            printWriter.close();
        } catch (Exception var6) {
            LOGGER.error("Failed to save options", var6);
        }
    }

    

}
