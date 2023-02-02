package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.TimerEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 */
@UCCommand
public class TimerCommand extends Command {

    private static final String usage = "/timer (start|stop) (Name|ID) (Zeit<h/m/s>)";

    public TimerCommand() {
        super("timer");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (TimerEntry.ACTIVE_TIMERS == null)
            TimerEntry.ACTIVE_TIMERS = new HashMap<>();

        if (arguments.length == 0) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Aktive Timer:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            AtomicInteger id = new AtomicInteger();
            TimerEntry.ACTIVE_TIMERS.values().forEach(timerEntry -> {
                id.getAndIncrement();
                p.sendMessage(Message.getBuilder()
                        .of("» " + id + ". ").color(ColorCode.GRAY).advance()
                        .of(timerEntry.getName() + " (noch").color(ColorCode.AQUA).advance().space()
                        .of(timerEntry.getTimeLeftString()).color(ColorCode.DARK_AQUA).advance()
                        .of(")").color(ColorCode.AQUA).advance().space()
                        .of("[✕]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/timer stop " + timerEntry.getId())
                                .advance()
                        .createComponent());
            });
            p.sendEmptyMessage();
        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("start")) {
            new TimerEntry(arguments[1], arguments[2]).start();
            p.sendInfoMessage("Timer gestartet.");
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("stop") && MathUtils.isInteger(arguments[1])) {
            TimerEntry.ACTIVE_TIMERS.get(Long.parseLong(arguments[1])).stop();
            p.sendInfoMessage("Timer gestoppt.");
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "start", "stop")
                .build();
    }
}