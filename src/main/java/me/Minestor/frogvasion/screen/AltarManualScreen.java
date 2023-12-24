package me.Minestor.frogvasion.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.recipe.FloradicAltarRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class AltarManualScreen extends Screen {
    private final ClientPlayerEntity player;
    private static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/gui/altar_manual.png");
    private final List<RecipeButtonWidget> RECIPES = new ArrayList<>();
    final int backgroundWidth = 192;
    final int backgroundHeight = 192;
    private PageTurnWidget nextPage;
    private PageTurnWidget previousPage;
    RecipeButtonWidget hasSelected;
    int xOffset;
    int page = 0;
    final int pages;
    private final static Text altarT = Text.translatable("block.frogvasion.floradic_altar");
    public AltarManualScreen(ClientPlayerEntity player) {
        super(NarratorManager.EMPTY);
        this.player = player;
        pages = (int) Math.floor((double) player.getWorld().getRecipeManager().listAllOfType(FloradicAltarRecipe.Type.INSTANCE).size() / 8) + 2;
    }

    @Override
    protected void init() {
        super.init();
        World world = player.getWorld();
        int x = this.width / 2 - 55, y = (height - backgroundHeight) / 2 + 10, i = 0;

        for (RecipeEntry<FloradicAltarRecipe> recipe : world.getRecipeManager().listAllOfType(FloradicAltarRecipe.Type.INSTANCE)) {
            RecipeButtonWidget widget = new RecipeButtonWidget(x, y, i, recipe.value(), (int) (double) (i / 8) + 2);
            RECIPES.add(widget);
            this.addDrawableChild(widget);
            widget.setActive(false);
            y += 20;
            i++;
            if(y >= 20*8 + (height - backgroundHeight) / 2 + 10) y = (height - backgroundHeight) / 2 + 10;
        }

        nextPage = this.addDrawableChild(new PageTurnWidget((width - backgroundWidth) / 2 + 116, (height - backgroundHeight) / 2, true, (button) -> this.openNextPage(), true));
        previousPage = this.addDrawableChild(new PageTurnWidget((width - backgroundWidth) / 2 + 43, (height - backgroundHeight) / 2, false, (button) -> this.openPreviousPage(), true));
    }
    @Override
    protected void initTabNavigation() {}

    private void adjustCoords(int x) {
        for (RecipeButtonWidget widget: RECIPES) {
            widget.setX(widget.getX() + x);
        }
        nextPage.setX(nextPage.getX() + x);
        previousPage.setX(previousPage.getX() + x);

        xOffset += x;
    }
    public void openNextPage() {
        page = Math.min(page + 1, pages);
        for (RecipeButtonWidget widget: RECIPES) {
            widget.setActive(widget.onPage == page);
        }
        this.setFocused(null);
        if(page == 0 || page == 1) hasSelected = null;
    }
    public void openPreviousPage() {
        page = Math.max(page - 1, 0);
        for (RecipeButtonWidget widget : RECIPES) {
            widget.setActive(widget.onPage == page);
        }
        this.setFocused(null);
        if(page == 0 || page == 1) hasSelected = null;
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        TextRenderer renderer = client.inGameHud.getTextRenderer();
        if(hasSelected != null) {
            int i = 2;
            int x = (width - backgroundWidth) / 2;
            int y = (height - backgroundHeight) / 2;

            for (Text text: hasSelected.getIngredients()) {
                context.drawText(renderer, text, x + backgroundWidth -24 + xOffset, y + 10 + i, 0, false);
                i += 8;
            }
        }

        int x = this.width / 2 - 55, y = (height - backgroundHeight) / 2 + 10;
        if(page == 0) {
            context.drawText(renderer, Text.translatable("text.frogvasion.manual_introduction", player.getName()), x, y, 0, false);
            y += 8;

            for (OrderedText text: renderer.wrapLines(Text.translatable("text.frogvasion.screen.altar_explained", altarT), 110)) {
                context.drawText(renderer, text, x, y, 0, false);
                y += 8;
            }
        } else if (page == 1) {
            for (OrderedText text: renderer.wrapLines(Text.translatable("text.frogvasion.screen.grenade_explained", altarT, Text.translatable("item.frogvasion.orchid_grenade_shell"), Text.translatable("item.frogvasion.orchid_grenade")), 110)) {
                context.drawText(renderer, text, x, y, 0, false);
                y += 8;
            }
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int length = hasSelected != null ? x + backgroundWidth - 20 + hasSelected.getLengthIngredientList() : 0;

        if(hasSelected != null && length > width) {
            matrices.translate(width - length, 0, 0);
            if(xOffset == 0) adjustCoords(width - length);
        } else if (hasSelected != null && xOffset != 0) {
            adjustCoords(-xOffset);
        }

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        if(hasSelected != null){
            context.drawTexture(TEXTURE, x + backgroundWidth - 26, y + 10, 230 - hasSelected.getLengthIngredientList(), 208, hasSelected.getLengthIngredientList() + 6, 255-208);
        }
        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public class RecipeButtonWidget extends ButtonWidget {
        private static final Identifier BUTTON = new Identifier(Frogvasion.MOD_ID, "textures/gui/widgets.png");
        final int index;
        final FloradicAltarRecipe recipe;
        int onPage;
        protected RecipeButtonWidget(int x, int y, int index, FloradicAltarRecipe recipe, int onPage) {
            super(x, y, 110, 20, ScreenTexts.EMPTY, button -> hasSelected = RECIPES.get(index), Supplier::get);
            this.index = index;
            this.recipe = recipe;
            this.active = true;
            this.onPage = onPage;
        }
        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            if(active){
                context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.setShaderTexture(0, BUTTON);
                context.drawTexture(BUTTON, getX(), getY(), 0, this.isSelected() ? 20 : 0, this.width, this.height, 110,40);
                context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                TextRenderer renderer = client.inGameHud.getTextRenderer();
                this.drawMessage(context, renderer, 16777215 | MathHelper.ceil(this.alpha * 255.0F) << 24);

                Text name = Text.literal(recipe.getOutput().getCount() + " ").append(recipe.getOutput().getName());
                int j = 0;
                for(OrderedText text : renderer.wrapLines(name, 100)){
                    if(renderer.wrapLines(name, 100).size() > 1) {
                        context.drawText(renderer, text, this.getX() + (110 - renderer.getWidth(text)) / 2, this.getY() + 2 + 8*j, 0, false);
                        j++;
                    } else {
                        context.drawText(renderer, text, this.getX() + (110 - renderer.getWidth(text)) / 2, this.getY() + 6, 0, false);
                    }
                }
            }
        }

        public List<Text> getIngredients() {
            List<Text> text = new ArrayList<>();
            text.add(Text.translatable("text.frogvasion.screen.altar_manual", altarT).formatted(Formatting.AQUA));
            text.add(getNameToShow(recipe.getIngredients().get(0)));
            text.add(getNameToShow(recipe.getIngredients().get(1)));
            text.add(getNameToShow(recipe.getIngredients().get(2)));
            text.add(getNameToShow(recipe.getIngredients().get(3)));

            return text;
        }
        public int getLengthIngredientList() {
            int a = 0;
            for (Text text: getIngredients()) {
                if(client.inGameHud.getTextRenderer().getWidth(text) > a) a = client.inGameHud.getTextRenderer().getWidth(text);
            }
            return a;
        }

        private static Text getNameToShow(Ingredient ingr) {
            ItemStack[] stacks = ingr.getMatchingStacks();
            int l = stacks.length;

            if(l == 0) return ItemStack.EMPTY.getName();
            if(l == 1) return stacks[0].getName();

            long timeSeconds = Util.getMeasuringTimeMs() / 1000L;
            return stacks[(int) Math.floor(timeSeconds % l)].getName();
        }
    }
}