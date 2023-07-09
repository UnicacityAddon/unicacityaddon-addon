package com.rettichlp.unicacityaddon.base.config.tablist;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultTabListConfiguration extends Config implements TabListConfiguration {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> sorted = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> afk = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<Boolean> sorted() {
        return this.sorted;
    }

    @Override
    public ConfigProperty<Boolean> afk() {
        return this.afk;
    }
}