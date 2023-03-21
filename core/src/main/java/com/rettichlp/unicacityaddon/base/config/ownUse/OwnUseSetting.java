package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.setting.KokainSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MarihuanaSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MethamphetaminSetting;

/**
 * @author RettichLP
 */
public interface OwnUseSetting {

    KokainSetting kokainSetting();

    MarihuanaSetting marihuanaSetting();

    MethamphetaminSetting methamphetaminSetting();
}