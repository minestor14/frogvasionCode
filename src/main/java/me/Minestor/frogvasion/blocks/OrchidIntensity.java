package me.Minestor.frogvasion.blocks;

public enum OrchidIntensity {
    POWDER(4.5f, 1),
    FLOWER(3.2f, 3),
    BOUQUET(1.8f, 6);
    final float range;
    final int power;
    OrchidIntensity(float range, int power){
        this.power = power;
        this.range = range;
    }

    public float getRange() {
        return range;
    }

    public int getPower() {
        return power;
    }
}
