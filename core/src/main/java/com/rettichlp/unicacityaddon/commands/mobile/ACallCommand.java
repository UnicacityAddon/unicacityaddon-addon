package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCCommand
public class ACallCommand extends Command {

    public static boolean isActive;

    private static final String usage = "/acall [Spielername]";
    private final Timer timer = new Timer();

    public ACallCommand() {
        super("acall");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        isActive = true;
        p.sendServerMessage("/nummer " + arguments[0]);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int number = MobileEventHandler.lastCheckedNumber;
                if (number == 0) {
                    p.sendErrorMessage("Der Spieler wurde nicht gefunden!");
                    return;
                }

                p.sendServerMessage("/call " + number);
            }
        }, 250L);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}