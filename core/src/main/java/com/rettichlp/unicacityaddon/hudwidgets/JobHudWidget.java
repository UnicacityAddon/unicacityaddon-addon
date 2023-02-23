package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

public class JobHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine experience;
    private TextLine money;

    public JobHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.money = super.createLine("Geld", FileManager.DATA.getJobBalance() + "$");
        this.experience = super.createLine("Exp", FileManager.DATA.getJobExperience() + " EXP");
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.experience.updateAndFlush(e.getData().getJobExperience() + " EXP");
        this.money.updateAndFlush(e.getData().getJobBalance() + "$");
    }
}