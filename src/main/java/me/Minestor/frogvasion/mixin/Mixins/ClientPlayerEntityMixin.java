package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.screen.AltarManualScreen;
import me.Minestor.frogvasion.util.entity.IAltarScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public class ClientPlayerEntityMixin implements IAltarScreen {
    @Unique
    private AltarManualScreen altarManualScreen;
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
}
