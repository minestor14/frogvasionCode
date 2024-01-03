package me.Minestor.frogvasion.quests;

import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import me.Minestor.frogvasion.util.quest.QuestUtil;
import me.Minestor.frogvasion.util.quest.ServerQuestProgression;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ExtraQuestData {
    private int amount;
    private EntityType<? extends Entity> target;
    private Item item;
    private Block block;
    private QuestType type;
    private boolean completed;
    private int originalAmount;
    private boolean active;
    private static final Random rand = new Random();
    public static final ExtraQuestData EMPTY = empty();
    private ExtraQuestData() {
        completed = false;
        active = false;
    }
    public static ExtraQuestData randomOf(QuestType type) {
        return switch (type) {
            case Collect -> collect(QuestUtil.getRandomCollectable(), rand.nextInt(27) + 6); //up to 32, minimal 5
            case Kill -> kill(QuestUtil.getRandomTargetable(), rand.nextInt(5) + 6);
            case Mine -> mine(QuestUtil.getRandomMineable(), rand.nextInt(45) + 6);
            case Enchant -> enchant(rand.nextInt(3) + 1); // 1 up to 3
            case Empty -> empty();
        };
    }
    public static ExtraQuestData random() {
        int a = rand.nextInt(QuestType.values().length - 1); //not QuestType.Empty
        return randomOf(QuestType.values()[a]);
    }
    public static ExtraQuestData collect(Item item, int amount) {
        ExtraQuestData data = new ExtraQuestData();
        data.setType(QuestType.Collect);
        data.setItem(item);
        data.setAmount(amount);
        data.setOriginalAmount(amount);
        data.fillEmpty(true);

        return data;
    }
    public static ExtraQuestData kill(EntityType<? extends Entity> entityType, int amount) {
        ExtraQuestData data = new ExtraQuestData();
        data.setType(QuestType.Kill);
        data.setTarget(entityType);
        data.setAmount(amount);
        data.setOriginalAmount(amount);
        data.fillEmpty(true);

        return data;
    }
    public static ExtraQuestData mine(Block block, int amount) {
        ExtraQuestData data = new ExtraQuestData();
        data.setType(QuestType.Mine);
        data.setBlock(block);
        data.setAmount(amount);
        data.setOriginalAmount(amount);
        data.fillEmpty(true);

        return data;
    }
    public static ExtraQuestData enchant(int amount) {
        ExtraQuestData data = new ExtraQuestData();
        data.setType(QuestType.Enchant);
        data.setAmount(amount);
        data.setOriginalAmount(amount);
        data.fillEmpty(true);

        return data;
    }
    public static ExtraQuestData empty() {
        ExtraQuestData data = new ExtraQuestData();

        data.setType(QuestType.Empty);
        data.setAmount(0);
        data.setOriginalAmount(0);
        data.setItem(ItemStack.EMPTY.getItem());
        data.setBlock(Blocks.AIR);
        data.setTarget(EntityType.MARKER);
        data.setCompleted(false);
        data.setActive(false);

        return data;
    }
    public NbtCompound getAsNBT() {
        this.fillEmpty(this.active);
        NbtCompound nbt = new NbtCompound();

        nbt.putInt("quest.amount", this.amount);
        nbt.putInt("quest.original_amount", this.originalAmount);
        nbt.putString("quest.type", this.type.toString());
        nbt.putString("quest.item", getItemString());
        nbt.putString("quest.block", getBlockString());
        nbt.putString("quest.target", getTargetString());
        nbt.putBoolean("quest.completed", this.completed);
        nbt.putBoolean("quest.active", this.active);

        return nbt;
    }
    @Nullable
    public static ExtraQuestData getFromNBT(NbtCompound nbt) {
        if(!nbt.contains("quest.amount")) {
            return null;
        }
        ExtraQuestData data = new ExtraQuestData();
        if(nbt.getString("quest.type").equalsIgnoreCase("craft")) {
            return EMPTY;
        }

        data.setAmount(nbt.getInt("quest.amount"));
        data.setOriginalAmount(nbt.getInt("quest.original_amount"));
        data.setType(QuestType.valueOf(nbt.getString("quest.type")));
        data.setItem(Registries.ITEM.get(Identifier.tryParse(nbt.getString("quest.item"))));
        data.setBlock(Registries.BLOCK.get(Identifier.tryParse(nbt.getString("quest.block"))));
        data.setTarget(Registries.ENTITY_TYPE.get(Identifier.tryParse(nbt.getString("quest.target"))));
        data.setCompleted(nbt.getBoolean("quest.completed"));
        data.setActive(nbt.getBoolean("quest.active"));

        return data;
    }
    public void copyFrom(ExtraQuestData data) {
        this.setType(data.getType());
        this.setAmount(data.getAmount());
        this.setBlock(data.getBlock());
        this.setItem(data.getItem());
        this.setTarget(data.getTarget());
        this.setOriginalAmount(data.getOriginalAmount());
        this.setCompleted(data.isCompleted());
        this.setActive(data.isActive());
    }
    public static ExtraQuestData of(int amount, QuestType type, Item item, Block block, EntityType<? extends Entity> target, boolean completed) {
        ExtraQuestData data = new ExtraQuestData();

        data.setAmount(amount);
        data.setOriginalAmount(amount);
        data.setType(type);
        data.setItem(item);
        data.setBlock(block);
        data.setTarget(target);
        data.setCompleted(completed);

        return data;
    }
    public static ExtraQuestData of(int amount, int originalAmount, QuestType type, Item item, Block block, EntityType<? extends Entity> target, boolean completed) {
        ExtraQuestData data = new ExtraQuestData();

        data.setAmount(amount);
        data.setOriginalAmount(originalAmount);
        data.setType(type);
        data.setItem(item);
        data.setBlock(block);
        data.setTarget(target);
        data.setCompleted(completed);

        return data;
    }
    private void fillEmpty(boolean active) {
        if(this.getType() == null) {
            ExtraQuestData data = random();
            copyFrom(data);
            return;
        }

        if(this.getAmount() == 0) this.setAmount(-1);
        if(this.getItem() == null) this.setItem(ItemStack.EMPTY.getItem());
        if(this.getBlock() == null) this.setBlock(Blocks.AIR);
        if(this.getTarget() == null) this.setTarget(EntityType.MARKER);
        this.active = active;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;

        if(this.completed)  {
            this.completed = false;
            this.setEmpty();

            this.fillEmpty(false);
        }
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public EntityType<? extends Entity> getTarget() {
        return target;
    }
    public void setTarget(EntityType<? extends Entity> target) {
        this.target = target;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public Block getBlock() {
        return block;
    }
    public void setBlock(Block block) {
        this.block = block;
    }
    public QuestType getType() {
        return type;
    }
    public void setType(QuestType type) {
        this.type = type;
    }
    public int getOriginalAmount() {
        return originalAmount;
    }
    public void setOriginalAmount(int originalAmount) {
        this.originalAmount = originalAmount;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public void decreaseAmount(int times, ServerPlayerEntity player) {
        if(!completed) {
            this.setAmount(Math.max(0, amount - times));
        }
        if(getAmount() == 0) {
            ServerQuestProgression.IQuestCompletionEvent.COMPLETION
                    .invoker().interact(player, QuestDataManager.getQuest((IEntityDataSaver) player));
            setCompleted(true);
        }
    }
    public String getItemString() {
        return Registries.ITEM.getId(this.item).toString();
    }
    public String getBlockString() {
        return Registries.BLOCK.getId(this.block).toString();
    }
    public String getTargetString() {
        return Registries.ENTITY_TYPE.getId(this.target).toString();
    }
    public boolean isEmpty() {
        boolean b1 = amount <= 0;
        boolean b2 = item == null || item == ItemStack.EMPTY.getItem();
        boolean b3 = block == null || block == Blocks.AIR;
        boolean b4 = target == null || target == EntityType.MARKER;

        return  b1 && b2 && b3 && b4 || this.getType() == QuestType.Empty;
    }
    public void setEmpty() {
        setAmount(0);
        setItem(null);
        setBlock(null);
        setTarget(null);
        setCompleted(false);
        setActive(false);
        setType(QuestType.Empty);
    }
}