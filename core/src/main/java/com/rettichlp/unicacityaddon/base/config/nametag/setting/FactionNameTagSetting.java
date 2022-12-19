package com.rettichlp.unicacityaddon.base.config.nametag.setting;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface FactionNameTagSetting {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<ColorCode> color();
}
