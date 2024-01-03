package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.screen.custom.AltarManualScreen;
import me.Minestor.frogvasion.screen.custom.GuideToFrogsScreen;
import me.Minestor.frogvasion.util.entity.IBookProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public class ClientPlayerEntityMixin implements IBookProvider {
    @Unique
    private AltarManualScreen altarManualScreen;
    @Unique
    private GuideToFrogsScreen guideScreen;
    @Override
    public AltarManualScreen frogvasion$getAltarScreen() {
        if(this.altarManualScreen == null) {
            this.altarManualScreen = new AltarManualScreen((ClientPlayerEntity) (Object) this);
        }
        return altarManualScreen;
    }

    @Override
    public void frogvasion$setAltarScreen(AltarManualScreen screen) {
        this.altarManualScreen = screen;
    }

    @Override
    public GuideToFrogsScreen frogvasion$getGuide() {
        if(this.guideScreen == null) {
            this.guideScreen = new GuideToFrogsScreen((ClientPlayerEntity) (Object) this);
        }
        return guideScreen;
    }

    @Override
    public void frogvasion$setGuide(@Nullable GuideToFrogsScreen screen) {
        this.guideScreen = screen;
    }

}
