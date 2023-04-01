package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import net.labymod.api.event.client.scoreboard.TabListUpdateEvent;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/tree/master/src/main/java/de/fuzzlemann/ucutils/utils/tablist">UCUtils by paulzhng</a>
 */
public class TabListListener {

    private final UnicacityAddon unicacityAddon;

    public TabListListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onScoreboardTeamUpdate(ScoreboardTeamUpdateEvent e) {
        // if (e.team().getEntries().size() > 0)
        // TODO: 30.03.2023 UnicacityAddon.debug("SCOREBOARDTEAM: " + e.team().getTeamName() + " (" + e.team().getEntries() + ")");
    }

    @Subscribe
    public void onTabListUpdate(TabListUpdateEvent e) {
        // TODO: 30.03.2023 UnicacityAddon.debug("TABLIST UPDATE");
//        this.tabListController.orderTabList();
    }
}