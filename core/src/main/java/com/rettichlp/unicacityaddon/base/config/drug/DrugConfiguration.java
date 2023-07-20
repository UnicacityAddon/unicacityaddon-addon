package com.rettichlp.unicacityaddon.base.config.drug;

import com.rettichlp.unicacityaddon.base.config.drug.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.drug.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.drug.methamphetamin.Methamphetamin;

/**
 * @author RettichLP
 */
public interface DrugConfiguration {

    Cocaine cocaine();

    Marijuana marijuana();

    Methamphetamin methamphetamin();
}