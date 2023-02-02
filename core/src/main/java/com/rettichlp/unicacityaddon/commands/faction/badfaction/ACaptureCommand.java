package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCCommand
public class ACaptureCommand extends Command {

    public static boolean isActive;

    private static final String usage = "/capture";

    public ACaptureCommand() {
        super("capture");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (isActive)
            return true;

        AbstractionLayer.getPlayer().sendChatMessage("/capture");
        isActive = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AbstractionLayer.getPlayer().sendChatMessage("/capture");
                isActive = false;
            }
        }, TimeUnit.SECONDS.toMillis(15));
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}