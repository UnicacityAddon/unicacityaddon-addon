package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CalculateCommand extends UnicacityCommand {

    private static final String usage = "/calculate [mathematischer Ausdruck]";

    private final UnicacityAddon unicacityAddon;

    public CalculateCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "calculate", false, "calc", "rechner");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String mathString = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ");
        MathUtils mathUtils = new MathUtils(mathString);
        try {
            mathUtils.evaluate();
        } catch (MathUtils.ExpressionException e) {
            p.sendErrorMessage("Der eingegebene mathematische Ausdruck konnte nicht evaluiert werden: " + e.getMessage());
        }

        p.sendMessage(Message.getBuilder()
                .prefix()
                .of(mathString).color(ColorCode.AQUA).advance().space()
                .of("=").color(ColorCode.WHITE).advance().space()
                .of(mathUtils.parse()).color(ColorCode.AQUA).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}