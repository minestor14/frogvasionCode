package me.Minestor.frogvasion.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.entities.ModEntities;
import me.Minestor.frogvasion.entities.custom.*;
import me.Minestor.frogvasion.util.entity.GuideUnlocked;
import me.Minestor.frogvasion.util.entity.IEntityDataSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class GuideToFrogsScreen extends Screen {
    public ClientPlayerEntity player;
    private static final Identifier TEXTURE = new Identifier(Frogvasion.MOD_ID, "textures/gui/guide_to_frogs.png");
    private static final Identifier NOT_YET_UNLOCKED = new Identifier(Frogvasion.MOD_ID, "textures/gui/not_yet_unlocked.png");
    static final int backgroundWidth = 192;
    static final int backgroundHeight = 192;
    private PageTurnWidget nextPage;
    private PageTurnWidget previousPage;
    private ButtonWidget toggleInfused;
    boolean infused = false;
    int page = 0;
    final static int pages = 10;
    World world;
    SoldierFrog soldier;
    ArmedFrog armed;
    BossSoldierFrog boss_soldier;
    EnderFrog ender;
    ExplosiveFrog explosive;
    GrapplingFrog grappling;
    GrowingFrog growing;
    IceFrog ice;
    TadpoleRocket rocket;
    static final Vector3f vec = new Vector3f();
    List<ModFrog> entities;

    public GuideToFrogsScreen(ClientPlayerEntity player) {
        super(NarratorManager.EMPTY);
        this.player = player;
        this.world = player.getWorld();
        soldier = new SoldierFrog(ModEntities.SOLDIER_FROG_ENTITY, world);
        armed = new ArmedFrog(ModEntities.ARMED_FROG_ENTITY, world);
        boss_soldier = new BossSoldierFrog(ModEntities.BOSS_SOLDIER_FROG_ENTITY, world);
        ender = new EnderFrog(ModEntities.ENDER_FROG_ENTITY, world);
        explosive = new ExplosiveFrog(ModEntities.EXPLOSIVE_FROG_ENTITY, world);
        grappling = new GrapplingFrog(ModEntities.GRAPPLING_FROG_ENTITY, world);
        growing = new GrowingFrog(ModEntities.GROWING_FROG_ENTITY, world);
        ice = new IceFrog(ModEntities.ICE_FROG_ENTITY, world);
        rocket = new TadpoleRocket(ModEntities.TADPOLE_ROCKET_ENTITY, world);
        entities = List.of(soldier, boss_soldier, armed, ender, explosive, grappling, growing, rocket, ice);

        for(ModFrog frog : entities) {
            frog.bodyYaw = 210.0F;
            frog.setPitch(25.0F);
            frog.headYaw = frog.getYaw();
            frog.prevHeadYaw = frog.getYaw();
        }
    }
    @Override
    protected void initTabNavigation() {}

    @Override
    protected void init() {
        super.init();
        nextPage = this.addDrawableChild(new PageTurnWidget((width - backgroundWidth) / 2 + 116, (height - backgroundHeight) / 2, true, (button) -> this.openNextPage(), true));
        previousPage = this.addDrawableChild(new PageTurnWidget((width - backgroundWidth) / 2 + 43, (height - backgroundHeight) / 2, false, (button) -> this.openPreviousPage(), true));
        toggleInfused = this.addDrawableChild(ButtonWidget.builder(Text.translatable("text.frogvasion.guide.toggle_infused", infused ? "On" : "Off"), button -> {
            infused = !infused;
            for(ModFrog frog : entities) {
                frog.setInfused(infused);
            }
            button.setMessage(Text.translatable("text.frogvasion.guide.toggle_infused", infused ? "On" : "Off"));
        }).dimensions(this.width / 2 - 55, (height - backgroundHeight) / 2 + 150, 110, 20).build());
        toggleInfused.visible = false;
        previousPage.visible = false;
    }
    public void openNextPage() {
        page = Math.min(page + 1, pages);
        this.setFocused(null);
        if(page == pages) {
            nextPage.visible = false;
            toggleInfused.visible = true;
        }
        previousPage.visible = true;
    }
    public void openPreviousPage() {
        page = Math.max(page - 1, 0);
        this.setFocused(null);
        if(page == 0) previousPage.visible = false;
        nextPage.visible = true;
        toggleInfused.visible = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        TextRenderer renderer = client.inGameHud.getTextRenderer();

        int x = this.width / 2 - 55, y = (height - backgroundHeight) / 2 + 18;
        final int cx = width / 2;
        final int cy = height / 2;
        Quaternionf rot = new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
        if(page == 0) {
            for (OrderedText text: renderer.wrapLines(Text.translatable("text.frogvasion.screen.guide_explained", Text.translatable("item.frogvasion.herpetologist_jar")), 110)) {
                context.drawText(renderer, text, x, y, 0, false);
                y += 8;
            }
        } else if (page < 10){
            if(GuideUnlocked.hasType((IEntityDataSaver) player, FrogTypes.getById(page))){
                MutableText frogName = FrogTypes.getById(page).getTranslation().formatted(Formatting.UNDERLINE);
                context.drawText(renderer, frogName, (width - renderer.getWidth(frogName)) / 2, y, 0, false);
                InventoryScreen.drawEntity(context, cx, cy - 32, page == 8 ? 80 : 60, vec, rot, null, entities.get(page - 1));

                y = height / 2 - 12;
                for (OrderedText text : renderer.wrapLines(Text.translatable("text.frogvasion.guide." + page, frogName, FrogTypes.SOLDIER.getTranslation(), FrogTypes.TADPOLE_ROCKET.getTranslation(), Text.translatable("item.minecraft.ender_pearl"),
                        Text.translatable("item.minecraft.potion.effect.improver_potion"), Text.translatable("item.frogvasion.ice_spike")), 110)) {
                    context.drawText(renderer, text, x, y, 0, false);
                    y += 8;
                }
            } else {
                MutableText frogName = Text.literal("????????").formatted(Formatting.UNDERLINE);
                context.drawText(renderer, frogName, (width - renderer.getWidth(frogName)) / 2, y, 0, false);

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, NOT_YET_UNLOCKED);

                context.getMatrices().push();
                context.drawTexture(NOT_YET_UNLOCKED, cx - 24, cy - 60, 50, 0,0,48,48,48,48);
                context.getMatrices().pop();

                y = height / 2 - 12;
                for (OrderedText text : renderer.wrapLines(Text.translatable("text.frogvasion.guide.not_yet_unlocked"), 110)) {
                    context.drawText(renderer, text, x, y, 0, false);
                    y += 8;
                }
            }
        } else if (page == 10) {
            y -= 6;
            for (OrderedText text: renderer.wrapLines(Text.translatable("text.frogvasion.guide.infused", Text.translatable("block.frogvasion.lava_infused_froglight"), Text.translatable("item.minecraft.potion.effect.improver_potion")), 110)) {
                context.drawText(renderer, text, x, y, 0, false);
                y += 8;
            }
        }
        context.drawText(renderer, Text.literal(String.valueOf(page + 1)), (width + backgroundWidth) / 2 - 36 - renderer.getWidth(Text.literal(String.valueOf(page))), (height + backgroundHeight) /2 -32, 0, false);
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

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        matrices.pop();
    }
}
