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
 * @author Gelegenheitscode
 * @author RettichLP
 */
public class BalanceHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
    private TextLine bank;

    private final UnicacityAddon unicacityAddon;

    public BalanceHudWidget(UnicacityAddon unicacityAddon) {
        super("balance");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.bank = super.createLine("Bank", this.numberFormat.format(this.unicacityAddon.fileService().data().getBankBalance()) + "$");
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.bank.updateAndFlush(this.numberFormat.format(e.getData().getBankBalance()) + "$");
    }
}