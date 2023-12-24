package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockBreakEvent implements PlayerBlockBreakEvents.Before{
    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity p, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if(QuestDataManager.getData((IEntityDataSaver) p) == null) return true;

        Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

        if(!quest.isOfType(QuestType.Mine)) return true;
        if(!state.isOf(quest.getData().getBlock())) return true;
        BlockState replacement;

        try {
            replacement = state.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
        } catch (IllegalArgumentException e) {
            replacement = Blocks.AIR.getDefaultState();
        }
        QuestDataManager.completedTask((ServerPlayerEntity) p, quest, 1);

        world.setBlockState(pos, replacement);
        ServerPlayNetworking.send((ServerPlayerEntity) p, ModMessages.UPDATE_QUEST_S2C, ModPackets.createQuestUpdate(QuestDataManager.getQuest((IEntityDataSaver) p)));

        return false;
    }
}
