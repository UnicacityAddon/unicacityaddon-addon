package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

public class MoneyHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine bank;
    private TextLine cash;

    public MoneyHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.bank = super.createLine("Bargeld", FileManager.DATA.getBankBalance() + "$");
        this.cash = super.createLine("Bargeld", FileManager.DATA.getCashBalance() + "$");
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.bank.updateAndFlush(e.getData().getBankBalance() + "$");
        this.cash.updateAndFlush(e.getData().getCashBalance() + "$");
    }
}