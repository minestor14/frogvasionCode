package me.Minestor.frogvasion.util.quest;

import me.Minestor.frogvasion.quests.Quest;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class ServerQuestProgression{
    public interface IQuestProgressionEvent {
        Event<IQuestProgressionEvent> PROGRESS = EventFactory.createArrayBacked(IQuestProgressionEvent.class,
                (listeners) -> (player, quest) -> {
                    for (IQuestProgressionEvent listener : listeners) {
                        ActionResult result = listener.interact(player, quest);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }

                    return ActionResult.SUCCESS;
                });

        ActionResult interact(ServerPlayerEntity player, Quest quest);
    }
    public interface IQuestCompletionEvent {
        Event<IQuestCompletionEvent> COMPLETION = EventFactory.createArrayBacked(IQuestCompletionEvent.class,
                (listeners) -> (player, quest) -> {
                    for (IQuestCompletionEvent listener : listeners) {
                        ActionResult result = listener.interact(player, quest);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }

                    return ActionResult.SUCCESS;
                });

        ActionResult interact(ServerPlayerEntity player, Quest quest);
    }
}
