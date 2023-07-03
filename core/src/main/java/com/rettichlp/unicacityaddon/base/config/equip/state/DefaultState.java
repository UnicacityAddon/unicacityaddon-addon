package com.rettichlp.unicacityaddon.base.config.equip.state;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultState extends Config implements State {

    @TextFieldSetting
    private final ConfigProperty<String> cuff = new ConfigProperty<>("0");

    @TextFieldSetting
    private final ConfigProperty<String> defuseKit = new ConfigProperty<>("500");

    @TextFieldSetting
    private final ConfigProperty<String> elytra = new ConfigProperty<>("450");

    @TextFieldSetting
    private final ConfigProperty<String> grenade = new ConfigProperty<>("250");

    @TextFieldSetting
    private final ConfigProperty<String> shield = new ConfigProperty<>("0");

    @TextFieldSetting
    private final ConfigProperty<String> smoke = new ConfigProperty<>("300");

    @TextFieldSetting
    private final ConfigProperty<String> taser = new ConfigProperty<>("0");

    @TextFieldSetting
    private final ConfigProperty<String> tracker = new ConfigProperty<>("0");

    @Override
    public ConfigProperty<String> cuff() {
        return this.cuff;
    }

    @Override
    public ConfigProperty<String> defuseKit() {
        return this.defuseKit;
    }

    @Override
    public ConfigProperty<String> elytra() {
        return this.elytra;
    }

    @Override
    public ConfigProperty<String> grenade() {
        return this.grenade;
    }

    @Override
    public ConfigProperty<String> shield() {
        return this.shield;
    }

    @Override
    public ConfigProperty<String> smoke() {
        return this.smoke;
    }

    @Override
    public ConfigProperty<String> taser() {
        return this.taser;
    }

    @Override
    public ConfigProperty<String> tracker() {
        return this.tracker;
    }
}