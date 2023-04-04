package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
public class MoneyHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine bank;
    private TextLine cash;

    private final UnicacityAddon unicacityAddon;

    public MoneyHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.bank = super.createLine("Bank", this.unicacityAddon.fileService().data().getBankBalance() + "$");
        this.cash = super.createLine("Bargeld", this.unicacityAddon.fileService().data().getCashBalance() + "$");
        this.setIcon(this.unicacityAddon.getIcon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.bank.updateAndFlush(e.getData().getBankBalance() + "$");
        this.cash.updateAndFlush(e.getData().getCashBalance() + "$");
    }
}