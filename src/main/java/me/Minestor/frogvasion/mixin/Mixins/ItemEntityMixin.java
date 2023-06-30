package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.events.PickupItemEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Nameable, EntityLike, CommandOutput {
    @Shadow public abstract ItemStack getStack();
    @Shadow private int pickupDelay;
    @Shadow @Nullable private UUID owner;

    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void onPlayerPickup(PlayerEntity player, CallbackInfo ci) {
        if(!this.world.isClient) {
            ItemStack itemStack = this.getStack();
            Item item = itemStack.getItem();
            int i = itemStack.getCount();
            if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(player.getUuid())) && hasRoom(player.getInventory(), itemStack)) {
                boolean match = PickupItemEvent.pickup(i, item, player);

                if(match) {
                    this.setStack(ItemStack.EMPTY);
                    ci.cancel();
                }
            }
        }
    }

    private static boolean hasRoom(PlayerInventory inv, ItemStack stack) {
        int i = inv.getOccupiedSlotWithRoomForStack(stack);
        if (i == -1) {
            i = inv.getEmptySlot();
        }

        return i != -1;
    }
}
