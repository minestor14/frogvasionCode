package me.Minestor.frogvasion.items.Custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class IceSpikeItem extends Item {

    public IceSpikeItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);

		user.getItemCooldownManager().set(this, 5);
        if (!world.isClient) {
            IceSpikeItemEntity iceSpikeItemEntity = new IceSpikeItemEntity(user, world);
            iceSpikeItemEntity.setPos(user.getX(),user.getEyeY(),user.getZ());
            iceSpikeItemEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.7F, 0.2F);
            world.spawnEntity(iceSpikeItemEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrement(1);

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
