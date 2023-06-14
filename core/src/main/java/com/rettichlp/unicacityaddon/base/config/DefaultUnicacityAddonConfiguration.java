package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.atm.DefaultATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.equip.DefaultEquipConfiguration;
import com.rettichlp.unicacityaddon.base.config.equip.EquipConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.DefaultHotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.CommandConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.DefaultCommandConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.DefaultPasswordConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.PasswordConfiguration;
import com.rettichlp.unicacityaddon.base.config.message.DefaultMessageConfiguration;
import com.rettichlp.unicacityaddon.base.config.message.MessageConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.DefaultNameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.ownUse.DefaultOwnUseConfiguration;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUseConfiguration;
import com.rettichlp.unicacityaddon.base.config.reinforcement.DefaultReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSlocConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocConfiguration;
import com.rettichlp.unicacityaddon.base.config.teamspeak.DefaultTeamSpeakConfiguration;
import com.rettichlp.unicacityaddon.base.config.teamspeak.TeamSpeakConfiguration;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
public class DefaultUnicacityAddonConfiguration extends AddonConfig implements UnicacityAddonConfiguration {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    private final DefaultHotkeyConfiguration hotkey = new DefaultHotkeyConfiguration();

    @SettingSection("nametag")
    private final DefaultNameTagConfiguration nametag = new DefaultNameTagConfiguration();

    @SettingSection("faction")
    private final DefaultReinforcementConfiguration reinforcement = new DefaultReinforcementConfiguration();

    private final DefaultSlocConfiguration sloc = new DefaultSlocConfiguration();

    private final DefaultEquipConfiguration equip = new DefaultEquipConfiguration();

    private final DefaultOwnUseConfiguration ownUse = new DefaultOwnUseConfiguration();

    @SettingSection("message")
    private final DefaultMessageConfiguration message = new DefaultMessageConfiguration();

    @SettingSection("join")
    private final DefaultPasswordConfiguration password = new DefaultPasswordConfiguration();

    private final DefaultCommandConfiguration command = new DefaultCommandConfiguration();

    @SwitchSetting
    private final ConfigProperty<Boolean> texturePack = new ConfigProperty<>(true);

    @SettingSection("automation")
    private final DefaultATMConfiguration atm = new DefaultATMConfiguration();

    @SwitchSetting
    private final ConfigProperty<Boolean> bombScreenshot = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> carRoute = new ConfigProperty<>(true);

    @SliderSetting(min = 1, max = 20)
    private final ConfigProperty<Integer> aBuyAmount = new ConfigProperty<>(10);

    @SettingSection("other")
    private final DefaultTeamSpeakConfiguration teamspeak = new DefaultTeamSpeakConfiguration();

    @SwitchSetting
    private final ConfigProperty<Boolean> tablist = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> despawnTime = new ConfigProperty<>(true);

    @SettingSection("debug")
    @SwitchSetting
    private final ConfigProperty<Boolean> debug = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public NameTagConfiguration nametag() {
        return this.nametag;
    }

    @Override
    public HotkeyConfiguration hotkey() {
        return this.hotkey;
    }

    @Override
    public ReinforcementConfiguration reinforcement() {
        return this.reinforcement;
    }

    @Override
    public SlocConfiguration sloc() {
        return this.sloc;
    }

    @Override
    public EquipConfiguration equip() {
        return this.equip;
    }

    @Override
    public OwnUseConfiguration ownUse() {
        return this.ownUse;
    }

    @Override
    public MessageConfiguration message() {
        return this.message;
    }

    @Override
    public PasswordConfiguration password() {
        return this.password;
    }

    @Override
    public CommandConfiguration command() {
        return this.command;
    }

    @Override
    public ConfigProperty<Boolean> texturePack() {
        return this.texturePack;
    }

    @Override
    public ATMConfiguration atm() {
        return this.atm;
    }

    @Override
    public ConfigProperty<Boolean> bombScreenshot() {
        return this.bombScreenshot;
    }

    @Override
    public ConfigProperty<Boolean> carRoute() {
        return this.carRoute;
    }

    @Override
    public ConfigProperty<Integer> aBuyAmount() {
        return this.aBuyAmount;
    }

    @Override
    public TeamSpeakConfiguration teamspeak() {
        return this.teamspeak;
    }

    @Override
    public ConfigProperty<Boolean> tablist() {
        return this.tablist;
    }

    @Override
    public ConfigProperty<Boolean> despawnTime() {
        return this.despawnTime;
    }

    @Override
    public ConfigProperty<Boolean> debug() {
        return this.debug;
    }
}