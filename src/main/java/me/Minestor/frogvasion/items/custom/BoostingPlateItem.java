package me.Minestor.frogvasion.items.custom;

import me.Minestor.frogvasion.screen.custom.BoostingPlateScreenHandler;
import me.Minestor.frogvasion.util.options.FrogvasionGameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoostingPlateItem extends Item implements NamedScreenHandlerFactory {
    int s1 = 0;
    int s2 = 0;
    int s3 = 0;
    int s4 = 0;
    int s5 = 0;
    int s6 = 0;
    int s7 = 0;
    int s8 = 0;
    int s9 = 0;
    private final PropertyDelegate pd = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> s1;
                case 1 -> s2;
                case 2 -> s3;
                case 3 -> s4;
                case 4 -> s5;
                case 5 -> s6;
                case 6 -> s7;
                case 7 -> s8;
                case 8 -> s9;
                default -> -1;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> s1 = value;
                case 1 -> s2 = value;
                case 2 -> s3 = value;
                case 3 -> s4 = value;
                case 4 -> s5 = value;
                case 5 -> s6 = value;
                case 6 -> s7 = value;
                case 7 -> s8 = value;
                case 8 -> s9 = value;
            }
        }

        @Override
        public int size() {
            return 9;
        }
    };
    BoostingPlateScreenHandler handler;

    public BoostingPlateItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        handler = null;
        s1 = 0; s2 = 0; s3 = 0; s4 = 0; s5 = 0; s6 = 0; s7 = 0; s8 = 0; s9 = 0;
        if(!world.isClient) {
            if(user.getInventory().getMainHandStack().getItem() instanceof BoostingPlateItem bi) {
                user.openHandledScreen(bi);
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("text.item.boosting_plate");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        handler = new BoostingPlateScreenHandler(syncId, playerInventory, pd);
        return handler;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(handler != null && !world.isClient){
            handler.tick();
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(FrogvasionGameOptions.getShowTooltips()){
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("text.item.boosting_plate1").formatted(Formatting.AQUA));
                tooltip.add(Text.translatable("text.item.boosting_plate2").formatted(Formatting.GREEN));
                tooltip.add(Text.translatable("text.item.boosting_plate3").formatted(Formatting.DARK_RED));
            } else {
                tooltip.add(Text.translatable("text.frogvasion.tooltip.press_shift").formatted(Formatting.YELLOW));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
