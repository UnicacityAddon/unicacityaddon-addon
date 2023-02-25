package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;

import java.util.Map;

public class InventoryHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final Icon hudWidgetIcon;

    public InventoryHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        int amount = FileManager.DATA.getDrugInventoryMap().values().stream()
                .map(Map::values)
                .map(integers -> integers.stream().reduce(0, Integer::sum))
                .reduce(0, Integer::sum);

        this.textLine = super.createLine("Inventar", amount);
        this.setIcon(this.hudWidgetIcon);
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            int amount = FileManager.DATA.getDrugInventoryMap().values().stream()
                    .map(Map::values)
                    .map(integers -> integers.stream().reduce(0, Integer::sum))
                    .reduce(0, Integer::sum);

            this.textLine.updateAndFlush(amount);
        }
    }
}