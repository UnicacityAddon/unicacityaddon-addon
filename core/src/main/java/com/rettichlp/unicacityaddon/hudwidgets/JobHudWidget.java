package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

public class JobHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    public JobHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Job", FileManager.DATA.getJobBalance() + "$ | " + FileManager.DATA.getJobExperience() + " EXP");
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getJobBalance() + "$ | " + e.getData().getJobExperience() + " EXP");
    }
}