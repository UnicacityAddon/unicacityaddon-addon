package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.WeaponShotEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;

public class AmmunitionHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final Icon hudWidgetIcon;

    public AmmunitionHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Munition", ColorCode.RED.getCode() + "0" + ColorCode.DARK_GRAY.getCode() + "/" + ColorCode.GOLD.getCode() + "0");
        this.setIcon(this.hudWidgetIcon);
    }

    @Override
    public boolean isVisibleInGame() {
        ItemStack mainHandItemStack = UnicacityAddon.ADDON.labyAPI().minecraft().clientPlayer().getMainHandItemStack();
        String displayName = TextUtils.legacy(mainHandItemStack.getDisplayName());
        return Weapon.getWeaponByItemName(displayName) != null;
    }

    @Subscribe
    public void onWeaponShot(WeaponShotEvent e) {
        this.textLine.updateAndFlush(e.getWeaponAmmunitionText());
    }
}