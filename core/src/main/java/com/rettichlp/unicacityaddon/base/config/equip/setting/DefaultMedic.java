package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultMedic extends Config implements Medic {

    @TextFieldSetting
    private final ConfigProperty<String> axe = new ConfigProperty<>("50");

    @TextFieldSetting
    private final ConfigProperty<String> bandage = new ConfigProperty<>("80");

    @TextFieldSetting
    private final ConfigProperty<String> extinguisher = new ConfigProperty<>("400");

    @TextFieldSetting
    private final ConfigProperty<String> helmet = new ConfigProperty<>("250");

    @TextFieldSetting
    private final ConfigProperty<String> pills = new ConfigProperty<>("80");

    @TextFieldSetting
    private final ConfigProperty<String> syringe = new ConfigProperty<>("120");

    @Override
    public ConfigProperty<String> axe() {
        return this.axe;
    }

    @Override
    public ConfigProperty<String> bandage() {
        return this.bandage;
    }

    @Override
    public ConfigProperty<String> extinguisher() {
        return this.extinguisher;
    }

    @Override
    public ConfigProperty<String> helmet() {
        return this.helmet;
    }

    @Override
    public ConfigProperty<String> pills() {
        return this.pills;
    }

    @Override
    public ConfigProperty<String> syringe() {
        return this.syringe;
    }
}