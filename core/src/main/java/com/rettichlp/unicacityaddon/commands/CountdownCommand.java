package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ChatType;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class CountdownCommand extends UnicacityCommand {

    public static int countdown;

    private static final String usage = "/countdown [Sekunden] [Chat]";
    private static boolean active = false;

    private final UnicacityAddon unicacityAddon;

    public CountdownCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "countdown", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (active) {
            p.sendErrorMessage("Es ist gerade schon ein Countdown aktiv!");
            return true;
        }

        if (arguments.length < 1 || !MathUtils.isInteger(arguments[0])) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        countdown = Integer.parseInt(arguments[0]);

        if (countdown < 1) {
            p.sendErrorMessage("Der Countdown darf nicht bei 0 starten!");
            return true;
        }

        ChatType chatType = arguments.length == 1 ? ChatType.CHAT : ChatType.getChatTypeByDisplayName(arguments[1]);
        if (chatType == null) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        active = true;
        int delay = 0;

        if (chatType.equals(ChatType.ADMIN)) {
            p.sendInfoMessage("In 10 Sekunden wird ein Countdown mittels /o gestartet! Du kannst diesen mit /cancelcountdown abbrechen.");
            delay = 10000;
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countdown == 0) {
                    p.sendServerMessage(chatType.getChatCommand() + " Start!");
                } else if (countdown == -1) {
                    timer.cancel();
                    active = false;
                    return;
                } else {
                    p.sendServerMessage(chatType.getChatCommand() + " " + countdown);
                }
                countdown--;
            }
        }, delay, 1000);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, Arrays.stream(ChatType.values()).map(ChatType::getDisplayName).sorted().collect(Collectors.toList()))
                .build();
    }
}