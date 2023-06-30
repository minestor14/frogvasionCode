package me.Minestor.frogvasion.quests;

public enum QuestType {
    Collect(1),
    Kill(2),
    Mine(1),
    Enchant(3),
    Craft(2),
    Empty(0);
    final int reward;
    QuestType(int reward) {
        this.reward =reward;
    }

    public int getReward() {
        return reward;
    }
}
