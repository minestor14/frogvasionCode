package me.Minestor.frogvasion.blocks;

import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.items.custom.OrchidGrenadeItemEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public enum OrchidType {
    PINK,
    BLUE,
    PURPLE,
    DARK_PURPLE,
    DARK_RED,
    WHITE,
    BLACK,
    MIX;
    public void applyEffect(List<LivingEntity> entities, int power, OrchidGrenadeItemEntity grenade, LivingEntity attacker) {
        for (LivingEntity e: entities) {
            World world = e.getWorld();
            BlockPos pos = e.getBlockPos();
            if(this == PINK || this == MIX) {
                e.getVelocity().add(0, (double) power / 2,0);
            }
            if(this == BLUE || this == MIX) {
                e.extinguish();
                if(e.hurtByWater()) {
                    e.damage(e.getDamageSources().mobProjectile(grenade, attacker), 3 * power);
                }
                e.setAir(0);
            }
            if(this == PURPLE || this == MIX) {
                e.addStatusEffect(new StatusEffectInstance(ModEffects.IMPROVER, 70 * Math.round((float) power / 2), Math.round((float) power / 2)));
                e.addStatusEffect(new StatusEffectInstance(ModEffects.FROG_CAMOUFLAGE, 70 * Math.round((float) power / 2), Math.round((float) power / 2)));
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 70 * Math.round((float) power / 2), Math.round((float) power / 2)));
            }
            if(this == DARK_PURPLE || this == MIX) {
                e.requestTeleport(attacker.getX(), attacker.getY(), attacker.getZ());
            }
            if(this == DARK_RED || this == MIX) {
                e.heal(2.5f * power);
            }
            if(this == WHITE || this == MIX) {
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 90 * power, Math.round((float) power / 2)));
                if(e.getGroup() == EntityGroup.UNDEAD) {
                    e.setFireTicks(100);
                }
                if(world.getBlockState(pos).isIn(BlockTags.REPLACEABLE)) {
                    world.setBlockState(pos, AbstractFireBlock.getState(world, pos));
                }
            }
            if(this == BLACK || this == MIX) {
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 90 * power, 1));
                e.damage(e.getDamageSources().mobAttack(attacker), 10 * power);
                if(e.isSleeping()) e.wakeUp();
            }
        }
    }
    public ParticleEffect getParticle(int power) {
        return switch (this) {
            case PINK -> new DustParticleEffect(new Vector3f(216, 156, 198).div(255), 0.2f * power);
            case BLUE -> new DustParticleEffect(new Vector3f(42, 191, 253).div(255), 0.2f * power);
            case PURPLE -> new DustParticleEffect(new Vector3f(166, 129, 179).div(255), 0.2f * power);
            case DARK_PURPLE -> new DustParticleEffect(new Vector3f(122, 67, 142).div(255), 0.2f * power);
            case DARK_RED -> new DustParticleEffect(new Vector3f(136,67,71).div(255), 0.2f * power);
            case WHITE -> new DustParticleEffect(new Vector3f(255, 244, 253).div(255), 0.2f * power);
            case BLACK -> new DustParticleEffect(new Vector3f(24,0 ,4).div(255), 0.2f * power);
            case MIX -> new DustParticleEffect(new Vector3f(137, 95, 122).div(255), 0.2f * power);
        };
    }
    public Formatting getColor() {
        return switch (this) {
            case PINK -> Formatting.RED;
            case BLUE -> Formatting.BLUE;
            case PURPLE -> Formatting.LIGHT_PURPLE;
            case DARK_PURPLE -> Formatting.DARK_PURPLE;
            case DARK_RED -> Formatting.DARK_RED;
            case WHITE -> Formatting.WHITE;
            case BLACK -> Formatting.BLACK;
            case MIX -> Formatting.GOLD;
        };
    }
}
