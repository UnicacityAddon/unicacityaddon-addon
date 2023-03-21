package com.rettichlp.unicacityaddon.base.config.ownUse.setting;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultMarihuanaSetting extends Config implements MarihuanaSetting {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SliderSetting(min = 1, max = 100)
    private final ConfigProperty<Integer> amount = new ConfigProperty<>(25);

    @DropdownSetting
    private final ConfigProperty<DrugPurity> purity = ConfigProperty.createEnum(DrugPurity.GOOD);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<Integer> amount() {
        return this.amount;
    }

    @Override
    public ConfigProperty<DrugPurity> purity() {
        return this.purity;
    }
}