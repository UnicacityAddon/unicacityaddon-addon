package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "reply", aliases = {"r"}, usage = "[Nachricht]")
public class ReplyCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ReplyCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        String message = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ");
        p.sendServerMessage("/sms " + MobileListener.lastCheckedNumber + " " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}