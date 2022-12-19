package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.setting.BadFactionSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultBadFactionSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultHitmanSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultMedicSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultNewsSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultStateSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.DefaultTerrorSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.HitmanSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.MedicSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.NewsSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.StateSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.TerrorSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

public class DefaultEquipSetting extends Config implements EquipSetting {

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
    private final DefaultStateSetting stateSetting = new DefaultStateSetting();

    private final DefaultBadFactionSetting badFactionSetting = new DefaultBadFactionSetting();

    private final DefaultMedicSetting medicSetting = new DefaultMedicSetting();

    private final DefaultNewsSetting newsSetting = new DefaultNewsSetting();

    private final DefaultHitmanSetting hitmanSetting = new DefaultHitmanSetting();

    private final DefaultTerrorSetting terrorSetting = new DefaultTerrorSetting();

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
    public StateSetting stateSetting() {
        return null;
    }

    @Override
    public BadFactionSetting badFactionSetting() {
        return this.badFactionSetting;
    }

    @Override
    public MedicSetting medicSetting() {
        return this.medicSetting;
    }

    @Override
    public NewsSetting newsSetting() {
        return this.newsSetting;
    }

    @Override
    public HitmanSetting hitmanSetting() {
        return this.hitmanSetting;
    }

    @Override
    public TerrorSetting terrorSetting() {
        return this.terrorSetting;
    }
}