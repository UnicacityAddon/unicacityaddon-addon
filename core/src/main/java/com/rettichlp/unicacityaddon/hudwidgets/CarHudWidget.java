package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;

public class CarHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final Icon hudWidgetIcon;

    public CarHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Auto", FileManager.DATA.getCarInfo());
        this.setIcon(this.hudWidgetIcon);
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getCarInfo());
    }
}