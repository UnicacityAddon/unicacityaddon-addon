package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
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
@UCCommand(prefix = "drugactivity", aliases = {"dact"}, usage = "[Droge] [Reinheit] [Menge] (screenshot)")
public class DrugActivityCommand extends UnicacityCommand {
    private final UnicacityAddon unicacityAddon;

    public DrugActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {

            if (arguments.length < 3) {
                sendUsage();
                return;
            }

            DrugType drugType = DrugType.getDrugType(arguments[0]);
            if (drugType == null) {
                p.sendErrorMessage("Diese Droge existiert nicht.");
                return;
            }

            DrugPurity drugPurity = DrugPurity.getDrugPurity(arguments[1]);
            if (drugPurity == null) {
                p.sendErrorMessage("Diese Reinheit existiert nicht.");
                return;
            }

            int drugAmount = Integer.parseInt(arguments[2]);
            String screenshot = "";

            if (arguments.length == 4) {
                screenshot = arguments[3];
            } else {
                try {
                    File file = this.unicacityAddon.fileService().getNewImageFile();
                    ScreenshotBuilder.getBuilder(unicacityAddon).file(file).save();
                    screenshot = this.unicacityAddon.utilService().imageUpload().uploadToLink(file);
                } catch (IOException e) {
                    this.unicacityAddon.logger().warn(e.getMessage());
                }
            }

            String info = ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                    .activity(ActivityCheckBuilder.Activity.DRUG)
                    .value(String.valueOf(drugAmount))
                    .drugType(drugType)
                    .drugPurity(drugPurity)
                    .date(System.currentTimeMillis())
                    .screenshot(screenshot)
                    .send().getInfo();

            p.sendAPIMessage(info, true);
        }).start();
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