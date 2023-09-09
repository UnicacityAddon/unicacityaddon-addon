package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Activity;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "moneyactivity", aliases = {"mact"}, usage = "[Typ] [Menge] (Screenshot)")
public class MoneyActivityCommand extends UnicacityCommand {

    private final List<String> typeOptions = Arrays.asList("blacklist", "ausraub", "menschenhandel", "transport", "autoverkauf");

    private final UnicacityAddon unicacityAddon;

    public MoneyActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 2 || !MathUtils.isInteger(arguments[1])) {
            sendUsage();
            return true;
        }

        if (!typeOptions.contains(arguments[0].toLowerCase())) {
            p.sendErrorMessage("Dieser Aktivitätstyp existiert nicht.");
            return true;
        }

        try {
            String type = arguments[0].toLowerCase();
            int value = Integer.parseInt(arguments[1]);
            File file = this.unicacityAddon.fileService().getNewImageFile();
            String screenshot = arguments.length == 3 ? arguments[2] : ScreenshotBuilder.getBuilder(unicacityAddon).file(file).upload();

            ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                    .activity(Activity.MONEY)
                    .type(type)
                    .value(String.valueOf(value))
                    .date(System.currentTimeMillis())
                    .screenshot(screenshot)
                    .send();
        } catch (IOException e) {
            this.unicacityAddon.logger().warn(e.getMessage());
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, typeOptions)
                .build();
    }
}