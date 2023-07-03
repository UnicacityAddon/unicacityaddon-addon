package com.rettichlp.unicacityaddon.base.config.nametag.streetwar;

import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultStreetwar extends Config implements Streetwar {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @DropdownSetting
    private final ConfigProperty<ColorCode> color = ConfigProperty.createEnum(ColorCode.RED);

    @DropdownSetting
    private final ConfigProperty<Faction> faction1 = ConfigProperty.createEnum(Faction.NULL);

    @DropdownSetting
    private final ConfigProperty<Faction> faction2 = ConfigProperty.createEnum(Faction.NULL);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<ColorCode> color() {
        return this.color;
    }

    @Override
    public ConfigProperty<Faction> faction1() {
        return this.faction1;
    }

    @Override
    public ConfigProperty<Faction> faction2() {
        return this.faction2;
    }
}
