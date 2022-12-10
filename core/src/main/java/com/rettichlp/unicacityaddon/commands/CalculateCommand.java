package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CalculateCommand extends Command {

    private static final String usage = "/calculate [mathematischer Ausdruck]";

    @Inject
    private CalculateCommand() {
    super("calculate", "calc", "rechner");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String mathString = TextUtils.makeStringByArgs(arguments, " ");
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
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}