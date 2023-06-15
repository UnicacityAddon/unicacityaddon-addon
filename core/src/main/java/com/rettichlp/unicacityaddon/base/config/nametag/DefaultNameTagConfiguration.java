package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.alliance.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.alliance.DefaultAlliance;
import com.rettichlp.unicacityaddon.base.config.nametag.faction.DefaultFaction;
import com.rettichlp.unicacityaddon.base.config.nametag.faction.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.specific.DefaultSpecific;
import com.rettichlp.unicacityaddon.base.config.nametag.specific.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.streetwar.DefaultStreetwar;
import com.rettichlp.unicacityaddon.base.config.nametag.streetwar.Streetwar;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultNameTagConfiguration extends Config implements NameTagConfiguration {

    @SwitchSetting
    private final ConfigProperty<Boolean> info = new ConfigProperty<>(true);

    private final DefaultFaction faction = new DefaultFaction();

    private final DefaultAlliance alliance = new DefaultAlliance();

    private final DefaultStreetwar streetwar = new DefaultStreetwar();

    @SwitchSetting
    private final ConfigProperty<Boolean> houseban = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> duty = new ConfigProperty<>(false);

    private final DefaultSpecific specific = new DefaultSpecific();

    @SwitchSetting
    private final ConfigProperty<Boolean> corpse = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> afk = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> team = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> info() {
        return this.info;
    }

    @Override
    public Faction faction() {
        return this.faction;
    }

    @Override
    public Alliance alliance() {
        return this.alliance;
    }

    @Override
    public Streetwar streetwar() {
        return this.streetwar;
    }

    @Override
    public ConfigProperty<Boolean> houseban() {
        return this.houseban;
    }

    @Override
    public ConfigProperty<Boolean> duty() {
        return this.duty;
    }

    @Override
    public Specific specific() {
        return this.specific;
    }

    @Override
    public ConfigProperty<Boolean> corpse() {
        return this.corpse;
    }

    @Override
    public ConfigProperty<Boolean> afk() {
        return this.afk;
    }

    @Override
    public ConfigProperty<Boolean> team() {
        return this.team;
    }
}