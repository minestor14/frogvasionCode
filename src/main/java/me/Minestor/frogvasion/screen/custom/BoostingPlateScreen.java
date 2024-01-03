package me.Minestor.frogvasion.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.networking.ModMessages;
import me.Minestor.frogvasion.networking.packets.ModPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class BoostingPlateScreen extends HandledScreen<BoostingPlateScreenHandler> {
    ClientPlayerEntity player;
    TicTacToeTile button1;
    TicTacToeTile button2;
    TicTacToeTile button3;
    TicTacToeTile button4;
    TicTacToeTile button5;
    TicTacToeTile button6;
    TicTacToeTile button7;
    TicTacToeTile button8;
    TicTacToeTile button9;
    List<TicTacToeTile> tiles;
    private static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID,"textures/gui/boosting_plate.png");
    boolean playerTurn = true;
    public BoostingPlateScreen(BoostingPlateScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        this.player = (ClientPlayerEntity) inv.player;
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        button1 = this.addDrawableChild(new TicTacToeTile(x + 55, y + 7,1));
        button2 = this.addDrawableChild(new TicTacToeTile(x + 55, y + 29,2));
        button3 = this.addDrawableChild(new TicTacToeTile(x + 55, y + 51,3));
        button4 = this.addDrawableChild(new TicTacToeTile(x + 77, y + 7,4));
        button5 = this.addDrawableChild(new TicTacToeTile(x + 77, y + 29,5));
        button6 = this.addDrawableChild(new TicTacToeTile(x + 77, y + 51,6));
        button7 = this.addDrawableChild(new TicTacToeTile(x + 99, y + 7,7));
        button8 = this.addDrawableChild(new TicTacToeTile(x + 99, y + 29,8));
        button9 = this.addDrawableChild(new TicTacToeTile(x + 99, y + 51,9));
        tiles = List.of(button1, button2, button3, button4, button5, button6, button7, button8, button9);
    }

    @Override
    protected void handledScreenTick() {
        int i = 0;
        for (TicTacToeTile tile : tiles) {
            tile.update(TicTacToeType.getByValue(handler.getPd().get(i)));
            i++;
        }
        super.handledScreenTick();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int y = (height - backgroundHeight) / 2 + 75;
        TextRenderer renderer = client.textRenderer;
        Text text = getText();
        context.drawText(renderer, text, (width - renderer.getWidth(text)) / 2, y, 0, false);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @NotNull
    private Text getText() {
        Text text;
        if(getGameStatus() == TicTacToeType.EMPTY) {
            if(playerTurn) {
                text = Text.translatable("text.frogvasion.boosting_plate.player");
            } else {
                text = Text.translatable("text.frogvasion.boosting_plate.ai");
            }
        } else {
            if(getGameStatus() == TicTacToeType.DRAW) text = Text.translatable("text.frogvasion.boosting_plate.draw");
            else text = Text.translatable("text.frogvasion.boosting_plate.win", getGameStatus().name());
        }
        return text;
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


    public TicTacToeType getGameStatus() {
        for (int i = 0; i < 3; i++) {
            if(areSameType(tiles.get(i * 3), tiles.get(i*3 +1), tiles.get(i*3 +2))) {//horizontal
                return tiles.get(i*3).type;
            }
            if(areSameType(tiles.get(i), tiles.get(i + 3), tiles.get(i + 6))) {//vertical
                return tiles.get(i).type;
            }
        }
        if(areSameType(tiles.get(0), tiles.get(4), tiles.get(8)) || areSameType(tiles.get(2), tiles.get(4), tiles.get(6))) //diagonal
            return tiles.get(4).type;

        for (int i = 0; i < 9; i++) {
            if(tiles.get(i).type == TicTacToeType.EMPTY)
                return TicTacToeType.EMPTY;
        } //if no tile is empty, it is a draw
        return TicTacToeType.DRAW;
    }
    public List<TicTacToeTile> getWinningTiles() {
        if(getGameStatus() == TicTacToeType.EMPTY || getGameStatus() == TicTacToeType.DRAW) return List.of();
        for (int i = 0; i < 3; i++) {
            if(areSameType(tiles.get(i * 3), tiles.get(i*3 +1), tiles.get(i*3 +2))) {//horizontal
                return List.of(tiles.get(i * 3), tiles.get(i * 3 +1), tiles.get(i * 3 +2));
            }
            if(areSameType(tiles.get(i), tiles.get(i + 3), tiles.get(i + 6))) {//vertical
                return List.of(tiles.get(i), tiles.get(i + 3), tiles.get(i + 6));
            }
        }
        if(areSameType(tiles.get(0), tiles.get(4), tiles.get(8))) //diagonal
            return List.of(tiles.get(0), tiles.get(4), tiles.get(8));
        if(areSameType(tiles.get(2), tiles.get(4), tiles.get(6))) //diagonal
            return List.of(tiles.get(2), tiles.get(4), tiles.get(6));
        return List.of();
    }

    public static boolean areSameType(TicTacToeTile... tiles) {
        TicTacToeType firstType = tiles[0].type;
        for (TicTacToeTile tile : tiles) {
            if(tile.isEmpty()) return false;
            if(tile.type != firstType) return false;
        }
        return true;
    }


    public class TicTacToeTile extends QuestBlockScreen.BaseButtonWidget {
        TicTacToeType type = TicTacToeType.EMPTY;
        int index;
        boolean isOfWinning = false;
        public TicTacToeTile(int x, int y, int index) {
            super(x, y, Text.empty());
            this.index = index;
        }
        void update(TicTacToeType type) {
            if(this.type != type && this.type != TicTacToeType.X) {
                pressed(false);
                this.type = type;
            }
        }
        @Override
        protected void renderExtra(DrawContext context) {
            if(this.type != TicTacToeType.EMPTY) {
                context.drawTexture(TEXTURE, this.getX() + 2, this.getY(), type == TicTacToeType.X ? 112 : 134, 220, this.width, this.height);
            }
        }
        public boolean isEmpty() {
            return type == TicTacToeType.EMPTY;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public void onPress() {
            if(this.type == TicTacToeType.EMPTY && playerTurn){
                pressed(true);
                ClientPlayNetworking.send(ModMessages.UPDATE_TTT, ModPackets.tttPlayerMove(handler.syncId, this.getIndex()));
            }
        }
        public void pressed(boolean player) {
            if(!isEmpty()) return;
            if(player) {
                this.type = TicTacToeType.X;
            } else {
                this.type = TicTacToeType.O;
            }
            this.disabled = true;
            BoostingPlateScreen.this.playerTurn = !player;

            if(BoostingPlateScreen.this.getGameStatus() != TicTacToeType.EMPTY) {
                for (TicTacToeTile tile : tiles) {
                    tile.active = false;
                }
                for (TicTacToeTile tile : getWinningTiles()) {
                    tile.isOfWinning = true;
                }
            }
        }
        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int u = 0;
            if(this.isOfWinning) {
                u += this.width * 4;
            } else if (!this.active) {
                u += this.width * 2;
            } else if (this.disabled) {
                u += this.width;
            } else if (this.isHovered() && BoostingPlateScreen.this.playerTurn) {
                u += this.width * 3;
            }
            context.drawTexture(TEXTURE, this.getX(), this.getY(), u, 219, this.width, this.height);
            this.renderExtra(context);
        }
    }

}
