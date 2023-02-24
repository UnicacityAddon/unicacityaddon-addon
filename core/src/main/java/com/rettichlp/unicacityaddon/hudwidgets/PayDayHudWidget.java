package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;

public class PayDayHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final Icon hudWidgetIcon;

    public PayDayHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("PayDay", FileManager.DATA.getPayDayTime() + "/60");
        this.setIcon(this.hudWidgetIcon);
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getPayDayTime() + "/60");
    }
}