package com.rettichlp.unicacityaddon.base.config.drug.marijuana;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/**
 * @author RettichLP
 */
public class DefaultMarijuana extends Config implements Marijuana {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection("ownUse")
    @SliderSetting(min = 1, max = 100)
    private final ConfigProperty<Integer> amount = new ConfigProperty<>(25);

    @DropdownSetting
    private final ConfigProperty<DrugPurity> purity = ConfigProperty.createEnum(DrugPurity.GOOD);

    @SettingSection("price")
    @SliderSetting(min = 20, max = 70)
    private final ConfigProperty<Integer> best = new ConfigProperty<>(25);

    @SliderSetting(min = 15, max = 65)
    private final ConfigProperty<Integer> good = new ConfigProperty<>(25);

    @SliderSetting(min = 10, max = 60)
    private final ConfigProperty<Integer> medium = new ConfigProperty<>(25);

    @SliderSetting(min = 5, max = 55)
    private final ConfigProperty<Integer> bad = new ConfigProperty<>(25);

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

    @Override
    public ConfigProperty<Integer> best() {
        return this.best;
    }

    @Override
    public ConfigProperty<Integer> good() {
        return this.good;
    }

    @Override
    public ConfigProperty<Integer> medium() {
        return this.medium;
    }

    @Override
    public ConfigProperty<Integer> bad() {
        return this.bad;
    }
}