package com.rettichlp.unicacityaddon.base.config.reinforcement;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultReinforcement extends Config implements Reinforcement {

    public static final String REINFORCEMENT = Message.getBuilder()
            .of("%type%").color(ColorCode.RED).bold().advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%navipoint%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%distance%" + "m").color(ColorCode.DARK_AQUA).advance().create();

    public static final String ANSWER = Message.getBuilder()
            .of("➥").color(ColorCode.GRAY).advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("➡").color(ColorCode.GRAY).advance().space()
            .of("%target%").color(ColorCode.DARK_AQUA).advance().space()
            .of("- (").color(ColorCode.GRAY).advance()
            .of("%distance%" + "m").color(ColorCode.DARK_AQUA).advance()
            .of(")").color(ColorCode.GRAY).advance().create();
    @TextFieldSetting
    private final ConfigProperty<String> reinforcement = new ConfigProperty<>(REINFORCEMENT);

    @TextFieldSetting
    private final ConfigProperty<String> answer = new ConfigProperty<>(ANSWER);

    @SwitchSetting
    private final ConfigProperty<Boolean> screen = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<String> reinforcement() {
        return this.reinforcement;
    }

    @Override
    public ConfigProperty<String> answer() {
        return this.answer;
    }

    @Override
    public ConfigProperty<Boolean> screen() {
        return this.screen;
    }
}