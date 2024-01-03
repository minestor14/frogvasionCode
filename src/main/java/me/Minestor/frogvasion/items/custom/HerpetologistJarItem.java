package me.Minestor.frogvasion.items.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.FrogTypes;
import me.Minestor.frogvasion.entities.custom.ModFrog;
import me.Minestor.frogvasion.entities.custom.TadpoleRocket;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.util.entity.GuideUnlocked;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HerpetologistJarItem extends Item {
    public HerpetologistJarItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof ModFrog mf && !(entity instanceof TadpoleRocket) && !user.getWorld().isClient && hand == Hand.MAIN_HAND) {
            NbtCompound nbt = stack.getOrCreateNbt();
            if(!nbt.contains("frog_type")){
                nbt.putString("frog_type", mf.getFrogType().name());
                nbt.put("frog_nbt", NbtPredicate.entityToNbt(mf));

                stack.setNbt(nbt);
                int s = user.getInventory().selectedSlot;
                user.getInventory().setStack(s, stack);

                mf.discard();
                GuideUnlocked.modifyUnlocked((IEntityDataSaver) user, mf.getFrogType(), true);
                ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessages.UPDATE_GUIDE, ModPackets.guideUpdate((IEntityDataSaver) user));
            } else {
                user.sendMessage(Text.translatable("text.item.herpetologist_jar").formatted(Formatting.RED));
            }
        }
        return ActionResult.success(user.getWorld().isClient);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        NbtCompound nbt = stack.getOrCreateNbt();
        World world = context.getWorld();

        if(nbt.contains("frog_type") && !world.isClient && context.getPlayer() != null){
            ModFrog frog = ModEntities.getFrog(FrogTypes.valueOf(nbt.getString("frog_type")), world);
            try {
                frog.setNbt(nbt.getCompound("frog_nbt"));
            } catch (CommandSyntaxException ignored) {}
            pos = pos.add(context.getSide().getVector());

            frog.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            ((ServerWorld) world).spawnEntityAndPassengers(frog);

            nbt.remove("frog_type");
            nbt.remove("frog_nbt");
            stack.damage(1, context.getPlayer(), (p) -> p.sendToolBreakStatus(context.getHand()));

            int s = context.getPlayer().getInventory().selectedSlot;
            context.getPlayer().getInventory().setStack(s, stack);
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.herpetologist_jar_tt", Text.translatable("item.frogvasion.guide_to_frogs")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains("frog_type")) {
            tooltip.add(FrogTypes.valueOf(nbt.getString("frog_type")).getTranslation().formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
