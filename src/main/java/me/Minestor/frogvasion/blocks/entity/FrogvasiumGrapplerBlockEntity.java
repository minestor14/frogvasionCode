package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.blocks.custom.FrogvasiumGrapplerBlock;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class FrogvasiumGrapplerBlockEntity extends BlockEntity implements FrogvasiumActiveBlock {
    public int cooldown = 0;

    public FrogvasiumGrapplerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FROGVASIUM_GRAPPLER_TYPE, pos, state);
    }

    public BlockPos getForward() {
        Direction dir = getCachedState().get(FrogvasiumGrapplerBlock.FACING);
        return new BlockPos(this.getPos().add(dir.getVector()));
    }
    public BlockPos getFroward(BlockPos pos) {
        Direction dir = getCachedState().get(FrogvasiumGrapplerBlock.FACING);
        return new BlockPos(pos.add(dir.getVector()));
    }
    public static void tick(World world, BlockPos pos, BlockState state, FrogvasiumGrapplerBlockEntity entity) {
        if(entity.isReceivingPower(pos, world) || entity.cooldown > 0) {
            entity.cooldown++;
            if(entity.cooldown == 5 && !world.isClient()) entity.applyDamage(state,(ServerWorld) world,pos);
            if(entity.cooldown >= 15) {
                entity.cooldown = 0;
            }
        }
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("frogvasium_grappler.cooldown", this.cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        cooldown = nbt.getInt("frogvasium_grappler.cooldown");
    }
    public void applyDamage(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos pos1 = this.getForward();
        List<Entity> list = world.getOtherEntities(null, new Box(pos1));
        list.addAll(world.getOtherEntities(null,new Box(getFroward(pos1))));
        for(Entity en : list) {
            if(en instanceof LivingEntity) {
                en.damage(this.getWorld().getDamageSources().create(ModDamageSources.FROGVASIUM_ATTACK_KEY),2f);
                en.setVelocity(new Vec3d(this.getPos().getX() + 0.5,this.getPos().getY(),this.getPos().getZ() + 0.5).subtract(new Vec3d(en.getX(),en.getY(),en.getZ())).normalize());
            }
        }
    }
    public boolean canPlay(World world, BlockPos pos) {
        return (isReceivingPower(pos, world) && cooldown == 0) || cooldown > 0;
    }
}
