package com.rettichlp.unicacityaddon.commands.mobile;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReplyCommand extends Command {

    private static final String usage = "/reply [Nachricht]";

    @Inject
    private ReplyCommand() {
    super("reply", "r");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String message = TextUtils.makeStringByArgs(arguments, " ");
        p.sendChatMessage("/sms " + MobileEventHandler.lastCheckedNumber + " " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}