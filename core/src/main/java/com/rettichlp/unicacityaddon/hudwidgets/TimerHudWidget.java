package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

/**
 * Timer for:
 * <ul>
 *     <li>FBI burglary (time until wps are cleared)</li>
 *     <li>Explosive belt timer (time until explosive belt explodes)</li>
 * </ul>
 *
 * @author Dimiikou
 * @author RettichLP
 */
public class TimerHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public TimerHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Timer", this.unicacityAddon.services().util().text().parseTimer(this.unicacityAddon.services().file().data().getTimer()));
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.unicacityAddon.services().file().data().getTimer() > 0;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            this.textLine.updateAndFlush(this.unicacityAddon.services().util().text().parseTimer(this.unicacityAddon.services().file().data().getTimer()));
        }
    }
}