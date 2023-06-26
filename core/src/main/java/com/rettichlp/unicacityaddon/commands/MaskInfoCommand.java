package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "maskinfo")
public class MaskInfoCommand extends UnicacityCommand {

    public static long startTime = 0;

    private final UnicacityAddon unicacityAddon;

    public MaskInfoCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (startTime > 0) {
            long timeLeft = System.currentTimeMillis() - startTime;
            String maskDuration = this.unicacityAddon.services().util().text().parseTimer(1200 - TimeUnit.MILLISECONDS.toSeconds(timeLeft));
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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}