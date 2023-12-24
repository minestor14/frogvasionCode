package me.Minestor.frogvasion.effects.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ImproverEffect extends StatusEffect {
    public ImproverEffect() {
        super(StatusEffectCategory.BENEFICIAL, 16455082);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if((entity instanceof SoldierFrog|| entity instanceof GrapplingFrog || entity instanceof ArmedFrog || entity instanceof ExplosiveFrog) && !entity.getWorld().isClient()) {
            World world = entity.getWorld();
            GrowingFrog frog = new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world);
            frog.setPosition(entity.getPos());
            frog.setYaw(entity.getYaw());
            frog.setInfused(((ModFrog) entity).isInfused());
            entity.discard();
            ((ServerWorld)world).spawnEntityAndPassengers(frog);
        }

    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
