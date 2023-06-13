package com.rettichlp.unicacityaddon.base.config.sloc;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultSlocConfiguration extends Config implements SlocConfiguration {

    public static final String SLOC = Message.getBuilder()
            .of("Position!").color(ColorCode.RED).bold().advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%x%").color(ColorCode.AQUA).advance().space()
            .of("|").color(ColorCode.GRAY).advance().space()
            .of("%y%").color(ColorCode.AQUA).advance().space()
            .of("|").color(ColorCode.GRAY).advance().space()
            .of("%z%").color(ColorCode.AQUA).advance().create();

    @TextFieldSetting
    private final ConfigProperty<String> sloc = new ConfigProperty<>(SLOC);

    @Override
    public ConfigProperty<String> sloc() {
        return this.sloc;
    }
}