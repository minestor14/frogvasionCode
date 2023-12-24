package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.events.ItemCraftEvent;
import net.minecraft.advancement.criterion.RecipeCraftedCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RecipeCraftedCriterion.class)
public class RecipeCraftedCriterionMixin {
    @Inject(method = "trigger", at = @At("HEAD"))
    private void triggerEvent(ServerPlayerEntity player, Identifier recipeId, List<ItemStack> ingredients, CallbackInfo ci) {
        if(player.getWorld().getRecipeManager().get(recipeId).isPresent()){
            ItemStack result = player.getWorld().getRecipeManager().get(recipeId).get().value().getResult(DynamicRegistryManager.EMPTY).copy();

            player.currentScreenHandler.setCursorStack(ItemCraftEvent.craft(result, result.getCount(), player));
        }
    }
}
