package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceListener;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;

/**
 * @author RettichLP
 */
public class EmergencyServiceHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    public static TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public EmergencyServiceHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        textLine = super.createLine("Notrufe", EmergencyServiceListener.openServices);
        this.setIcon(this.unicacityAddon.services().util().icon());
    }
}