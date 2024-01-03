package me.Minestor.frogvasion.screen.custom;

public enum TicTacToeType {
    O(2),
    X(1),
    EMPTY(0),
    DRAW(-1);
    private final int value;
    TicTacToeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static TicTacToeType getByValue(int value) {
        for (TicTacToeType type : values()) {
            if (type.getValue() == value) return type;
        }
        return EMPTY;
    }
}
