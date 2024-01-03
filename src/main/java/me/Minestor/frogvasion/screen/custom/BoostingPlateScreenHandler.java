package me.Minestor.frogvasion.screen.custom;

import me.Minestor.frogvasion.screen.ModScreenHandlers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.random.Random;

import java.util.Map;
import java.util.stream.Collectors;

public class BoostingPlateScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate pd;
    public final Random rand;
    int thinking = -1;
    boolean isFinished = false;
    public BoostingPlateScreenHandler(int syncId, PlayerInventory pinv) {
        this(syncId, pinv, new ArrayPropertyDelegate(9));
    }
    public BoostingPlateScreenHandler(int syncId, PlayerInventory pinv, PropertyDelegate pd) {
        super(ModScreenHandlers.BOOSTING_PLATE_SCREEN_HANDLER, syncId);
        inventory = new SimpleInventory(1);
        inventory.onOpen(pinv.player);
        this.rand = pinv.player.getRandom();

        this.addSlot(new Slot(inventory, 0, 12, 15));
        addPlayerInventory(pinv);
        addPlayerHotbar(pinv);

        this.pd = pd;
        addProperties(pd);
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (!this.slots.get(0).hasStack() && this.slots.get(0).canInsert(itemStack2) && itemStack2.getCount() == 1) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 1 && slot < 28) {
                if (!this.insertItem(itemStack2, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 28 && slot < 37) {
                if (!this.insertItem(itemStack2, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if(!player.getWorld().isClient) {
            player.giveItemStack(slots.get(0).getStack());
            slots.get(0).setStack(ItemStack.EMPTY);
        }
        super.onClosed(player);
    }

    public PropertyDelegate getPd() {
        return pd;
    }

    public void tick() {
        if(!isFinished && getGameStatus() == 0){
            if (thinking >= 0) thinking++;
            if (thinking >= 20) {
                if (AIMove() == -1)
                    throw new IllegalStateException("Couldn't find a possible move for the TicTacToe game! The board was already full!");
                thinking = -1;
            }
        }
        if(!isFinished && getGameStatus() != 0) {
            isFinished = true;
            if(slots.get(0).hasStack()) {
                ItemStack stack = slots.get(0).getStack();
                int status = getGameStatus();

                if(status == -1) return;
                if(status == 1) {
                    if(stack.isEnchantable()) {
                        EnchantmentHelper.enchant(rand, stack, 15, true);
                    } else if (stack.isDamaged()) {
                        stack.setDamage(Math.max(stack.getDamage() - 20, 0));
                    } else if (EnchantmentHelper.hasVanishingCurse(stack) || EnchantmentHelper.hasBindingCurse(stack)) {
                        Map<Enchantment, Integer> map = EnchantmentHelper.get(stack).entrySet().stream().
                                filter((entry) -> !entry.getKey().isCursed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        EnchantmentHelper.set(map, stack);
                    }
                } else if (status == 2) {
                    if(stack.isEnchantable() && !EnchantmentHelper.hasBindingCurse(stack) && Enchantments.BINDING_CURSE.isAcceptableItem(stack)) {
                        stack.addEnchantment(Enchantments.BINDING_CURSE, 1);
                    } else if(stack.isEnchantable() && !EnchantmentHelper.hasVanishingCurse(stack) && Enchantments.VANISHING_CURSE.isAcceptableItem(stack)) {
                        stack.addEnchantment(Enchantments.VANISHING_CURSE, 1);
                    } else if(stack.isDamageable()) {
                        stack.setDamage(Math.min(stack.getMaxDamage() -1, stack.getDamage() + 20));
                    }
                }
            }
        }
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        pd.set(id -1, 1);
        thinking = 0;
        return super.onButtonClick(player, id);
    }

    public static int getToPressWin(int[][] tilesAndValues) {
        int s1 = tilesAndValues[0][1], s2 = tilesAndValues[1][1], s3 = tilesAndValues[2][1];
        if(areSameType(s1, s2, s3)) return -1;
        if(s1 != 0 && s2 != 0 && s3 != 0) return -1;
        if(areSameType(s1, s2) || areSameType(s1, s3) || areSameType(s2, s3)) {
            if(areOfAI(s1, s2) && s3 == 0) return tilesAndValues[2][0];
            if(areOfAI(s2, s3) && s1 == 0) return tilesAndValues[0][0];
            if(areOfAI(s1, s3) && s2 == 0) return tilesAndValues[1][0];
        }
        return -1;
    }
    public static int getToPressDefence(int[][] tilesAndValues) {
        int s1 = tilesAndValues[0][1], s2 = tilesAndValues[1][1], s3 = tilesAndValues[2][1];
        if(areSameType(s1, s2, s3)) return -1;
        if(s1 != 0 && s2 != 0 && s3 != 0) return -1;
        if(areSameType(s1, s2) || areSameType(s1, s3) || areSameType(s2, s3)) {
            if(areOfPlayer(s1, s2) && s3 == 0) return tilesAndValues[2][0];
            if(areOfPlayer(s2, s3) && s1 == 0) return tilesAndValues[0][0];
            if(areOfPlayer(s1, s3) && s2 == 0) return tilesAndValues[1][0];
        }
        return -1;
    }
    public static boolean areOfAI(int... tiles) {
        if(tiles[0] != TicTacToeType.O.getValue()) return false;
        return areSameType(tiles);
    }
    public static boolean areOfPlayer(int... tiles) {
        if(tiles[0] != TicTacToeType.X.getValue()) return false;
        return areSameType(tiles);
    }
    public static boolean areSameType(int... tiles) {
        int firstType = tiles[0];
        for (int tile : tiles) {
            if(tile == 0) return false;
            if(tile != firstType) return false;
        }
        return true;
    }
    public int getGameStatus() {
        for (int i = 0; i < 3; i++) {
            if(areSameType(pd.get(i * 3), pd.get(i*3 + 1), pd.get(i*3 +2))) {
                return pd.get(i*3);
            }
            if(areSameType(pd.get(i), pd.get(i + 3), pd.get(i + 6))) {
                return pd.get(i);
            }
        }
        if(areSameType(pd.get(0), pd.get(4), pd.get(8)) || areSameType(pd.get(2), pd.get(4), pd.get(6)))
            return pd.get(4);

        for (int i = 0; i < 9; i++) {
            if(pd.get(i) == 0)
                return 0;
        }
        return -1;
    }
    public int AIMove() {
        if(getGameStatus() != 0) return -1;
        //check possible win
        for (int i = 0; i < 3; i++) {
            final int horizontal = getToPressWin(new int[][]{{i * 3, pd.get(i * 3)}, {i * 3 + 1, pd.get(i * 3 + 1)}, {i * 3 + 2, pd.get(i * 3 + 2)}});
            if(horizontal != -1) {
                pd.set(horizontal, 2);
                return horizontal;
            }
            final int vertical = getToPressWin(new int[][]{{i, pd.get(i)}, {i + 3, pd.get(i + 3)}, {i + 6, pd.get(i + 6)}});
            if(vertical != -1) {
                pd.set(vertical, 2);
                return vertical;
            }
        }
        final int diaLR = getToPressWin(new int[][]{{0, pd.get(0)}, {4, pd.get(4)}, {8, pd.get(8)}}),
                diaRL = getToPressWin(new int[][]{{2, pd.get(2)}, {4, pd.get(4)}, {6, pd.get(6)}});
        if(diaLR != -1) {
            pd.set(diaLR, 2);
            return diaLR;
        }
        if(diaRL != -1) {
            pd.set(diaRL, 2);
            return diaRL;
        }
        //check possible win opponent
        for (int i = 0; i < 3; i++) {
            final int horizontal = getToPressDefence(new int[][]{{i * 3, pd.get(i * 3)}, {i * 3 + 1, pd.get(i * 3 + 1)}, {i * 3 + 2, pd.get(i * 3 + 2)}});
            final int vertical = getToPressDefence(new int[][]{{i, pd.get(i)}, {i + 3, pd.get(i + 3)}, {i + 6, pd.get(i + 6)}});
            if(horizontal != -1) {
                pd.set(horizontal, 2);
                return horizontal;
            }
            if(vertical != -1) {
                pd.set(vertical, 2);
                return vertical;
            }
        }
        final int diaLRD = getToPressDefence(new int[][]{{0, pd.get(0)}, {4, pd.get(4)}, {8, pd.get(8)}}),
                diaRLD = getToPressDefence(new int[][]{{2, pd.get(2)}, {4, pd.get(4)}, {6, pd.get(6)}});
        if(diaLRD != -1) {
            pd.set(diaLRD, 2);
            return diaLRD;
        }
        if(diaRLD != -1) {
            pd.set(diaRLD, 2);
            return diaRLD;
        }
        //no danger or opportunity --> select random if middle is not open
        if(pd.get(4) == 0) {
            pd.set(4, 2);
            return 4;
        }
        if(getGameStatus() != 0) return -1;
        while(getGameStatus() == 0) {
            int c = rand.nextInt(9);
            if(pd.get(c) == 0) {
                pd.set(c, 2);
                return c;
            }
        }
        return -1;
    }
}