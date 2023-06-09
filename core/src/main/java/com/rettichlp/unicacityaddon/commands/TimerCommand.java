package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.file.TimerEntry;
import net.labymod.api.client.component.event.ClickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "timer", usage = "(start|stop) (Name|ID) (Zeit)(h|m|s)")
public class TimerCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TimerCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
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
            new TimerEntry(arguments[1], arguments[2]).start(this.unicacityAddon);
            p.sendInfoMessage("Timer gestartet.");
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("stop") && MathUtils.isInteger(arguments[1])) {
            TimerEntry.ACTIVE_TIMERS.get(Long.parseLong(arguments[1])).stop();
            p.sendInfoMessage("Timer gestoppt.");
        } else {
            sendUsage();
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "start", "stop")
                .build();
    }
}