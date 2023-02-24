package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import static com.rettichlp.unicacityaddon.base.utils.MathUtils.HEART_DECIMAL_FORMAT;

public class HearthHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    public HearthHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Herzen", "nicht geladen");
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isIngame() && e.isPhase(UnicacityAddonTickEvent.Phase.TICK)) {
            this.textLine.updateAndFlush(HEART_DECIMAL_FORMAT.format(UnicacityAddon.PLAYER.getHealth() / 2) + ColorCode.RED.getCode() + "❤");
        }
    }
}