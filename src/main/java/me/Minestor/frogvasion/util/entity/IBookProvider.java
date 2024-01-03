package me.Minestor.frogvasion.util.entity;

import me.Minestor.frogvasion.screen.custom.AltarManualScreen;
import me.Minestor.frogvasion.screen.custom.GuideToFrogsScreen;
import org.jetbrains.annotations.Nullable;

public interface IBookProvider {
    AltarManualScreen frogvasion$getAltarScreen();
    void frogvasion$setAltarScreen(@Nullable AltarManualScreen screen);
    GuideToFrogsScreen frogvasion$getGuide();
    void frogvasion$setGuide(@Nullable GuideToFrogsScreen screen);
}
