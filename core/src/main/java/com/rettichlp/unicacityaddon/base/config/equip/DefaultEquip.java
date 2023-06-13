package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.setting.BadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultBadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultHitman;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultMedic;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultNews;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultState;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultTerror;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Hitman;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Medic;
import com.rettichlp.unicacityaddon.base.config.equip.setting.News;
import com.rettichlp.unicacityaddon.base.config.equip.setting.State;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Terror;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/**
 * @author RettichLP
 */
public class DefaultEquip extends Config implements Equip {

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
    private final DefaultState stateSetting = new DefaultState();

    private final DefaultBadFaction badFactionSetting = new DefaultBadFaction();

    private final DefaultMedic medicSetting = new DefaultMedic();

    private final DefaultNews newsSetting = new DefaultNews();

    private final DefaultHitman hitmanSetting = new DefaultHitman();

    private final DefaultTerror terrorSetting = new DefaultTerror();

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
        return this.stateSetting;
    }

    @Override
    public BadFaction badFaction() {
        return this.badFactionSetting;
    }

    @Override
    public Medic medic() {
        return this.medicSetting;
    }

    @Override
    public News news() {
        return this.newsSetting;
    }

    @Override
    public Hitman hitman() {
        return this.hitmanSetting;
    }

    @Override
    public Terror terror() {
        return this.terrorSetting;
    }
}