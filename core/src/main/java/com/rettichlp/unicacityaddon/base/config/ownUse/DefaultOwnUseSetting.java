package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultKokainSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultMarihuanaSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultMethamphetaminSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.KokainSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MarihuanaSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MethamphetaminSetting;
import net.labymod.api.configuration.loader.Config;

public class DefaultOwnUseSetting extends Config implements OwnUseSetting {

    private final DefaultKokainSetting kokainSetting = new DefaultKokainSetting();

    private final DefaultMarihuanaSetting marihuanaSetting = new DefaultMarihuanaSetting();

    private final DefaultMethamphetaminSetting methamphetaminSetting = new DefaultMethamphetaminSetting();

    @Override
    public KokainSetting kokainSetting() {
        return this.kokainSetting;
    }

    @Override
    public MarihuanaSetting marihuanaSetting() {
        return this.marihuanaSetting;
    }

    @Override
    public MethamphetaminSetting methamphetaminSetting() {
        return this.methamphetaminSetting;
    }
}