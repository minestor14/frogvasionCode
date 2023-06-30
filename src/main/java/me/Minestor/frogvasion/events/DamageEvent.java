package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

public class DamageEvent implements ServerLivingEntityEvents.AllowDamage {
    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        if(entity.getMainHandStack().getItem() == ModItems.JUMPY_TOTEM || entity.getOffHandStack().getItem() == ModItems.JUMPY_TOTEM) {
            if(source.isOutOfWorld()) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 220, 10));
                entity.getStackInHand(entity.getMainHandStack().getItem() == ModItems.JUMPY_TOTEM ? Hand.MAIN_HAND : Hand.OFF_HAND).setCount(0);
                entity.playSound(SoundEvents.ITEM_TOTEM_USE,1,1);

                return false;
            }
        }
        return true;
    }
}
