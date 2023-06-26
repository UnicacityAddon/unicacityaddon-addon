package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class EventRegistrationListener {

    private long currentTick = 0;

    private final UnicacityAddon unicacityAddon;

    public EventRegistrationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onGameTick(GameTickEvent e) {
        if (e.phase().equals(Phase.POST)) {
            this.currentTick++;

            this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.TICK));

            // 0,25 SECONDS
            if (this.currentTick % 5 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.TICK_5));
            }

            // 1 SECOND
            if (this.currentTick % 20 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND));
            }

            // 3 SECONDS
            if (this.currentTick % 60 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_3));
            }

            // 5 SECONDS
            if (this.currentTick % 100 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_5));
            }

            // 30 SECONDS
            if (this.currentTick % 600 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_30));
            }

            // 1 MINUTE
            if (this.currentTick % 1200 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.MINUTE));
            }
        }
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        KeyEvent.State state = e.state();
        Key key = e.key();

        UnicacityAddonConfiguration configuration = this.unicacityAddon.configuration();
        if (state.equals(KeyEvent.State.PRESS)) {
            if (key.equals(Key.TAB) && !Laby.references().chatAccessor().isChatOpen() && configuration.tablist().get()) {
                this.unicacityAddon.utilService().debug("Sorting tab list");
                this.unicacityAddon.tabListController().orderTabList(this.unicacityAddon);
                return;
            }

            HotkeyConfiguration hotkeyConfiguration = configuration.hotkey();
            if (!Laby.references().chatAccessor().isChatOpen() && this.unicacityAddon.utilService().isUnicacity() && hotkeyConfiguration.enabled().get()) {
                this.unicacityAddon.labyAPI().eventBus().fire(new HotkeyEvent(this.unicacityAddon, key));
            }
        }
    }
}