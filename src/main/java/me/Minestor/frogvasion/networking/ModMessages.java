package me.Minestor.frogvasion.networking;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.entity.FloradicAltarBlockEntity;
import me.Minestor.frogvasion.blocks.entity.ModBlockEntities;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import me.Minestor.frogvasion.util.quest.ServerQuestProgression;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModMessages {
    public static final Identifier UPDATE_TRAP = new Identifier(Frogvasion.MOD_ID,"update_trap");
    public static final Identifier UPDATE_QUEST_C2S = new Identifier(Frogvasion.MOD_ID, "update_quest_c2s");
    public static final Identifier UPDATE_QUEST_S2C = new Identifier(Frogvasion.MOD_ID, "update_quest_s2c");
    public static final Identifier REQUEST_QUEST = new Identifier(Frogvasion.MOD_ID, "request_quest");
    public static final Identifier FLORADIC_S2C = new Identifier(Frogvasion.MOD_ID, "floradic_s2c");
    public static final Identifier FLORADIC_C2S = new Identifier(Frogvasion.MOD_ID, "floradic_c2s");
    public static final Identifier GRENADE_EXPLOSION = new Identifier(Frogvasion.MOD_ID, "grenade_explosion");
    public static final Identifier ALTAR_MANUAL_S2C = new Identifier(Frogvasion.MOD_ID, "altar_manual_s2c");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_QUEST_C2S, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
            int amount = buf.readByte();
            int originalAmount = buf.readByte();
            String type = buf.readString();
            String item = buf.readString();
            String block = buf.readString();
            String target = buf.readString();
            boolean completed = buf.readBoolean();
            boolean active = buf.readBoolean();

            ExtraQuestData data = ExtraQuestData.of(amount, originalAmount, QuestType.valueOf(type),
                    Registries.ITEM.get(Identifier.tryParse(item)), Registries.BLOCK.get(Identifier.tryParse(block)),
                    Registries.ENTITY_TYPE.get(Identifier.tryParse(target)), completed);
            data.setActive(active);

            Quest quest = new Quest(data);

            QuestDataManager.setQuest((IEntityDataSaver) player, quest);
            ServerQuestProgression.IQuestProgressionEvent.PROGRESS.invoker().interact(player, quest);
        });

        ServerPlayNetworking.registerGlobalReceiver(ModMessages.REQUEST_QUEST, (server, player, handler, buf, responseSender) -> {
            if(QuestDataManager.getData((IEntityDataSaver) player) != null){
                ServerPlayNetworking.send(player, ModMessages.UPDATE_QUEST_S2C, ModPackets.createQuestUpdate(QuestDataManager.getQuest((IEntityDataSaver) player)));
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(ModMessages.FLORADIC_C2S, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();

            FloradicAltarBlockEntity be = player.getWorld().getBlockEntity(pos, ModBlockEntities.FLORADIC_ALTAR_TYPE).get();
            ServerPlayNetworking.send(player, ModMessages.FLORADIC_S2C, ModPackets.createFloradicUpdate(pos, be.getItems(), be.getProgress()));
        });
    }
}
