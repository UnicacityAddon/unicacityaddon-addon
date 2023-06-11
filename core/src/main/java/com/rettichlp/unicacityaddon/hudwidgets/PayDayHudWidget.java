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
public class PayDayHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public PayDayHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("PayDay", this.unicacityAddon.services().file().data().getPayDayTime() + "/60");
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getPayDayTime() + "/60");
    }
}