package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.UpdateQuestPacket;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class PickupItemEvent {
    public static boolean pickup(int count, Item item, PlayerEntity p) {
        if(QuestDataManager.getData((IEntityDataSaver) p) == null) return false;
        Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

        if(!quest.isOfType(QuestType.Collect)) return false;
        if(item != quest.getData().getItem()) return false;

        int amount = quest.getData().getAmount();
        if(count >= amount) {
            QuestDataManager.completedTask((ServerPlayerEntity) p, quest, count);
            p.getInventory().insertStack(new ItemStack(item, count - amount));

        } else {
            QuestDataManager.completedTask((ServerPlayerEntity) p, quest, count);
        }
        ServerPlayNetworking.send((ServerPlayerEntity) p, ModMessages.UPDATE_QUEST_S2C, UpdateQuestPacket.createUpdate(QuestDataManager.getQuest((IEntityDataSaver) p)));
        return true;
    }
}
