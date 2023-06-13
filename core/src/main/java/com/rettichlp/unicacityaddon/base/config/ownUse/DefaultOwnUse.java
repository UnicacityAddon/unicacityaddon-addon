package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultCocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultMarijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.DefaultMethamphetamin;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Methamphetamin;
import net.labymod.api.configuration.loader.Config;

/**
 * @author RettichLP
 */
public class DefaultOwnUse extends Config implements OwnUse {

    private final DefaultCocaine kokainSetting = new DefaultCocaine();

    private final DefaultMarijuana marihuanaSetting = new DefaultMarijuana();

    private final DefaultMethamphetamin methamphetaminSetting = new DefaultMethamphetamin();

    @Override
    public Cocaine cocaine() {
        return this.kokainSetting;
    }

    @Override
    public Marijuana marijuana() {
        return this.marihuanaSetting;
    }

    @Override
    public Methamphetamin methamphetamin() {
        return this.methamphetaminSetting;
    }
}