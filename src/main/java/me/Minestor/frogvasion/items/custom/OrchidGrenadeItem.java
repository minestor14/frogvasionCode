package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrchidGrenadeItem extends Item {
    public OrchidGrenadeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);

        user.getItemCooldownManager().set(this, 5);
        if (!world.isClient) {
            OrchidGrenadeItemEntity grenade = new OrchidGrenadeItemEntity(world, user.getX(), user.getEyeY(),user.getZ(), itemStack, user);
            grenade.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1F, 0.2F);
            world.spawnEntity(grenade);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrement(1);

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        if(!stack.isEmpty() && nbt != null && nbt.contains("grenade")){
            NbtCompound nbtGrenade = nbt.getCompound("grenade");
            for (int i = 1; i < 4; i++) {
                OrchidType type = OrchidType.valueOf(nbtGrenade.getString("orchid_type_" + i));
                OrchidIntensity intensity = OrchidIntensity.valueOf(nbtGrenade.getString("orchid_intensity_" + i));

                tooltip.add(Text.literal(type.toString().replace('_', ' ') + " " + intensity).formatted(type.getColor()));
            }
        }
        if(FrogvasionGameOptions.getShowTooltips()) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.orchid_grenade").formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}