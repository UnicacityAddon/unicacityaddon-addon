package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.entries.TimerEntry;
import com.rettichlp.UnicacityAddon.base.api.request.TabCompletionBuilder;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 */
@UCCommand
public class TimerCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "timer";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/timer (start|stop) (Name|ID) (Zeit<h/m/s>)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "start", "stop")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (TimerEntry.ACTIVE_TIMERS == null) TimerEntry.ACTIVE_TIMERS = new HashMap<>();

        if (args.length == 0) {
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
        } else if (args.length == 3 && args[0].equalsIgnoreCase("start")) {
            new TimerEntry(args[1], args[2]).start();
            p.sendInfoMessage("Timer gestartet.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("stop") && MathUtils.isInteger(args[1])) {
            TimerEntry.ACTIVE_TIMERS.get(Long.parseLong(args[1])).stop();
            p.sendInfoMessage("Timer gestoppt.");
       } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}