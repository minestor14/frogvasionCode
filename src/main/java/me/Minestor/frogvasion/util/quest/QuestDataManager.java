package me.Minestor.frogvasion.util.quest;

import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class QuestDataManager {
    public static void setQuest(IEntityDataSaver player, Quest quest) {
        NbtCompound nbt = player.getPersistentData();
        ExtraQuestData data = quest.getData();

        nbt.copyFrom(data.getAsNBT());
    }
    @Nullable
    public static ExtraQuestData getData(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
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
    @Environment(EnvType.CLIENT)
    public static void completedClientTask(IEntityDataSaver player, Quest quest, int amount) {
        NbtCompound nbt = player.getPersistentData();
        quest.decreaseAmount(amount);

        nbt.copyFrom(quest.getData().getAsNBT());

        setQuest(player, quest);
    }
    public static void increaseQuestMilestone(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        int milestone = nbt.getInt("quest_milestone");
        milestone++;

        nbt.putInt("quest_milestone", milestone);
    }
    public static int getQuestMilestone(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getInt("quest_milestone");
    }
}