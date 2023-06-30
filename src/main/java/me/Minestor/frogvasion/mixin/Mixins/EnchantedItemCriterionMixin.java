package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.events.EnchantEvent;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantedItemCriterion.class)
public class EnchantedItemCriterionMixin {
    @Inject(method = "trigger", at = @At("HEAD"))
    public void triggerEnchant(ServerPlayerEntity player, ItemStack stack, int levels, CallbackInfo ci) {
        EnchantEvent.enchant(player);
    }
}
