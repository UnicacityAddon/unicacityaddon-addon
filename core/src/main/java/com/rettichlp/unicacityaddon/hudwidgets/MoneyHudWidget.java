package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author RettichLP
 */
public class MoneyHudWidget extends TextHudWidget<MoneyHudWidget.MoneyHudWidgetConfig> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
    private TextLine bank;
    private TextLine cash;

    private final UnicacityAddon unicacityAddon;

    public MoneyHudWidget(UnicacityAddon unicacityAddon) {
        super("money", MoneyHudWidgetConfig.class);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(MoneyHudWidgetConfig config) {
        super.load(config);

        this.bank = super.createLine("Bank", this.numberFormat.format(this.unicacityAddon.fileService().data().getBankBalance()) + "$");
        this.cash = super.createLine("Bargeld", this.numberFormat.format(this.unicacityAddon.fileService().data().getCashBalance()) + "$");
        this.setIcon(this.unicacityAddon.utilService().icon());

        this.bank.setState(config.showBankMoney().get() ? TextLine.State.VISIBLE : TextLine.State.HIDDEN);
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.bank.updateAndFlush(this.numberFormat.format(e.getData().getBankBalance()) + "$");
        this.cash.updateAndFlush(this.numberFormat.format(e.getData().getCashBalance()) + "$");
    }

    public static class MoneyHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchSetting
        private final ConfigProperty<Boolean> showBankBalance = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> showBankMoney() {
            return this.showBankBalance;
        }
    }
}