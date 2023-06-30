package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.UpdateQuestPacket;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemCraftEvent {
    public static ItemStack craft(ItemStack stack, int count, PlayerEntity p) {
        if(!p.getWorld().isClient) return stack;
        if(QuestDataManager.getData((IEntityDataSaver) p) == null) return stack;
        Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

        if(!quest.isOfType(QuestType.Craft)) return stack;
        if(stack.getItem() != quest.getData().getItem()) return stack;

        int amount = quest.getData().getAmount();
        if(count >= amount) {
            QuestDataManager.completedClientTask((IEntityDataSaver) p, quest, count);
            stack.setCount(count - amount);

        } else {
            QuestDataManager.completedClientTask((IEntityDataSaver) p, quest, count);
            stack.setCount(0);
        }
        ClientPlayNetworking.send(ModMessages.UPDATE_QUEST_C2S, UpdateQuestPacket.createUpdate(QuestDataManager.getQuest((IEntityDataSaver) p)));
        return stack;
    }
}
