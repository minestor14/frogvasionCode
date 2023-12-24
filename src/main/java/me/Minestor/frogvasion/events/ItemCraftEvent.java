package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemCraftEvent {
    public static ItemStack craft(ItemStack stack, int count, ServerPlayerEntity p) {
        if(QuestDataManager.getData((IEntityDataSaver) p) == null) return stack;
        Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

        if(!quest.isOfType(QuestType.Craft)) return stack;
        if(stack.getItem() != quest.getData().getItem()) return stack;

        int amount = quest.getData().getAmount();
        if(count >= amount) {
            QuestDataManager.completedTask(p, quest, count);
            stack.setCount(count - amount);

        } else {
            QuestDataManager.completedTask(p, quest, count);
            stack.setCount(0);
        }
        return stack;
    }
}
