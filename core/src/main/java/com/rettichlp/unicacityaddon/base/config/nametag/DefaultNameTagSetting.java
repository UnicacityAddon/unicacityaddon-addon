package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultAllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultSpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultStreetwarNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultNameTagSetting extends Config implements NameTagSetting {

    @SwitchSetting
    private final ConfigProperty<Boolean> factionInfo = new ConfigProperty<>(true);

    private final DefaultFactionNameTagSetting factionNameTagSetting = new DefaultFactionNameTagSetting();

    private final DefaultAllianceFactionNameTagSetting allianceFactionNameTagSetting = new DefaultAllianceFactionNameTagSetting();

    private final DefaultStreetwarNameTagSetting streetwarNameTagSetting = new DefaultStreetwarNameTagSetting();

    @SwitchSetting
    private final ConfigProperty<Boolean> houseBan = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> duty = new ConfigProperty<>(false);

    private final DefaultSpecificNameTagSetting factionSpecificNameTagSetting = new DefaultSpecificNameTagSetting();

    @SwitchSetting
    private final ConfigProperty<Boolean> corpse = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> noPushInfo = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> factionInfo() {
        return this.factionInfo;
    }

    @Override
    public FactionNameTagSetting factionNameTagSetting() {
        return this.factionNameTagSetting;
    }

    @Override
    public AllianceFactionNameTagSetting allianceFactionNameTagSetting() {
        return this.allianceFactionNameTagSetting;
    }

    @Override
    public StreetwarNameTagSetting streetwarNameTagSetting() {
        return this.streetwarNameTagSetting;
    }

    @Override
    public ConfigProperty<Boolean> houseBan() {
        return this.houseBan;
    }

    @Override
    public ConfigProperty<Boolean> duty() {
        return this.duty;
    }

    @Override
    public SpecificNameTagSetting specificNameTagSetting() {
        return this.factionSpecificNameTagSetting;
    }

    @Override
    public ConfigProperty<Boolean> corpse() {
        return this.corpse;
    }

    @Override
    public ConfigProperty<Boolean> noPushInfo() {
        return this.noPushInfo;
    }
}