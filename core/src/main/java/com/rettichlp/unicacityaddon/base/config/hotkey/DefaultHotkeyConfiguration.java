package com.rettichlp.unicacityaddon.base.config.hotkey;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultHotkeyConfiguration extends Config implements HotkeyConfiguration {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @KeyBindSetting
    private final ConfigProperty<Key> acceptAd = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> declineAd = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> acceptReport = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> cancelReport = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> publicChannel = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> aBuy = new ConfigProperty<>(Key.NONE);

    @KeyBindSetting
    private final ConfigProperty<Key> screenshot = new ConfigProperty<>(Key.NONE);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<Key> acceptAd() {
        return this.acceptAd;
    }

    @Override
    public ConfigProperty<Key> declineAd() {
        return this.declineAd;
    }

    @Override
    public ConfigProperty<Key> acceptReport() {
        return this.acceptReport;
    }

    @Override
    public ConfigProperty<Key> cancelReport() {
        return this.cancelReport;
    }

    @Override
    public ConfigProperty<Key> publicChannel() {
        return this.publicChannel;
    }

    @Override
    public ConfigProperty<Key> aBuy() {
        return this.aBuy;
    }

    @Override
    public ConfigProperty<Key> screenshot() {
        return this.screenshot;
    }
}