package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ChatType;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class CountdownCommand implements IClientCommand {

    private static boolean active = false;
    public static int countdown;

    @Override
    @Nonnull
    public String getName() {
        return "countdown";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/countdown [Sekunden] [Chat]";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (active) {
            p.sendErrorMessage("Es ist gerade schon ein Countdown aktiv!");
            return;
        }

        if (args.length < 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        if (!MathUtils.isInteger(args[0])) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        countdown = Integer.parseInt(args[0]);

        if (countdown < 1) {
            p.sendErrorMessage("Der Countdown darf nicht bei 0 starten!");
            return;
        }

        ChatType chatType = ChatType.getChatTypeByDisplayName(args[1]);
        if (chatType == null) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
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
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Arrays.stream(ChatType.values()).map(ChatType::getDisplayName).sorted().collect(Collectors.toList());
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
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