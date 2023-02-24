package com.rettichlp.unicacityaddon.hudwidgets;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;

public class EmergencyServiceHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    public static TextLine textLine;

    public EmergencyServiceHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        textLine = super.createLine("Notrufe", 0);
    }
}