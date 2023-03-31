package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSParser;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.TargetMode;

/**
 * @author Fuzzlemann
 */
public class SendTextMessageCommand extends BaseCommand<CommandResponse> {

    public SendTextMessageCommand(UnicacityAddon unicacityAddon, Client target, String message) {
        this(unicacityAddon, target.getClientID(), message);
    }

    public SendTextMessageCommand(UnicacityAddon unicacityAddon, int targetID, String message) {
        super(unicacityAddon, "sendtextmessage targetmode=" + TargetMode.PRIVATE.getID() + " target=" + targetID + " msg=" + TSParser.encode(message));
    }

    public SendTextMessageCommand(UnicacityAddon unicacityAddon, TargetMode targetMode, String message) {
        super(unicacityAddon, "sendtextmessage targetmode=" + targetMode.getID() + " msg=" + TSParser.encode(message));
    }
}
