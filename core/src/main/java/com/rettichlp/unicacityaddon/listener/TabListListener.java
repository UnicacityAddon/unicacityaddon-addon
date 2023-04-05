package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.scoreboard.TabListUpdateEvent;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/tree/master/src/main/java/de/fuzzlemann/ucutils/utils/tablist">UCUtils by paulzhng</a>
 */
@UCEvent
public class TabListListener {

    private final UnicacityAddon unicacityAddon;

    public TabListListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onTabListUpdate(TabListUpdateEvent e) {
        if (this.unicacityAddon.configuration().orderedTablist().get()) {
            // TODO: 03.04.2023 this.unicacityAddon.tabListController().orderTabList();
        }
    }
}