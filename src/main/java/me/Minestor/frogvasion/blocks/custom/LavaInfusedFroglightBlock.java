package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.entities.custom.ModFrog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LavaInfusedFroglightBlock extends Block {
    public LavaInfusedFroglightBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.bypassesSteppingEffects() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
        }
        if(entity instanceof ModFrog) {
            if (world.random.nextInt(100) == 69) {
                ((ModFrog) entity).setInfused(true);
                world.setBlockState(pos, Blocks.OCHRE_FROGLIGHT.getDefaultState());
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity));
            } else {
                entity.setOnFireFor(1);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.FROGLIGHT;
    }
}
