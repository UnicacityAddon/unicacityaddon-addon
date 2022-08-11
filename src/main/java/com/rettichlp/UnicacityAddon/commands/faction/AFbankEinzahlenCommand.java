package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Dimiikou
 */
public class AFbankEinzahlenCommand extends CommandBase {

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
            p.sendSyntaxMessage("/afbank [einzahlen/auszahlen] [Betrag]");
            return;
        }

        String interaction = args[0];

        if (STARTED.get()) return;

        if (!interaction.equalsIgnoreCase("einzahlen") && !interaction.equalsIgnoreCase("auszahlen")) return;

        // check if there are taxes
        p.sendChatMessage("/fbank " + interaction + " 4");
        this.amount = amount - 4; // we already paid 4$

        STARTED.set(true);

        AFbankEinzahlenCommand instance = this;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!STARTED.get()) {
                    cancel();
                    return;
                }

                if (instance.amount > 1000) {
                    // if amount is bigger than 1000, add or remove 1k from faction bank and wait
                    p.sendChatMessage("/fbank " + interaction + " 1000");
                    instance.amount -= 1000;
                } else {
                    // otherwise add or remove the remainder and stop the task
                    p.sendChatMessage("/fbank " + interaction + " " + instance.amount);
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
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Arrays.asList("einzahlen", "auszahlen");
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
}