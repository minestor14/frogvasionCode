package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.sounds.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class CroakingWhistleItem extends Item {
    public CroakingWhistleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.GUARANTEED_CROAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            for (Entity e : world.getOtherEntities(user, new Box(user.getBlockPos()).expand(20), entity -> entity instanceof ModFrog)) {
                ModFrog frog = (ModFrog) e;
                frog.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200), user);
            }
        }
        user.getItemCooldownManager().set(this, 100);
        return super.use(world, user, hand);
    }
}
