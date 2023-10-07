package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.sounds.ModSounds;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import me.Minestor.frogvasion.util.quest.ServerQuestProgression;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class QuestCompletionEvent implements ServerQuestProgression.IQuestCompletionEvent {
    @Override
    public ActionResult interact(ServerPlayerEntity player, Quest quest) {
        player.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable("text.quest.completed")));

        player.playSound(ModSounds.QUEST_COMPLETED, SoundCategory.RECORDS, 1f, 1f);

        QuestDataManager.increaseQuestMilestone((IEntityDataSaver) player);

        if(QuestDataManager.getQuestMilestone((IEntityDataSaver) player) % 10 == 0) {
            player.giveItemStack(ModBlocks.GOLDEN_FROG_STATUE.asItem().getDefaultStack());
        } else {
            if (player.getWorld().getRandom().nextBetween(0,4) == 1) {
                player.giveItemStack(ModBlocks.FROG_STATUE.asItem().getDefaultStack());
            } else{
                player.giveItemStack(new ItemStack(ModItems.RUBBER, 10 * quest.getType().getReward()));
            }
        }
        player.addExperience(100 * quest.getType().getReward());

        return ActionResult.PASS;
    }
}
