package com.rettichlp.unicacityaddon.base.config.drug;

import com.rettichlp.unicacityaddon.base.config.drug.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.drug.cocaine.DefaultCocaine;
import com.rettichlp.unicacityaddon.base.config.drug.marijuana.DefaultMarijuana;
import com.rettichlp.unicacityaddon.base.config.drug.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.drug.methamphetamin.DefaultMethamphetamin;
import com.rettichlp.unicacityaddon.base.config.drug.methamphetamin.Methamphetamin;
import net.labymod.api.configuration.loader.Config;

/**
 * @author RettichLP
 */
public class DefaultDrugConfiguration extends Config implements DrugConfiguration {

    private final DefaultCocaine cocaine = new DefaultCocaine();

    private final DefaultMarijuana marijuana = new DefaultMarijuana();

    private final DefaultMethamphetamin methamphetamin = new DefaultMethamphetamin();

    @Override
    public Cocaine cocaine() {
        return this.cocaine;
    }

    @Override
    public Marijuana marijuana() {
        return this.marijuana;
    }

    @Override
    public Methamphetamin methamphetamin() {
        return this.methamphetamin;
    }
}