package me.Minestor.frogvasion.events;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.UpdateQuestPacket;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class DeathEvent implements ServerLivingEntityEvents.AllowDeath{
    @Override
    public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if(entity.getAttacker() instanceof  PlayerEntity p) {
            if (QuestDataManager.getData((IEntityDataSaver) p) == null) return true;

            Quest quest = QuestDataManager.getQuest((IEntityDataSaver) p);

            if (!quest.isOfType(QuestType.Kill)) return true;
            if (entity.getType() != quest.getData().getTarget()) return true;

            QuestDataManager.completedTask((ServerPlayerEntity) p, quest, 1);
            ServerPlayNetworking.send((ServerPlayerEntity) p, ModMessages.UPDATE_QUEST_S2C, UpdateQuestPacket.createUpdate(QuestDataManager.getQuest((IEntityDataSaver) p)));

            entity.discard();
            return false;
        }
        return true;
    }
}
