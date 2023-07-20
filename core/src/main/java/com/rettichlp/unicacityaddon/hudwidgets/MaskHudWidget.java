package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.MaskPutOnEvent;
import com.rettichlp.unicacityaddon.base.events.MaskRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class MaskHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private Integer time;

    private final UnicacityAddon unicacityAddon;

    public MaskHudWidget(UnicacityAddon unicacityAddon) {
        super("mask");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Maske", this.unicacityAddon.utilService().text().parseTimer(0));
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.time != null;
    }

    @Subscribe
    public void onMaskPutOn(MaskPutOnEvent e) {
        this.time = Math.toIntExact(TimeUnit.MINUTES.toSeconds(20));
    }

    @Subscribe
    public void onMaskRemoved(MaskRemovedEvent e) {
        this.time = null;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.time != null) {
            String text = this.unicacityAddon.utilService().text().parseTimer(this.time);
            this.textLine.updateAndFlush(text);
            this.time = this.time >= 0 ? this.time - 1 : null;
        }
    }
}