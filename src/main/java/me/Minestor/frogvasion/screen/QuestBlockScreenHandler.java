package me.Minestor.frogvasion.screen;

import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class QuestBlockScreenHandler extends ScreenHandler {
    private PlayerInventory inv;
    private Quest quest;
    public QuestBlockScreenHandler(int syncId, PlayerInventory inv) {
        super(ModScreenHandlers.QUEST_BLOCK_SCREEN_HANDLER, syncId);

        this.quest = getQuest(inv.player);
        this.inv = inv;

        if(!inv.player.getWorld().isClient){
            ServerPlayNetworking.send((ServerPlayerEntity) inv.player, ModMessages.UPDATE_QUEST_S2C, ModPackets.createQuestUpdate(quest));
        }

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inv, m, 8 + m * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
    public PlayerInventory getInv() {
        return inv;
    }

    public void setInv(PlayerInventory inv) {
        this.inv = inv;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Quest getQuest(PlayerEntity player) {
        return QuestDataManager.getData((IEntityDataSaver) player) == null ? new Quest(ExtraQuestData.random()) : QuestDataManager.getQuest((IEntityDataSaver) player);
    }
}
