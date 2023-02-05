package me.Minestor.frogvasion.blocks.custom;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.SoldierFrog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class  FrogTowerChestBlock extends Block {
    public static final IntProperty LEVEL = IntProperty.of("level",0,10);

    public FrogTowerChestBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LEVEL, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
        super.appendProperties(builder);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(state.get(LEVEL) <= 5) this.lootTableId = new Identifier(Frogvasion.MOD_ID, "blocks/tower_chests/frog_tower_chest" + state.get(LEVEL));
        super.onBroken(world, pos, state);
        int m = state.get(LEVEL);
        int i = 0;
        while(i < m) {
            if (!world.isClient()) {
                SoldierFrog bob = new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, (ServerWorld) world);
                bob.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ()+0.5);
                ((ServerWorld) world).spawnEntityAndPassengers(bob);
                bob.updatePosition(pos.getX() + 0.5, pos.getY(), pos.getZ()+0.5);
            }
            i++;
        }
    }
}
