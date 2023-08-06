package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.atm.DefaultATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.drug.DefaultDrugConfiguration;
import com.rettichlp.unicacityaddon.base.config.drug.DrugConfiguration;
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
import com.rettichlp.unicacityaddon.base.config.reinforcement.DefaultReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSlocConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocConfiguration;
import com.rettichlp.unicacityaddon.base.config.tablist.DefaultTabListConfiguration;
import com.rettichlp.unicacityaddon.base.config.tablist.TabListConfiguration;
import com.rettichlp.unicacityaddon.base.config.teamspeak.DefaultTeamSpeakConfiguration;
import com.rettichlp.unicacityaddon.base.config.teamspeak.TeamSpeakConfiguration;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
@SpriteTexture("settings.png")
public class DefaultUnicacityAddonConfiguration extends AddonConfig implements UnicacityAddonConfiguration {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 1)
    private final DefaultHotkeyConfiguration hotkey = new DefaultHotkeyConfiguration();

    @SettingSection("nametag")
    @SpriteSlot(x = 2)
    private final DefaultNameTagConfiguration nametag = new DefaultNameTagConfiguration();

    @SettingSection("faction")
    @SpriteSlot(x = 3)
    private final DefaultReinforcementConfiguration reinforcement = new DefaultReinforcementConfiguration();

    @SpriteSlot(x = 4)
    private final DefaultSlocConfiguration sloc = new DefaultSlocConfiguration();

    @SpriteSlot(x = 5)
    private final DefaultEquipConfiguration equip = new DefaultEquipConfiguration();

    @SpriteSlot(x = 6)
    private final DefaultDrugConfiguration drug = new DefaultDrugConfiguration();

    @SettingSection("message")
    @SpriteSlot(x = 7)
    private final DefaultMessageConfiguration message = new DefaultMessageConfiguration();

    @SettingSection("join")
    @SpriteSlot(y = 1)
    private final DefaultPasswordConfiguration password = new DefaultPasswordConfiguration();

    @SpriteSlot(x = 1, y = 1)
    private final DefaultCommandConfiguration command = new DefaultCommandConfiguration();

    @SwitchSetting
    @SpriteSlot(x = 2, y = 1)
    private final ConfigProperty<Boolean> texturePack = new ConfigProperty<>(true);

    @SwitchSetting
    @SpriteSlot(x = 3, y = 1)
    private final ConfigProperty<Boolean> hitSound = new ConfigProperty<>(false);

    @SettingSection("automation")
    @SpriteSlot(x = 4, y = 1)
    private final DefaultATMConfiguration atm = new DefaultATMConfiguration();

    @SwitchSetting
    @SpriteSlot(x = 5, y = 1)
    private final ConfigProperty<Boolean> bombScreenshot = new ConfigProperty<>(true);

    @SwitchSetting
    @SpriteSlot(x = 6, y = 1)
    private final ConfigProperty<Boolean> carRoute = new ConfigProperty<>(true);

    @SettingSection("other")
    @SpriteSlot(x = 7, y = 1)
    private final DefaultTeamSpeakConfiguration teamspeak = new DefaultTeamSpeakConfiguration();

    @SwitchSetting
    @SpriteSlot(y = 2)
    private final DefaultTabListConfiguration tablist = new DefaultTabListConfiguration();

    @SwitchSetting
    @SpriteSlot(x = 1, y = 2)
    private final ConfigProperty<Boolean> despawnTime = new ConfigProperty<>(true);

    @SettingSection("debug")
    @SwitchSetting
    private final ConfigProperty<Boolean> debug = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> local = new ConfigProperty<>(false);

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
    public DrugConfiguration drug() {
        return this.drug;
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
    public ConfigProperty<Boolean> hitSound() {
        return this.hitSound;
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
    public TeamSpeakConfiguration teamspeak() {
        return this.teamspeak;
    }

    @Override
    public TabListConfiguration tablist() {
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

    @Override
    public ConfigProperty<Boolean> local() {
        return this.local;
    }
}