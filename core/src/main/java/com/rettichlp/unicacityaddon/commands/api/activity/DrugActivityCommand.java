package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Activity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "drugactivity", aliases = {"dact"}, usage = "[Droge] [Reinheit] [Menge] (Screenshot)")
public class DrugActivityCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DrugActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 3) {
            sendUsage();
            return true;
        }

        DrugType drugType = DrugType.getDrugType(arguments[0]);
        if (drugType == null) {
            p.sendErrorMessage("Diese Droge existiert nicht.");
            return true;
        }

        DrugPurity drugPurity = DrugPurity.getDrugPurity(arguments[1]);
        if (drugPurity == null) {
            p.sendErrorMessage("Diese Reinheit existiert nicht.");
            return true;
        }

        try {
            int drugAmount = Integer.parseInt(arguments[2]);
            File file = this.unicacityAddon.fileService().getNewImageFile();
            String screenshot = arguments.length == 4 ? arguments[3] : ScreenshotBuilder.getBuilder(unicacityAddon).file(file).upload();

            ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                    .activity(Activity.DRUG)
                    .value(String.valueOf(drugAmount))
                    .drugType(drugType)
                    .drugPurity(drugPurity)
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
                .addAtIndex(1, Arrays.stream(DrugType.values()).filter(drugType -> !drugType.isLegal()).map(DrugType::getDrugName).toList())
                .addAtIndex(2, Arrays.stream(DrugPurity.values()).map(DrugPurity::getPurityString).toList())
                .build();
    }
}