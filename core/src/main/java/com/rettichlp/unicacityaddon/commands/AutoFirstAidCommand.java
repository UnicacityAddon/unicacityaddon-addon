package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "autofirstaid", usage = "[on|off]")
public class AutoFirstAidCommand extends UnicacityCommand {

    public static boolean autoAcceptFirstAid = false;

    private final UnicacityAddon unicacityAddon;

    public AutoFirstAidCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1 || !arguments[0].matches("on|off")) {
            sendUsage();
            return true;
        }

        autoAcceptFirstAid = switch (arguments[0]) {
            case "on" -> true;
            case "off" -> false;
            default -> throw new IllegalStateException("Unexpected value: " + arguments[0]);
        };

        p.sendInfoMessage("Du hast die Einstellung für die automatische Annahme von Erster Hilfe auf '" + ColorCode.DARK_AQUA.getCode() + arguments[0].toUpperCase() + ColorCode.WHITE.getCode() + "' geändert.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "on", "off")
                .build();
    }
}