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
public class JobHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public JobHudWidget(UnicacityAddon unicacityAddon) {
        super("job");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Job", this.numberFormat.format(this.unicacityAddon.services().file().data().getJobBalance()) + "$ | " + this.unicacityAddon.services().file().data().getJobExperience() + " EXP");
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(this.numberFormat.format(e.getData().getJobBalance()) + "$ | " + e.getData().getJobExperience() + " EXP");
    }
}