package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.methamphetamin.Methamphetamin;

/**
 * @author RettichLP
 */
public interface OwnUseConfiguration {

    Cocaine cocaine();

    Marijuana marijuana();

    Methamphetamin methamphetamin();
}