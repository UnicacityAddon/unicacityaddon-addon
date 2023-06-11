package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import static com.rettichlp.unicacityaddon.base.services.utils.MathUtils.HEART_DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
public class HearthHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public HearthHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Herzen", "nicht geladen");
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.unicacityAddon.player().getWeaponInMainHand() != null;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        Float health = this.unicacityAddon.player().getHealth();
        if (e.isIngame() && e.isPhase(UnicacityAddonTickEvent.Phase.TICK) && health != null) {
            this.textLine.updateAndFlush(HEART_DECIMAL_FORMAT.format(health / 2) + ColorCode.RED.getCode() + "❤");
        }
    }
}