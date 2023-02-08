package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.util.math.vector.FloatVector3;

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

    public FloatVector3 getBlockPos() {
        return new FloatVector3(x, y, z);
    }

    public static NaviPoint getNaviPointEntryByTabName(String tabName) {
        return Syncer.NAVIPOINTLIST.stream()
                .filter(naviPointEntry -> naviPointEntry.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }
}