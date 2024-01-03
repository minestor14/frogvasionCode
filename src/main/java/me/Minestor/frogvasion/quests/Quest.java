package me.Minestor.frogvasion.quests;

import net.minecraft.server.network.ServerPlayerEntity;

public class Quest {
    private ExtraQuestData data;

    public Quest(ExtraQuestData data) {
        this.data = data;
    }

    public boolean isOfType(QuestType type) {
        return data.getType() == type;
    }

    public ExtraQuestData getData() {
        return this.data;
    }

    public void setData(ExtraQuestData data) {
        this.data = data;
    }

    public QuestType getType() {
        return data.getType();
    }

    public void decreaseAmount(int amount, ServerPlayerEntity player) {
        this.data.decreaseAmount(amount, player);
    }

    @Override
    public String toString() {
        return "Quest" + data.getAsNBT().asString();
    }
}
