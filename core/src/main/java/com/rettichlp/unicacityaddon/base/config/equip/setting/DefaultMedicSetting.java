package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultMedicSetting extends Config implements MedicSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> axe = new ConfigProperty<>(50);

    @TextFieldSetting
    private final ConfigProperty<Integer> bandage = new ConfigProperty<>(80);

    @TextFieldSetting
    private final ConfigProperty<Integer> extinguisher = new ConfigProperty<>(400);

    @TextFieldSetting
    private final ConfigProperty<Integer> helmet = new ConfigProperty<>(250);

    @TextFieldSetting
    private final ConfigProperty<Integer> pills = new ConfigProperty<>(80);

    @TextFieldSetting
    private final ConfigProperty<Integer> syringe = new ConfigProperty<>(120);

    @Override
    public ConfigProperty<Integer> axe() {
        return this.axe;
    }

    @Override
    public ConfigProperty<Integer> bandage() {
        return this.bandage;
    }

    @Override
    public ConfigProperty<Integer> extinguisher() {
        return this.extinguisher;
    }

    @Override
    public ConfigProperty<Integer> helmet() {
        return this.helmet;
    }

    @Override
    public ConfigProperty<Integer> pills() {
        return this.pills;
    }

    @Override
    public ConfigProperty<Integer> syringe() {
        return this.syringe;
    }
}