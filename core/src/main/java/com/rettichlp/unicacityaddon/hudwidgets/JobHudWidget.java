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
public class JobHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public JobHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Job", this.unicacityAddon.services().fileService().data().getJobBalance() + "$ | " + this.unicacityAddon.services().fileService().data().getJobExperience() + " EXP");
        this.setIcon(this.unicacityAddon.services().utilService().icon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getJobBalance() + "$ | " + e.getData().getJobExperience() + " EXP");
    }
}