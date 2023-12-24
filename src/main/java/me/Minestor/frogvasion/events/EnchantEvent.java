package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class EnchantEvent {
    public static void enchant(ServerPlayerEntity p) {
        if(QuestDataManager.getData((IEntityDataSaver) p) == null) return;
        Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

        if(!quest.isOfType(QuestType.Enchant)) return;

        QuestDataManager.completedTask(p, quest, 1);
        ServerPlayNetworking.send(p, ModMessages.UPDATE_QUEST_S2C, ModPackets.createQuestUpdate(QuestDataManager.getQuest((IEntityDataSaver) p)));
    }
}
