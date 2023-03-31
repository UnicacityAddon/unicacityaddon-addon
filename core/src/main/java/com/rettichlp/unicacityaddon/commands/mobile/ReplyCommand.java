package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReplyCommand extends Command {

    private static final String usage = "/reply [Nachricht]";

    private UnicacityAddon unicacityAddon;

    public ReplyCommand(UnicacityAddon unicacityAddon) {
    super("reply", "r");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String message = TextUtils.makeStringByArgs(arguments, " ");
        p.sendServerMessage("/sms " + MobileListener.lastCheckedNumber + " " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}