package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.items.ModItems;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {
    @Inject(method = "canRepair", at = @At("HEAD"), cancellable = true)
    private void carRepair(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if(ingredient.isOf(ModItems.RUBBER)) {
            cir.setReturnValue(true);
        }
    }
}
