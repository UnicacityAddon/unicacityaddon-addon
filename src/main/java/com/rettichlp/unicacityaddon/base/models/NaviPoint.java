package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
@Getter
public class NaviPoint {

    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String article;

    public String getTabName() {
        return name.replace(" ", "-");
    }

    public String getArticleFourthCase() {
        return article.replace("der", "den");
    }

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public static NaviPoint getNaviPointEntryByTabName(String tabName) {
        return Syncer.NAVIPOINTLIST.stream()
                .filter(naviPointEntry -> naviPointEntry.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }
}