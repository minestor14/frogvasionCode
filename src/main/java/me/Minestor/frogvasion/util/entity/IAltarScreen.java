package me.Minestor.frogvasion.util.entity;

import me.Minestor.frogvasion.screen.AltarManualScreen;
import org.jetbrains.annotations.Nullable;

public interface IAltarScreen {
    AltarManualScreen frogvasion$getAltarScreen();
    void frogvasion$setAltarScreen(@Nullable AltarManualScreen screen);
}
