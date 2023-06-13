package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.Methamphetamin;

/**
 * @author RettichLP
 */
public interface OwnUse {

    Cocaine cocaine();

    Marijuana marijuana();

    Methamphetamin methamphetamin();
}