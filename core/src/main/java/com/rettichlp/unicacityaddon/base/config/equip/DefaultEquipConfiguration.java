package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.badfaction.BadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.badfaction.DefaultBadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.hitman.DefaultHitman;
import com.rettichlp.unicacityaddon.base.config.equip.medic.DefaultMedic;
import com.rettichlp.unicacityaddon.base.config.equip.news.DefaultNews;
import com.rettichlp.unicacityaddon.base.config.equip.state.DefaultState;
import com.rettichlp.unicacityaddon.base.config.equip.terrorist.DefaultTerrorist;
import com.rettichlp.unicacityaddon.base.config.equip.hitman.Hitman;
import com.rettichlp.unicacityaddon.base.config.equip.medic.Medic;
import com.rettichlp.unicacityaddon.base.config.equip.news.News;
import com.rettichlp.unicacityaddon.base.config.equip.state.State;
import com.rettichlp.unicacityaddon.base.config.equip.terrorist.Terrorist;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/**
 * @author RettichLP
 */
public class DefaultEquipConfiguration extends Config implements EquipConfiguration {

    @SettingSection("global")
    @TextFieldSetting
    private final ConfigProperty<String> water = new ConfigProperty<>("0");

    @TextFieldSetting
    private final ConfigProperty<String> bread = new ConfigProperty<>("40");

    @TextFieldSetting
    private final ConfigProperty<String> donut = new ConfigProperty<>("30");

    @TextFieldSetting
    private final ConfigProperty<String> mask = new ConfigProperty<>("800");

    @TextFieldSetting
    private final ConfigProperty<String> lkev = new ConfigProperty<>("1450");

    @TextFieldSetting
    private final ConfigProperty<String> skev = new ConfigProperty<>("2200");

    @TextFieldSetting
    private final ConfigProperty<String> pepperSpray = new ConfigProperty<>("400");

    @TextFieldSetting
    private final ConfigProperty<String> pistol = new ConfigProperty<>("350");

    @TextFieldSetting
    private final ConfigProperty<String> mp5 = new ConfigProperty<>("550");

    @TextFieldSetting
    private final ConfigProperty<String> sniper = new ConfigProperty<>("2700");

    @SettingSection("specific")
    private final DefaultState state = new DefaultState();

    private final DefaultBadFaction badFaction = new DefaultBadFaction();

    private final DefaultMedic medic = new DefaultMedic();

    private final DefaultNews news = new DefaultNews();

    private final DefaultHitman hitman = new DefaultHitman();

    private final DefaultTerrorist terrorist = new DefaultTerrorist();

    @Override
    public ConfigProperty<String> water() {
        return this.water;
    }

    @Override
    public ConfigProperty<String> bread() {
        return this.bread;
    }

    @Override
    public ConfigProperty<String> donut() {
        return this.donut;
    }

    @Override
    public ConfigProperty<String> mask() {
        return this.mask;
    }

    @Override
    public ConfigProperty<String> lkev() {
        return this.lkev;
    }

    @Override
    public ConfigProperty<String> skev() {
        return this.skev;
    }

    @Override
    public ConfigProperty<String> pepperSpray() {
        return this.pepperSpray;
    }

    @Override
    public ConfigProperty<String> pistol() {
        return this.pistol;
    }

    @Override
    public ConfigProperty<String> mp5() {
        return this.mp5;
    }

    @Override
    public ConfigProperty<String> sniper() {
        return this.sniper;
    }

    @Override
    public State state() {
        return this.state;
    }

    @Override
    public BadFaction badFaction() {
        return this.badFaction;
    }

    @Override
    public Medic medic() {
        return this.medic;
    }

    @Override
    public News news() {
        return this.news;
    }

    @Override
    public Hitman hitman() {
        return this.hitman;
    }

    @Override
    public Terrorist terrorist() {
        return this.terrorist;
    }
}