package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.BombPlantedEvent;
import com.rettichlp.unicacityaddon.base.events.BombRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class MajorEventHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private Integer time;

    private final UnicacityAddon unicacityAddon;

    public MajorEventHudWidget(UnicacityAddon unicacityAddon) {
        super("majorEvent");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Bombe", this.unicacityAddon.utilService().text().parseTimer(0));
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.time != null;
    }

    @Subscribe
    public void onBombPlanted(BombPlantedEvent e) {
        long delay = e.getDelaySincePlace();
        this.unicacityAddon.utilService().debug("Start bomb with delay = " + delay);
        this.time = Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(delay));
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        this.time = null;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.time != null) {
            String text = (this.time >= 780 ? ColorCode.RED.getCode() : "") + this.unicacityAddon.utilService().text().parseTimer(this.time);
            textLine.updateAndFlush(text);
            this.time = this.time >= 1200 ? null : this.time + 1;
        }
    }
}