package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import java.util.Map;

/**
 * @author RettichLP
 */
public class InventoryHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final UnicacityAddon unicacityAddon;

    public InventoryHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        int amount = this.unicacityAddon.services().file().data().getDrugInventoryMap().values().stream()
                .map(Map::values)
                .map(integers -> integers.stream().reduce(0, Integer::sum))
                .reduce(0, Integer::sum);

        this.textLine = super.createLine("Inventar", amount);
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            int amount = this.unicacityAddon.services().file().data().getDrugInventoryMap().values().stream()
                    .map(Map::values)
                    .map(integers -> integers.stream().reduce(0, Integer::sum))
                    .reduce(0, Integer::sum);

            this.textLine.updateAndFlush(amount);
        }
    }
}