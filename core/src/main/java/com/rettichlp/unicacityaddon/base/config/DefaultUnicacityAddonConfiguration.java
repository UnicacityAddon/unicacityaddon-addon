package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMSetting;
import com.rettichlp.unicacityaddon.base.config.atm.DefaultATMSetting;
import com.rettichlp.unicacityaddon.base.config.equip.DefaultEquipSetting;
import com.rettichlp.unicacityaddon.base.config.equip.EquipSetting;
import com.rettichlp.unicacityaddon.base.config.hotkey.DefaultHotkeySetting;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.config.join.CommandSetting;
import com.rettichlp.unicacityaddon.base.config.join.DefaultCommandSetting;
import com.rettichlp.unicacityaddon.base.config.join.DefaultPasswordSetting;
import com.rettichlp.unicacityaddon.base.config.join.PasswordSetting;
import com.rettichlp.unicacityaddon.base.config.message.DefaultFactionMessageSetting;
import com.rettichlp.unicacityaddon.base.config.message.DefaultReportMessageSetting;
import com.rettichlp.unicacityaddon.base.config.message.FactionMessageSetting;
import com.rettichlp.unicacityaddon.base.config.message.ReportMessageSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.DefaultNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.DefaultOwnUseSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUseSetting;
import com.rettichlp.unicacityaddon.base.config.reinforcement.DefaultReinforcementSetting;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementSetting;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSlocSetting;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocSetting;
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

    private final DefaultHotkeySetting hotkeySetting = new DefaultHotkeySetting();

    @SettingSection("nametags")
    private final DefaultNameTagSetting nameTagSetting = new DefaultNameTagSetting();

    @SettingSection("reinforcement_and_sloc")
    private final DefaultReinforcementSetting reinforcementSetting = new DefaultReinforcementSetting();

    private final DefaultSlocSetting slocSetting = new DefaultSlocSetting();

    @SettingSection("messages")
    private final DefaultFactionMessageSetting factionMessageSetting = new DefaultFactionMessageSetting();

    private final DefaultReportMessageSetting reportMessageSetting = new DefaultReportMessageSetting();

    @SettingSection("join")
    private final DefaultPasswordSetting passwordSetting = new DefaultPasswordSetting();

    private final DefaultCommandSetting commandSetting = new DefaultCommandSetting();

    @SwitchSetting
    private final ConfigProperty<Boolean> texturePack = new ConfigProperty<>(true);

    @SettingSection("automation")
    private final DefaultATMSetting atmSetting = new DefaultATMSetting();

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

    @SettingSection("equip")
    private final DefaultEquipSetting equipSetting = new DefaultEquipSetting();

    private final DefaultOwnUseSetting ownUseSetting = new DefaultOwnUseSetting();

    @SettingSection("others")
    @SwitchSetting
    private final ConfigProperty<Boolean> orderedTablist = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> despawnTime = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> addonTag = new ConfigProperty<>(true);

    @SettingSection("debug")
    @SwitchSetting
    private final ConfigProperty<Boolean> debug = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public NameTagSetting nameTagSetting() {
        return this.nameTagSetting;
    }

    @Override
    public HotkeySetting hotkeySetting() {
        return this.hotkeySetting;
    }

    @Override
    public ReinforcementSetting reinforcementSetting() {
        return this.reinforcementSetting;
    }

    @Override
    public SlocSetting slocSetting() {
        return this.slocSetting;
    }

    @Override
    public FactionMessageSetting factionMessageSetting() {
        return this.factionMessageSetting;
    }

    @Override
    public ReportMessageSetting reportMessageSetting() {
        return this.reportMessageSetting;
    }

    @Override
    public PasswordSetting passwordSetting() {
        return this.passwordSetting;
    }

    @Override
    public CommandSetting commandSetting() {
        return this.commandSetting;
    }

    @Override
    public ConfigProperty<Boolean> texturePack() {
        return this.texturePack;
    }

    @Override
    public ATMSetting atmSetting() {
        return this.atmSetting;
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
    public EquipSetting equipSetting() {
        return this.equipSetting;
    }

    @Override
    public OwnUseSetting ownUseSetting() {
        return this.ownUseSetting;
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
    public ConfigProperty<Boolean> addonTag() {
        return this.addonTag;
    }

    @Override
    public ConfigProperty<Boolean> debug() {
        return this.debug;
    }
}