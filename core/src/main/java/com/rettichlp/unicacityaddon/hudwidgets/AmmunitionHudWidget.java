package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.WeaponShotEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
public class AmmunitionHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public AmmunitionHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Munition", ColorCode.RED.getCode() + "0" + ColorCode.DARK_GRAY.getCode() + "/" + ColorCode.GOLD.getCode() + "0");
        this.setIcon(this.unicacityAddon.services().util().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        return this.unicacityAddon.player().getWeaponInMainHand() != null;
    }

    @Subscribe
    public void onWeaponShot(WeaponShotEvent e) {
        this.textLine.updateAndFlush(e.getWeaponAmmunitionText());
    }
}