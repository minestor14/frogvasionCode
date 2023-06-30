package me.Minestor.frogvasion.effects.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class FrogCamouflageEffect extends StatusEffect {

    int ticksLastForgive = 0;
    public FrogCamouflageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 6768936);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        forgive(entity, amplifier);

        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        ticksLastForgive++;

        if(ticksLastForgive >= 600/(1+amplifier)) {
            ticksLastForgive = 0;
            forgive(entity, amplifier);
        }
    }
    private void forgive(LivingEntity entity, int amplifier) {
        for(Entity e : entity.getWorld().getOtherEntities(entity, new Box(entity.getBlockPos()).expand(20 * (amplifier +1)))) {
            if(e instanceof MobEntity me) {
                if(me instanceof Angerable a && entity instanceof PlayerEntity p) {
                    a.forgive(p);
                    continue;
                }
                if(me.getTarget() != null){
                    if (Objects.equals(me.getTarget().getUuidAsString(), entity.getUuidAsString())) {
                        me.setTarget(null);
                    }
                }
            }
        }
    }
}
