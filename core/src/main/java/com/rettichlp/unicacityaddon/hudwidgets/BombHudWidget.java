package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

public class BombHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    public static int timer = -1;
    public static TextLine textLine;

    public BombHudWidget(String id) {
        super(id);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        textLine = super.createLine("Bombe", "");
    }

    @Override
    public boolean isVisibleInGame() {
        return timer >= 0;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && timer >= 0) {
            textLine.updateAndFlush((timer >= 780 ? ColorCode.RED.getCode() : "") + TextUtils.parseTimer(timer));

            if (timer >= 1200)
                timer = -1;
            else
                timer++;
        }
    }
}