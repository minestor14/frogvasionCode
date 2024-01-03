package me.Minestor.frogvasion.networking;

import me.Minestor.frogvasion.blocks.OrchidIntensity;
import me.Minestor.frogvasion.blocks.OrchidType;
import me.Minestor.frogvasion.blocks.entity.FloradicAltarBlockEntity;
import me.Minestor.frogvasion.blocks.entity.renderers.FrogTrapRenderer;
import me.Minestor.frogvasion.entities.custom.FrogTypes;
import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.quests.QuestType;
import me.Minestor.frogvasion.util.entity.GuideUnlocked;
import me.Minestor.frogvasion.util.entity.IBookProvider;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

@Environment(EnvType.CLIENT)
public class ClientReceiver {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_TRAP, (client, handler, buf, responseSender) -> {
            int amount = buf.readByte();
            BlockPos pos = buf.readBlockPos();
            FrogTrapRenderer.map.put(pos.toString(), amount);
        });

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_QUEST_S2C, (client, handler, buf, responseSender) -> {
            int amount = buf.readByte();
            int originalAmount = buf.readByte();
            String type = buf.readString();
            String item = buf.readString();
            String block = buf.readString();
            String target = buf.readString();
            boolean completed = buf.readBoolean();
            boolean active = buf.readBoolean();

            if(type.equalsIgnoreCase("craft")) {
                type = "Empty";
            }
            ExtraQuestData data = ExtraQuestData.of(amount, originalAmount, QuestType.valueOf(type),
                    Registries.ITEM.get(Identifier.tryParse(item)), Registries.BLOCK.get(Identifier.tryParse(block)),
                    Registries.ENTITY_TYPE.get(Identifier.tryParse(target)), completed);
            data.setActive(active);
            Quest quest = new Quest(data);

            QuestDataManager.setQuest((IEntityDataSaver) client.player, quest);
        });

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.FLORADIC_S2C, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            int progress = buf.readInt();
            int size = buf.readInt();

            DefaultedList<ItemStack> inv = DefaultedList.ofSize(size, ItemStack.EMPTY);
            for (int i = 0; i < size; i++) {
                inv.set(i, buf.readItemStack());
            }

            if(client.player.getWorld().getBlockEntity(pos) instanceof FloradicAltarBlockEntity be) {
                be.setProgress(progress);
                be.setInv(inv);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.GRENADE_EXPLOSION, (client, handler, buf, responseSender) -> {
            double x = buf.readDouble(), y = buf.readDouble(), z = buf.readDouble();
            ItemStack stack = buf.readItemStack();
            NbtCompound nbt = stack.getNbt();

            NbtCompound nbtGrenade = nbt.getCompound("grenade");
            for (int i = 1; i < 4; i++) {
                OrchidType type = OrchidType.valueOf(nbtGrenade.getString("orchid_type_" + i));
                OrchidIntensity intensity = OrchidIntensity.valueOf(nbtGrenade.getString("orchid_intensity_" + i));
                float r = intensity.getRange();
                Box box = new Box(-r + x, -r + y, -r + z, r + x, r + y, r + z);

                for (int x1 = 0; x1 < 3; x1++) {
                    for (int y1 = 0; y1 < 3; y1++) {
                        for (int z1 = 0; z1 < 3; z1++) {
                            client.player.getWorld().addParticle(type.getParticle(intensity.getPower()), box.minX + (x1 + 1) / 1.5 * r, box.minY + (y1 + 1) / 1.5 * r, box.minZ + (z1 + 1) / 1.5 * r,0,0,0);
                        }
                    }
                }
            }
            client.player.getWorld().playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1f, true);
        });
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.ALTAR_MANUAL_S2C, (client, handler, buf, responseSender) ->
                client.execute(() -> client.setScreen(((IBookProvider)client.player).frogvasion$getAltarScreen())));
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.GUIDE_TO_FROGS_S2C, (client, handler, buf, responseSender) ->
                client.execute(() -> client.setScreen(((IBookProvider)client.player).frogvasion$getGuide())));
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_GUIDE, (client, handler, buf, responseSender) -> {
            if(client.player != null){
                for (FrogTypes type : FrogTypes.values()) {
                    GuideUnlocked.modifyUnlocked((IEntityDataSaver) client.player, type, buf.readBoolean());
                }
            }
        });
    }
}
