package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceListener;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;

/**
 * @author RettichLP
 */
public class EmergencyServiceHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    public static TextLine textLine;

    private final Icon hudWidgetIcon;

    public EmergencyServiceHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        textLine = super.createLine("Notrufe", EmergencyServiceListener.openServices);
        this.setIcon(this.hudWidgetIcon);
    }
}