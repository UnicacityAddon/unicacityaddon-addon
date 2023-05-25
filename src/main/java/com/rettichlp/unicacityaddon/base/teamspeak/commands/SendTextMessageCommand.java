package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSParser;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.TargetMode;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
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
