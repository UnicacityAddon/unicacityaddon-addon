package com.rettichlp.UnicacityAddon.base.api.entries;

import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import net.minecraft.util.math.BlockPos;

public class NaviPointEntry {

    private final String name;
    private final int x;
    private final int y;
    private final int z;

    public NaviPointEntry(String name, int x, int y , int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public String getTabName() {
        return name.replace(" ", "-");
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

    public static NaviPointEntry getNaviPointEntryByTabName(String tabName) {
        return NavigationUtils.NAVIPOINTLIST.stream()
                .filter(naviPointEntry -> naviPointEntry.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }
}