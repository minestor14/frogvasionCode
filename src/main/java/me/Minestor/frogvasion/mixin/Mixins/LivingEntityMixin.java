package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.items.Custom.FrogHelmetItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if(this.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof FrogHelmetItem) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 21 ,0,true,true));
        }
    }
}
