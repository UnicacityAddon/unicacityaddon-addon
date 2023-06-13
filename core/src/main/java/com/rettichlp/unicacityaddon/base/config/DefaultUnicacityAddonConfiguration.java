package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATM;
import com.rettichlp.unicacityaddon.base.config.atm.DefaultATM;
import com.rettichlp.unicacityaddon.base.config.equip.DefaultEquip;
import com.rettichlp.unicacityaddon.base.config.equip.Equip;
import com.rettichlp.unicacityaddon.base.config.hotkey.DefaultHotkey;
import com.rettichlp.unicacityaddon.base.config.hotkey.Hotkey;
import com.rettichlp.unicacityaddon.base.config.join.Command;
import com.rettichlp.unicacityaddon.base.config.join.DefaultCommand;
import com.rettichlp.unicacityaddon.base.config.join.DefaultPassword;
import com.rettichlp.unicacityaddon.base.config.join.Password;
import com.rettichlp.unicacityaddon.base.config.message.DefaultFaction;
import com.rettichlp.unicacityaddon.base.config.message.DefaultReport;
import com.rettichlp.unicacityaddon.base.config.message.Faction;
import com.rettichlp.unicacityaddon.base.config.message.Report;
import com.rettichlp.unicacityaddon.base.config.nametag.DefaultNameTag;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTag;
import com.rettichlp.unicacityaddon.base.config.ownUse.DefaultOwnUse;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUse;
import com.rettichlp.unicacityaddon.base.config.reinforcement.DefaultReinforcement;
import com.rettichlp.unicacityaddon.base.config.reinforcement.Reinforcement;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSloc;
import com.rettichlp.unicacityaddon.base.config.sloc.Sloc;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
public class DefaultUnicacityAddonConfiguration extends AddonConfig implements UnicacityAddonConfiguration {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    private final DefaultHotkey hotkey = new DefaultHotkey();

    @SettingSection("nametag")
    private final DefaultNameTag nameTag = new DefaultNameTag();

    @SettingSection("faction")
    private final DefaultReinforcement reinforcement = new DefaultReinforcement();

    private final DefaultSloc sloc = new DefaultSloc();

    private final DefaultFaction faction = new DefaultFaction();

    private final DefaultEquip equip = new DefaultEquip();

    private final DefaultOwnUse ownUse = new DefaultOwnUse();

    @SettingSection("report")
    private final DefaultReport report = new DefaultReport();

    @SettingSection("join")
    private final DefaultPassword password = new DefaultPassword();

    private final DefaultCommand command = new DefaultCommand();

    @SwitchSetting
    private final ConfigProperty<Boolean> texturePack = new ConfigProperty<>(true);

    @SettingSection("automation")
    private final DefaultATM atm = new DefaultATM();

    @SwitchSetting
    private final ConfigProperty<Boolean> bombScreenshot = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> carRoute = new ConfigProperty<>(true);

    @SliderSetting(min = 1, max = 20)
    private final ConfigProperty<Integer> aBuyAmount = new ConfigProperty<>(10);

    @SettingSection("teamspeak")
    @SwitchSetting
    private final ConfigProperty<Boolean> resolveAPIKey = new ConfigProperty<>(true);

    @TextFieldSetting
    private final ConfigProperty<String> tsApiKey = new ConfigProperty<>("");

    @SwitchSetting
    private final ConfigProperty<Boolean> tsNotificationPublic = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> tsNotificationSupport = new ConfigProperty<>(false);

    @SettingSection("other")
    @SwitchSetting
    private final ConfigProperty<Boolean> orderedTablist = new ConfigProperty<>(true);

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
    public NameTag nameTag() {
        return this.nameTag;
    }

    @Override
    public Hotkey hotkey() {
        return this.hotkey;
    }

    @Override
    public Reinforcement reinforcement() {
        return this.reinforcement;
    }

    @Override
    public Sloc sloc() {
        return this.sloc;
    }

    @Override
    public Faction faction() {
        return this.faction;
    }

    @Override
    public Report report() {
        return this.report;
    }

    @Override
    public Password password() {
        return this.password;
    }

    @Override
    public Command command() {
        return this.command;
    }

    @Override
    public ConfigProperty<Boolean> texturePack() {
        return this.texturePack;
    }

    @Override
    public ATM atm() {
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
    public ConfigProperty<Boolean> resolveAPIKey() {
        return this.resolveAPIKey;
    }

    @Override
    public ConfigProperty<String> tsApiKey() {
        return this.tsApiKey;
    }

    @Override
    public ConfigProperty<Boolean> tsNotificationPublic() {
        return this.tsNotificationPublic;
    }

    @Override
    public ConfigProperty<Boolean> tsNotificationSupport() {
        return this.tsNotificationSupport;
    }

    @Override
    public Equip equip() {
        return this.equip;
    }

    @Override
    public OwnUse ownUse() {
        return this.ownUse;
    }

    @Override
    public ConfigProperty<Boolean> orderedTablist() {
        return this.orderedTablist;
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