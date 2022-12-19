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
    private final ConfigProperty<Integer> water = new ConfigProperty<>(0);

    @TextFieldSetting
    private final ConfigProperty<Integer> bread = new ConfigProperty<>(40);

    @TextFieldSetting
    private final ConfigProperty<Integer> donut = new ConfigProperty<>(30);

    @TextFieldSetting
    private final ConfigProperty<Integer> mask = new ConfigProperty<>(800);

    @TextFieldSetting
    private final ConfigProperty<Integer> lkev = new ConfigProperty<>(1450);

    @TextFieldSetting
    private final ConfigProperty<Integer> skev = new ConfigProperty<>(2200);

    @TextFieldSetting
    private final ConfigProperty<Integer> pepperSpray = new ConfigProperty<>(400);

    @TextFieldSetting
    private final ConfigProperty<Integer> pistol = new ConfigProperty<>(350);

    @TextFieldSetting
    private final ConfigProperty<Integer> mp5 = new ConfigProperty<>(550);

    @TextFieldSetting
    private final ConfigProperty<Integer> sniper = new ConfigProperty<>(2700);

    @SettingSection("specific")
    private final DefaultStateSetting stateSetting = new DefaultStateSetting();

    private final DefaultBadFactionSetting badFactionSetting = new DefaultBadFactionSetting();

    private final DefaultMedicSetting medicSetting = new DefaultMedicSetting();

    private final DefaultNewsSetting newsSetting = new DefaultNewsSetting();

    private final DefaultHitmanSetting hitmanSetting = new DefaultHitmanSetting();

    private final DefaultTerrorSetting terrorSetting = new DefaultTerrorSetting();

    @Override
    public ConfigProperty<Integer> water() {
        return this.water;
    }

    @Override
    public ConfigProperty<Integer> bread() {
        return this.bread;
    }

    @Override
    public ConfigProperty<Integer> donut() {
        return this.donut;
    }

    @Override
    public ConfigProperty<Integer> mask() {
        return this.mask;
    }

    @Override
    public ConfigProperty<Integer> lkev() {
        return this.lkev;
    }

    @Override
    public ConfigProperty<Integer> skev() {
        return this.skev;
    }

    @Override
    public ConfigProperty<Integer> pepperSpray() {
        return this.pepperSpray;
    }

    @Override
    public ConfigProperty<Integer> pistol() {
        return this.pistol;
    }

    @Override
    public ConfigProperty<Integer> mp5() {
        return this.mp5;
    }

    @Override
    public ConfigProperty<Integer> sniper() {
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