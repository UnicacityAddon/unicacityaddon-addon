package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.event.Subscribe;

public class CarHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    public CarHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Auto", "");
    }

    @Override
    public boolean isVisibleInGame() {
        RenderableComponent renderableComponent = this.textLine.getRenderableComponent();
        return renderableComponent != null && renderableComponent.getText().length() > 0;
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        this.textLine.updateAndFlush(e.getData().getCarInfo());
    }
}