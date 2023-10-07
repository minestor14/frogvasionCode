package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogvasiumAttackerBlock;
import me.Minestor.frogvasion.util.entity.ModDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class FrogvasiumAttackerBlockEntity extends BlockEntity implements FrogvasiumActiveBlock {
    public int cooldown = 0;

    public FrogvasiumAttackerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROGVASIUM_ATTACKER_TYPE, pos, state);
    }

    public BlockPos getForward() {
        Direction dir = getCachedState().get(FrogvasiumAttackerBlock.FACING);
        return new BlockPos(this.getPos().add(dir.getVector()));
    }
    public static void tick(World world, BlockPos pos, BlockState state, FrogvasiumAttackerBlockEntity entity) {
        if(entity.isReceivingPower(pos, world) || entity.cooldown > 0) {
            entity.cooldown++;
            if(entity.cooldown == 5 && !world.isClient) entity.applyDamage(state,(ServerWorld) world,pos);
            if(entity.cooldown >= 10) {
                entity.cooldown = 0;
            }
        }
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("frogvasium_demolisher.cooldown", this.cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        cooldown = nbt.getInt("frogvasium_demolisher.cooldown");
    }
    public void applyDamage(BlockState state, ServerWorld world, BlockPos pos) {
        if(this.cooldown != 5) return;
        BlockPos pos1 = this.getForward();
        final List<Entity> list = world.getOtherEntities(null, new Box(pos1));
        for(Entity en : list) {
            if(en instanceof LivingEntity) {
                en.damage(this.getWorld().getDamageSources().create(ModDamageSources.FROGVASIUM_ATTACK_KEY),2f);
            }
        }
    }
    public boolean canPlay(World world, BlockPos pos) {
        return (isReceivingPower(pos, world) && cooldown == 0) || cooldown > 0;
    }
}
