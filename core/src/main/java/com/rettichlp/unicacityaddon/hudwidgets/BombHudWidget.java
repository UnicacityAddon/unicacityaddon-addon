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

/**
 * @author RettichLP
 */
public class BombHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private Integer time;

    private final UnicacityAddon unicacityAddon;

    public BombHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Bombe", this.unicacityAddon.services().utilService().textUtils().parseTimer(0));
        this.setIcon(this.unicacityAddon.services().utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.time != null;
    }

    @Subscribe
    public void onBombPlanted(BombPlantedEvent e) {
        this.time = 0;
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        this.time = null;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.time != null) {
            textLine.updateAndFlush((this.time >= 780 ? ColorCode.RED.getCode() : "") + this.unicacityAddon.services().utilService().textUtils().parseTimer(this.time));
            this.time = this.time >= 1200 ? null : this.time + 1;
        }
    }
}