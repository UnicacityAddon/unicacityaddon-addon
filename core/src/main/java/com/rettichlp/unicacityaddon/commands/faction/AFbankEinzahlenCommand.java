package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Dimiikou
 */
@UCCommand
public class AFbankEinzahlenCommand implements IClientCommand {

    public static final AtomicBoolean STARTED = new AtomicBoolean();

    public static final Timer timer = new Timer();
    public static int amount;

    @Override
    @Nonnull
    public String getName() {
        return "afbank";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/afbank [einzahlen/auszahlen] [Betrag]";
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
        if (args.length != 2 || !MathUtils.isInteger(args[1])) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        String interaction = args[0];

        if (STARTED.get()) return;

        if (!interaction.equalsIgnoreCase("einzahlen") && !interaction.equalsIgnoreCase("auszahlen")) return;

        // check if there are taxes
        p.sendChatMessage("/fbank " + interaction + " 4");
        amount = Integer.parseInt(args[1]) - 4; // we already paid 4$

        STARTED.set(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!STARTED.get()) {
                    cancel();
                    return;
                }

                if (amount > 1000) {
                    // if amount is bigger than 1000, add or remove 1k from faction bank and wait
                    p.sendChatMessage("/fbank " + interaction + " 1000");
                    amount -= 1000;
                } else {
                    // otherwise add or remove the remainder and stop the task
                    p.sendChatMessage("/fbank " + interaction + " " + amount);
                    STARTED.set(false);
                    cancel();

                    // send clock command
                    timer.schedule(new TimerTask() {
                        public void run() {
                            sendClockMessage();
                        }
                    }, 200L);
                }
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "einzahlen", "auszahlen")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    public static void sendClockMessage() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.GERMAN);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMMM yyyy", Locale.GERMAN);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss 'Uhr'");

        Date date = new Date();

        String dayString = dayFormat.format(date);
        String dateString = dateFormat.format(date);
        String timeString = timeFormat.format(date);

        Message.getBuilder()
                .prefix()
                .of("Heute ist ").color(ColorCode.GRAY).advance()
                .of(dayString).color(ColorCode.BLUE).advance()
                .of(", der ").color(ColorCode.GRAY).advance()
                .of(dateString).color(ColorCode.BLUE).advance()
                .of(" und wir haben ").color(ColorCode.GRAY).advance()
                .of(timeString).color(ColorCode.BLUE).advance()
                .of(".").color(ColorCode.GRAY).advance()
                .sendTo(AbstractionLayer.getPlayer().getPlayer());
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