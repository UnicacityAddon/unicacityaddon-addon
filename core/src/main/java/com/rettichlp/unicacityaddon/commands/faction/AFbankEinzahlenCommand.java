package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.text.SimpleDateFormat;
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
@UCCommand(prefix = "afbank", usage = "[einzahlen|auszahlen] [Betrag]")
public class AFbankEinzahlenCommand extends UnicacityCommand {

    public static final AtomicBoolean STARTED = new AtomicBoolean();
    public static final Timer timer = new Timer();
    public static int amount;

    private final UnicacityAddon unicacityAddon;

    public AFbankEinzahlenCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length != 2 || !MathUtils.isInteger(arguments[1])) {
            sendUsage();
            return true;
        }

        String interaction = arguments[0];

        if (STARTED.get())
            return true;

        if (!interaction.equalsIgnoreCase("einzahlen") && !interaction.equalsIgnoreCase("auszahlen"))
            return true;

        // check if there are taxes
        p.sendServerMessage("/fbank " + interaction + " 4");
        amount = Integer.parseInt(arguments[1]) - 4; // we already paid 4$

        STARTED.set(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!STARTED.get()) {
                    cancel();
                    return;
                }

                if (amount > 1000) {
                    // if amount is bigger than 1000, add or remove 1k from faction bank and wait
                    p.sendServerMessage("/fbank " + interaction + " 1000");
                    amount -= 1000;
                } else {
                    // otherwise add or remove the remainder and stop the task
                    p.sendServerMessage("/fbank " + interaction + " " + amount);
                    STARTED.set(false);
                    cancel();

                    // send clock command
                    timer.schedule(new TimerTask() {
                        public void run() {
                            sendClockMessage(AFbankEinzahlenCommand.this.unicacityAddon);
                        }
                    }, 200L);
                }
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "einzahlen", "auszahlen")
                .build();
    }

    public static void sendClockMessage(UnicacityAddon unicacityAddon) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.GERMAN);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMMM yyyy", Locale.GERMAN);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss 'Uhr'");

        Date date = new Date();

        String dayString = dayFormat.format(date);
        String dateString = dateFormat.format(date);
        String timeString = timeFormat.format(date);

        unicacityAddon.player().sendMessage(Message.getBuilder()
                .prefix()
                .of("Heute ist ").color(ColorCode.GRAY).advance()
                .of(dayString).color(ColorCode.BLUE).advance()
                .of(", der ").color(ColorCode.GRAY).advance()
                .of(dateString).color(ColorCode.BLUE).advance()
                .of(" und es ist ").color(ColorCode.GRAY).advance()
                .of(timeString).color(ColorCode.BLUE).advance()
                .of(".").color(ColorCode.GRAY).advance()
                .createComponent());
    }
}