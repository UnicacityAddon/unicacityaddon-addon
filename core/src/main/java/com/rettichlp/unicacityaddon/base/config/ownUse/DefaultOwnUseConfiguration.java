package com.rettichlp.unicacityaddon.base.config.ownUse;

import com.rettichlp.unicacityaddon.base.config.ownUse.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.cocaine.DefaultCocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.marijuana.DefaultMarijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.methamphetamin.DefaultMethamphetamin;
import com.rettichlp.unicacityaddon.base.config.ownUse.methamphetamin.Methamphetamin;
import net.labymod.api.configuration.loader.Config;

/**
 * @author RettichLP
 */
public class DefaultOwnUseConfiguration extends Config implements OwnUseConfiguration {

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