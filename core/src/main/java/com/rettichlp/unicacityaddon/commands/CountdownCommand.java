package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ChatType;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class CountdownCommand extends Command {

    public static int countdown;

    private static final String usage = "/countdown [Sekunden] [Chat]";
    private static boolean active = false;

    public CountdownCommand() {
        super("countdown");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

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
                    p.sendChatMessage(chatType.getChatCommand() + " Start!");
                } else if (countdown == -1) {
                    timer.cancel();
                    active = false;
                    return;
                } else {
                    p.sendChatMessage(chatType.getChatCommand() + " " + countdown);
                }
                countdown--;
            }
        }, delay, 1000);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(2, Arrays.stream(ChatType.values()).map(ChatType::getDisplayName).sorted().collect(Collectors.toList()))
                .build();
    }
}