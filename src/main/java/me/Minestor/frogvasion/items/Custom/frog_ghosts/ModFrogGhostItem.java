package me.Minestor.frogvasion.items.Custom.frog_ghosts;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.worldgen.dimension.ModDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ModFrogGhostItem extends Item {
    public ModFrogGhostItem(Settings settings) {
        super(settings);
    }

    private static void onDestroyed(ItemEntity entity, ServerWorld world, ModFrog frog) {
        if(entity.getBlockStateAtPos().isOf(ModBlocks.FROG_FLAME) && (world.getRegistryKey() == ModDimensions.GREENWOOD_DIMENSION_KEY || world.getRegistryKey() == World.OVERWORLD)) {
            if(entity.getBlockPos().getY() >= 2 && entity.getBlockPos().getY() <=255){
                entity.getWorld().setBlockState(entity.getBlockPos(), ModBlocks.GREENWOOD_PORTAL.getDefaultState());
            } else {
                entity.getWorld().setBlockState(entity.getBlockPos(), Blocks.AIR.getDefaultState());
                if(entity.getOwner() != null) entity.getOwner().sendMessage(Text.translatable("text.block.warning_greenwood_portal").formatted(Formatting.RED));
            }
        } else {
            frog.setPosition(entity.getPos());
            frog.setVelocity(new Vec3d(world.random.nextBoolean() ? world.random.nextFloat() / 2 : -world.random.nextFloat() / 2, 0.5, world.random.nextBoolean() ? world.random.nextFloat() / 2 : -world.random.nextFloat() / 2));
            frog.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 60, 0, true, true));
            world.spawnEntityAndPassengers(frog);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.item.frog_ghost").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    private static void onRightClickEntity(ItemStack stack, PlayerEntity user, LivingEntity entityClickedOn, ModFrog frogToSpawn) {
        if(entityClickedOn instanceof FrogEntity) {
            if(!entityClickedOn.getWorld().isClient()) {
                frogToSpawn.setPosition(entityClickedOn.getPos());
                frogToSpawn.setYaw(entityClickedOn.getYaw());
                frogToSpawn.setTamed(true,user);
                ((ServerWorld) entityClickedOn.getWorld()).spawnEntityAndPassengers(frogToSpawn);
                entityClickedOn.discard();
                stack.decrement(1);
            }
        }
    }
    public static class ArmedFrogGhostItem extends ModFrogGhostItem {
        public ArmedFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class BossSoldierFrogGhostItem extends ModFrogGhostItem {
        public BossSoldierFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class ExplosiveFrogGhostItem extends ModFrogGhostItem {
        public ExplosiveFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class GrapplingFrogGhostItem extends ModFrogGhostItem {
        public GrapplingFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class GrowingFrogGhostItem extends ModFrogGhostItem {
        public GrowingFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class SoldierFrogGhostItem extends ModFrogGhostItem {
        public SoldierFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class EnderFrogGhostItem extends ModFrogGhostItem {
        public EnderFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new EnderFrog(ModEntities.ENDER_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new EnderFrog(ModEntities.ENDER_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
    public static class IceFrogGhostItem extends ModFrogGhostItem {
        public IceFrogGhostItem(Settings settings) {
            super(settings);
        }
        @Override
        public void onItemEntityDestroyed(ItemEntity entity) {
            super.onItemEntityDestroyed(entity);
            if(!entity.getWorld().isClient()) {
                ServerWorld sworld = (ServerWorld) entity.getWorld();
                onDestroyed(entity, sworld, new IceFrog(ModEntities.ICE_FROG_ENTITY, sworld));
            }
        }
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            onRightClickEntity(stack,user,entity, new IceFrog(ModEntities.ICE_FROG_ENTITY, entity.getWorld()));
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
}
