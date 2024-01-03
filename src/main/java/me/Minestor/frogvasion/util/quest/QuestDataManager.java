package me.Minestor.frogvasion.util.quest;

import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class QuestDataManager {
    public static void setQuest(IEntityDataSaver player, Quest quest) {
        NbtCompound nbt = player.frogvasion$getPersistentData();
        ExtraQuestData data = quest.getData();

        nbt.copyFrom(data.getAsNBT());
    }
    @Nullable
    public static ExtraQuestData getData(IEntityDataSaver player) {
        NbtCompound nbt = player.frogvasion$getPersistentData();
        return ExtraQuestData.getFromNBT(nbt);
    }
    public static Quest getQuest(IEntityDataSaver player) {
        return new Quest(getData(player));
    }
    public static void completedTask(ServerPlayerEntity player, Quest quest, int amount) {
        quest.decreaseAmount(amount, player);

        setQuest((IEntityDataSaver) player, quest);

        ServerQuestProgression.IQuestProgressionEvent.PROGRESS.invoker().interact(player, quest);
    }
    public static void increaseQuestMilestone(IEntityDataSaver player) {
        NbtCompound nbt = player.frogvasion$getPersistentData();

        int milestone = nbt.getInt("quest_milestone");
        milestone++;

        nbt.putInt("quest_milestone", milestone);
    }
    public static int getQuestMilestone(IEntityDataSaver player) {
        NbtCompound nbt = player.frogvasion$getPersistentData();

        return nbt.getInt("quest_milestone");
    }
}