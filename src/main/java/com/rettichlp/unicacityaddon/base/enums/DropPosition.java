package com.rettichlp.unicacityaddon.base.enums;

public enum DropPosition {
    GLAS(885, 67, 350),
    WASTE(906, 66, 361),
    METAL(899, 67, 394),
    WOOD(874, 68, 378);

    final int x;
    final int y;
    final int z;

    DropPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}