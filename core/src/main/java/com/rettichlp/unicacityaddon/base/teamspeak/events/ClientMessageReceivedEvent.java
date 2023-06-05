package com.rettichlp.unicacityaddon.base.teamspeak.events;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.TargetMode;
import lombok.Getter;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
@Getter
@TSEvent.Name("notifytextmessage")
public class ClientMessageReceivedEvent extends TSEvent {

    private final TargetMode targetMode;
    private final String message;
    private final int invokerID;
    private final String invokerName;
    private final String invokerUniqueID;
    private final int targetID;

    public ClientMessageReceivedEvent(String input) {
        super(input);

        this.targetMode = TargetMode.byID(CommandResponse.parseInt(map.get("targetmode")));
        this.message = map.get("msg");
        this.invokerID = CommandResponse.parseInt(map.get("invokerid"));
        this.invokerName = map.get("invokername");
        this.invokerUniqueID = map.get("invokeruid");
        this.targetID = CommandResponse.parseInt(map.get("targetid"));
    }
}
