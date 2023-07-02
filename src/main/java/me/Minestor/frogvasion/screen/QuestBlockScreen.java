package me.Minestor.frogvasion.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.UpdateQuestPacket;
import me.Minestor.frogvasion.quests.ExtraQuestData;
import me.Minestor.frogvasion.quests.Quest;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import me.Minestor.frogvasion.util.quest.QuestDataManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class QuestBlockScreen extends HandledScreen<QuestBlockScreenHandler> {
    private Quest quest;
    private static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID,"textures/gui/quest_block_gui.png");
    private Text objective;
    private Text objectiveItem;
    private Text objectiveBlock;
    private Text objectiveEntity;
    private static final Text objectiveEnchant = Text.translatable("text.quest.enchant");
    private int amountRefreshed =0;
    private final List<IconButtonWidget> widgets = new ArrayList<>();
    private ExtraQuestData data;
    public QuestBlockScreen(QuestBlockScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        ClientPlayNetworking.send(ModMessages.REQUEST_QUEST, PacketByteBufs.create());

        this.quest = getQuest(inv.player);
        if(quest == null) {
            quest = new Quest(ExtraQuestData.random());
        }
        objective = Text.translatable("text.quest.headline", quest.getData().getType() , quest.getData().getAmount());
        objectiveItem = quest.getData().getItem().getName();
        objectiveBlock = quest.getData().getBlock().getName();
        objectiveEntity = quest.getData().getTarget().getName();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        RenderSystem.setShader(GameRenderer::getRenderTypeTextProgram);
        int cx = width / 2;
        int cy = height / 2;

        textRenderer.draw("Your objective: ", cx - (float) textRenderer.getWidth("Your objective: ") / 2, cy - (float) backgroundHeight /3 -10, 3618615, false,
                context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
        textRenderer.draw(objective, cx - (float) textRenderer.getWidth(objective) / 2, cy - (float) backgroundHeight /3 +5, 3618615,false,
                context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
        switch (quest.getData().getType()) {
            case Collect, Craft -> textRenderer.draw(objectiveItem, cx - (float) textRenderer.getWidth(objectiveItem) / 2, cy - (float) backgroundHeight /3 + 20, 3618615, false,
                    context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
            case Kill -> textRenderer.draw(objectiveEntity, cx - (float) textRenderer.getWidth(objectiveEntity) / 2, cy - (float) backgroundHeight /3 + 20, 3618615, false,
                    context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
            case Mine -> textRenderer.draw(objectiveBlock, cx - (float) textRenderer.getWidth(objectiveBlock) / 2, cy - (float) backgroundHeight /3 + 20, 3618615, false,
                    context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
            case Enchant -> textRenderer.draw(objectiveEnchant, cx - (float) textRenderer.getWidth(objectiveEnchant) / 2, cy - (float) backgroundHeight /3 + 20, 3618615, false,
                    context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 15);
        }
    }

    @Override
    protected void init() {
        super.init();
        DoneButtonWidget done = new DoneButtonWidget(width / 2 + backgroundWidth/2 - 30,  height / 2 - backgroundHeight /3 + 30);
        CancelButtonWidget cancel = new CancelButtonWidget(width / 2 + backgroundWidth/2 - 55,  height / 2 - backgroundHeight /3 + 30);

        this.addDrawableChild(done);
        this.addDrawableChild(cancel);

        widgets.add(done);
        widgets.add(cancel);

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Nullable
    public Quest getQuest(PlayerEntity player) {
        data = QuestDataManager.getData((IEntityDataSaver) player);
        return data == null || data.isEmpty() ? null : QuestDataManager.getQuest((IEntityDataSaver) player);
    }

    public void refresh() {
        if(amountRefreshed >= 5) {
            this.client.player.closeHandledScreen();

            amountRefreshed = 0;
        }
        amountRefreshed++;

        this.quest = getQuest(client.player);
        if(quest == null) {
            quest = new Quest(ExtraQuestData.random());
        }

        objective = Text.translatable("text.quest.headline", quest.getData().getType() , quest.getData().getAmount());
        objectiveItem = quest.getData().getItem().getName();
        objectiveBlock = quest.getData().getBlock().getName();
        objectiveEntity = quest.getData().getTarget().getName();

        for(IconButtonWidget i : this.widgets) {
            i.markDirty();
        }
    }
    @Environment(EnvType.CLIENT)
    public class DoneButtonWidget extends IconButtonWidget {
        public DoneButtonWidget(int x, int y) {
            super(x, y, 90, 220, ScreenTexts.DONE);
            init();
        }

        public void onPress() {
            if(!this.disabled){
                QuestDataManager.setQuest((IEntityDataSaver) client.player, quest);

                ClientPlayNetworking.send(ModMessages.UPDATE_QUEST_C2S, UpdateQuestPacket.createUpdate(quest));
                client.player.closeHandledScreen();
            }
        }
        public void markDirty() {
            init();
        }
        void init() {
            data = QuestDataManager.getData((IEntityDataSaver)client.player);
            this.active = true;
            boolean bl = data != null && (!data.isActive() || data.isEmpty());

            this.setEnabled(data == null || bl);
            Tooltip accept = Tooltip.of(Text.literal("§aClick to accept this quest!" +
                    "\n§4§lDURING A QUEST, NO ITEMS FROM THE SPECIFIED BLOCK OR MOB WILL BE DROPPED. SAME WITH CRAFTING THE QUEST ITEM"));
            Tooltip accepted = Tooltip.of(Text.literal("You haven't finished this quest yet!"));

            this.setTooltip(bl ? accept : accepted);
        }
    }
    @Environment(EnvType.CLIENT)
    public class CancelButtonWidget extends IconButtonWidget {
        public CancelButtonWidget(int x, int y) {
            super(x, y, 112, 220, ScreenTexts.CANCEL);

            this.setTooltip(Tooltip.of(Text.literal("§aClick to clear this quest!\n§4§lYOU WILL NOT GET ANY REWARDS FOR CANCELLING")));
        }

        public void onPress() {
            quest = new Quest(ExtraQuestData.empty());
            QuestDataManager.setQuest((IEntityDataSaver) client.player, quest);

            ClientPlayNetworking.send(ModMessages.UPDATE_QUEST_C2S, UpdateQuestPacket.createUpdate(quest));
            refresh();
        }

        @Override
        public void markDirty() {}
    }
    @Environment(EnvType.CLIENT)
    public abstract static class IconButtonWidget extends BaseButtonWidget {
        private final int u;
        private final int v;

        protected IconButtonWidget(int i, int j, int k, int l, Text text) {
            super(i, j, text);
            this.u = k;
            this.v = l;
        }

        @Override
        protected void renderExtra(DrawContext context) {
            context.drawTexture(QuestBlockScreen.TEXTURE, this.getX() + 2, this.getY() + 2, u, v, this.width, this.height);
        }

        public abstract void markDirty();
    }
    @Environment(EnvType.CLIENT)
    public abstract static class BaseButtonWidget extends PressableWidget {
        public boolean disabled;
        protected BaseButtonWidget(int x, int y, Text message) {
            super(x, y, 22, 22, message);
        }

        @Override
        protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, QuestBlockScreen.TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int u = 0;
            if (!this.active) {
                u += this.width * 2;
            } else if (this.disabled) {
                u += this.width;
            } else if (this.isHovered()) {
                u += this.width * 3;
            }
            context.drawTexture(QuestBlockScreen.TEXTURE, this.getX(), this.getY(), u, 219, this.width, this.height);
            this.renderExtra(context);
        }

        protected abstract void renderExtra(DrawContext context);

        public void setEnabled(boolean enabled) {
            this.disabled = !enabled;
        }

        public void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
