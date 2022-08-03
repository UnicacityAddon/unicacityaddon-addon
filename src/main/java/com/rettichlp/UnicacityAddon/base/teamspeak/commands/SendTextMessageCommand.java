package com.rettichlp.UnicacityAddon.base.teamspeak.commands;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSParser;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Client;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.TargetMode;

/**
 * @author Fuzzlemann
 */
public class SendTextMessageCommand extends BaseCommand<CommandResponse> {
    public SendTextMessageCommand(Client target, String message) {
        this(target.getClientID(), message);
    }

    public SendTextMessageCommand(int targetID, String message) {
        super("sendtextmessage targetmode=" + TargetMode.PRIVATE.getID() + " target=" + targetID + " msg=" + TSParser.encode(message));
    }

    public SendTextMessageCommand(TargetMode targetMode, String message) {
        super("sendtextmessage targetmode=" + targetMode.getID() + " msg=" + TSParser.encode(message));
    }
}
