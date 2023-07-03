package com.rettichlp.unicacityaddon.base.config.nametag.faction;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultFaction extends Config implements Faction {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @DropdownSetting
    private final ConfigProperty<ColorCode> color = ConfigProperty.createEnum(ColorCode.BLUE);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<ColorCode> color() {
        return this.color;
    }
}