package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.quest.ServerQuestProgression;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class QuestProgressionEvent implements ServerQuestProgression.IQuestProgressionEvent {
    @Override
    public ActionResult interact(ServerPlayerEntity player, Quest quest) {
        ExtraQuestData data = quest.getData();
        if(!data.isEmpty()){
            player.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.literal("Progress on quest: " + (quest.getData().getOriginalAmount() - quest.getData().getAmount()) + "/" + quest.getData().getOriginalAmount())));

            player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), 0.8f, 1f);
        }

        return ActionResult.PASS;
    }
}
