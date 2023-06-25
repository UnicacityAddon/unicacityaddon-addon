package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author RettichLP
 */
public class MoneyHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
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
        this.bank = super.createLine("Bank", this.numberFormat.format(this.unicacityAddon.services().file().data().getBankBalance()) + "$");
        this.cash = super.createLine("Bargeld", this.numberFormat.format(this.unicacityAddon.services().file().data().getCashBalance()) + "$");
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.bank.updateAndFlush(this.numberFormat.format(e.getData().getBankBalance()) + "$");
        this.cash.updateAndFlush(this.numberFormat.format(e.getData().getCashBalance()) + "$");
    }
}