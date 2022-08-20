package com.rettichlp.UnicacityAddon.base.location;

import net.minecraft.util.math.BlockPos;

public enum ServiceCallBox {

    ALTSTADT("???", 267, 69, 603),
    CFK_WESTSIDE("CFK [Westside]", -357, 69, 364),
    FLUGHAFEN("???", -217, 64, 633),
    KRANKENHAUS("Krankenhaus", 246, 69, 257),
    STADTHALLE("Stadthalle", 99, 69, 134),
    MEXICAN("Mexican", 280, 69, -81),
    OBRIEN("???", 728, 69, 538),
    UNICA17("???", 611, 69, 87),
    LEER1("???", 0, 69, 0),
    LEER2("???", 0, 69, 0);

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