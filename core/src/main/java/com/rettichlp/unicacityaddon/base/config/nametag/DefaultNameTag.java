package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.setting.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultAlliance;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultFaction;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultSpecific;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.DefaultStreetwar;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Streetwar;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultNameTag extends Config implements NameTag {

    @SwitchSetting
    private final ConfigProperty<Boolean> factionInfo = new ConfigProperty<>(true);

    private final DefaultFaction factionNameTagSetting = new DefaultFaction();

    private final DefaultAlliance allianceFactionNameTagSetting = new DefaultAlliance();

    private final DefaultStreetwar streetwarNameTagSetting = new DefaultStreetwar();

    @SwitchSetting
    private final ConfigProperty<Boolean> houseBan = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> duty = new ConfigProperty<>(false);

    private final DefaultSpecific factionSpecificNameTagSetting = new DefaultSpecific();

    @SwitchSetting
    private final ConfigProperty<Boolean> corpse = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> noPushInfo = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> addonTag = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> factionInfo() {
        return this.factionInfo;
    }

    @Override
    public Faction faction() {
        return this.factionNameTagSetting;
    }

    @Override
    public Alliance alliance() {
        return this.allianceFactionNameTagSetting;
    }

    @Override
    public Streetwar streetwar() {
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
    public Specific specific() {
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

    @Override
    public ConfigProperty<Boolean> addonTag() {
        return this.addonTag;
    }
}