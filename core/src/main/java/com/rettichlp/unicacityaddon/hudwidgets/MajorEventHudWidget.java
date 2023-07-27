package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.BankRobEndedEvent;
import com.rettichlp.unicacityaddon.base.events.BankRobStartedEvent;
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
    private Integer bankRobTime;
    private Integer bombTime;

    private final UnicacityAddon unicacityAddon;

    public MajorEventHudWidget(UnicacityAddon unicacityAddon) {
        super("majorEvent");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Großeinsatz", this.unicacityAddon.utilService().text().parseTimer(0) + " | " + this.unicacityAddon.utilService().text().parseTimer(0));
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.bankRobTime != null || this.bombTime != null;
    }

    @Subscribe
    public void onBankRobStarted(BankRobStartedEvent e) {
        long delay = e.getDelaySincePlace();
        this.unicacityAddon.utilService().debug("Start bank with delay = " + delay);
        this.bankRobTime = Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(delay));
    }

    @Subscribe
    public void onBankRobEnded(BankRobEndedEvent e) {
        this.bankRobTime = null;
    }

    @Subscribe
    public void onBombPlanted(BombPlantedEvent e) {
        long delay = e.getDelaySincePlace();
        this.unicacityAddon.utilService().debug("Start bomb with delay = " + delay);
        this.bombTime = Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(delay));
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        this.bombTime = null;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            StringBuilder completeStringBuilder = new StringBuilder();

            if (this.bankRobTime != null) {
                String bankTimeString = (this.bankRobTime >= 1500 ? ColorCode.RED.getCode() : "") + this.unicacityAddon.utilService().text().parseTimer(this.bankRobTime);
                completeStringBuilder.append(bankTimeString);
            }

            if (this.bankRobTime != null && this.bombTime != null) {
                completeStringBuilder.append(" | ");
            }

            if (this.bombTime != null) {
                String bombTimeString = (this.bombTime >= 780 ? ColorCode.RED.getCode() : "") + this.unicacityAddon.utilService().text().parseTimer(this.bombTime);
                completeStringBuilder.append(bombTimeString);
            }

            textLine.updateAndFlush(completeStringBuilder.toString());
            this.bankRobTime = this.bankRobTime >= 1800 ? null : this.bankRobTime + 1;
            this.bombTime = this.bombTime >= 1200 ? null : this.bombTime + 1;
        }
    }
}