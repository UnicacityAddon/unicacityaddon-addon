package com.rettichlp.UnicacityAddon.base.location;

import net.minecraft.util.math.BlockPos;

public enum ServiceCallBox {

    ALTSTADT("Altstadt", 267, 69, 603),
    CASINO("Casino [Chinatown]", 1242, 69, -318),
    CFK_WESTSIDE("CFK [Westside]", -357, 69, 364),
    DISCO("Disco", -253, 60, -25),
    FLUGHAFEN("Flughafen", -217, 64, 633),
    KRANKENHAUS("Krankenhaus", 246, 69, 257),
    MEXICAN("Mexican", 280, 69, -81),
    OBRIEN("O'Brien", 728, 69, 538),
    STADTHALLE("Stadthalle", 99, 69, 134),
    UNICA17("Unica17", 611, 69, 87);

    private final String locationName;
    private final int x;
    private final int y;
    private final int z;

    ServiceCallBox(String locationName, int x, int y, int z) {
        this.locationName = locationName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getLocationName() {
        return locationName;
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

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public long getDistance(BlockPos blockPos) {
        return Math.round(blockPos.getDistance(x, y, z));
    }

    public static ServiceCallBox getServiceCallBoxByLocationName(String s) {
        for (ServiceCallBox serviceCallBox : ServiceCallBox.values()) {
            if (serviceCallBox.getLocationName().equals(s)) return serviceCallBox;
        }
        return null;
    }

    public String getNaviCommand() {
        return "/navi " + x + "/" + y + "/" + z;
    }
}