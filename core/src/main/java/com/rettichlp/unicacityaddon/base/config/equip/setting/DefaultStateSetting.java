package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultStateSetting extends Config implements StateSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> cuff = new ConfigProperty<>(0);

    @TextFieldSetting
    private final ConfigProperty<Integer> defuseKit = new ConfigProperty<>(500);

    @TextFieldSetting
    private final ConfigProperty<Integer> elytra = new ConfigProperty<>(450);

    @TextFieldSetting
    private final ConfigProperty<Integer> grenade = new ConfigProperty<>(250);

    @TextFieldSetting
    private final ConfigProperty<Integer> shield = new ConfigProperty<>(0);

    @TextFieldSetting
    private final ConfigProperty<Integer> smoke = new ConfigProperty<>(300);

    @TextFieldSetting
    private final ConfigProperty<Integer> taser = new ConfigProperty<>(0);

    @TextFieldSetting
    private final ConfigProperty<Integer> tracker = new ConfigProperty<>(0);

    @Override
    public ConfigProperty<Integer> cuff() {
        return this.cuff;
    }

    @Override
    public ConfigProperty<Integer> defuseKit() {
        return this.defuseKit;
    }

    @Override
    public ConfigProperty<Integer> elytra() {
        return this.elytra;
    }

    @Override
    public ConfigProperty<Integer> grenade() {
        return this.grenade;
    }

    @Override
    public ConfigProperty<Integer> shield() {
        return this.shield;
    }

    @Override
    public ConfigProperty<Integer> smoke() {
        return this.smoke;
    }

    @Override
    public ConfigProperty<Integer> taser() {
        return this.taser;
    }

    @Override
    public ConfigProperty<Integer> tracker() {
        return this.tracker;
    }
}