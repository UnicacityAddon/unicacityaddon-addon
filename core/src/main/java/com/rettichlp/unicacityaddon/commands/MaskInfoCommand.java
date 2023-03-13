package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class MaskInfoCommand extends Command {

    public static long startTime = 0;

    public MaskInfoCommand() {
        super("maskinfo");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (startTime > 0) {
            long timeLeft = System.currentTimeMillis() - startTime;
            String maskDuration = TextUtils.parseTimer(1200 - TimeUnit.MILLISECONDS.toSeconds(timeLeft));
            String[] splittedMaskDurytion = maskDuration.split(":");
            int minutes = Integer.parseInt(splittedMaskDurytion[0]);
            int seconds = Integer.parseInt(splittedMaskDurytion[1]);
            p.sendInfoMessage("Du bist noch " + minutes + " Minute" + (minutes != 1 ? "n" : "") + " und " + seconds + " Sekunde" + (seconds != 1 ? "n" : "") + " maskiert.");
        } else {
            p.sendErrorMessage("Du bist nicht maskiert.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}