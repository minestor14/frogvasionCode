package me.Minestor.frogvasion.effects.custom;

import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class ImproverEffect extends StatusEffect {
    private final EntityAttributeModifier damage = new EntityAttributeModifier(UUID.randomUUID(),"damage_improver",1.5, EntityAttributeModifier.Operation.ADDITION);
    private final EntityAttributeModifier health = new EntityAttributeModifier(UUID.randomUUID(),"health_improver",2, EntityAttributeModifier.Operation.ADDITION);
    private final EntityAttributeModifier speed = new EntityAttributeModifier(UUID.randomUUID(),"speed_improver",0.1, EntityAttributeModifier.Operation.ADDITION);
    public ImproverEffect() {
        super(StatusEffectCategory.BENEFICIAL, 16455082);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
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

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).hasModifier(speed)) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(speed);
        }
        if(entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) !=null && !entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).hasModifier(damage)) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addTemporaryModifier(damage);
        }
        if(!entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).hasModifier(health)) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(health);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(speed);
        if(entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) !=null) entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).removeModifier(damage);
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(health);
    }
}
