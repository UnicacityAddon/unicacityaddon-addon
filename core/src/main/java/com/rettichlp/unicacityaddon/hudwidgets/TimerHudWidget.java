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

    public TimerHudWidget(UnicacityAddon unicacityAddon) {
        super("timer");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Timer", this.unicacityAddon.utilService().text().parseTimer(this.unicacityAddon.fileService().data().getTimer()));
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.unicacityAddon.fileService().data().getTimer() >= 0;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            if (this.unicacityAddon.fileService().data().getTimer() >= 0) {
                String text = this.unicacityAddon.utilService().text().parseTimer(this.unicacityAddon.fileService().data().getTimer());
                this.textLine.updateAndFlush(text);
                this.unicacityAddon.fileService().data().setTimer(this.unicacityAddon.fileService().data().getTimer() - 1);
            }
        }
    }
}