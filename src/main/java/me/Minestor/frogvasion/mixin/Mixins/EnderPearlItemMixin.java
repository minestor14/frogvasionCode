package me.Minestor.frogvasion.mixin.Mixins;

import me.Minestor.frogvasion.effects.ModEffects;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.EnderFrog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin {

    @Inject(method = "use", at = @At("HEAD"))
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!world.isClient && !user.hasStatusEffect(ModEffects.FROG_CAMOUFLAGE) && world.random.nextFloat() < 0.2f) {
            EnderFrog frog = new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world);
            frog.setPos(user.getX(), user.getY(), user.getZ());
            ((ServerWorld)world).spawnEntityAndPassengers(frog);
            frog.updatePosition(user.getX(),user.getY(),user.getZ());
        }
    }
}
