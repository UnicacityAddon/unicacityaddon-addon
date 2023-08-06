package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.ScreenshotType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "screen", aliases = {"activitytest"}, usage = "[Typ] (Roleplay-Unterordner)")
public class ScreenCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ScreenCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        ScreenshotType screenshotType = find(Arrays.asList(ScreenshotType.values()), st -> st.getDirectoryName().equals(arguments[0]));
        if (screenshotType == null) {
            sendUsage();
            return true;
        }

        try {
            File file = screenshotType.equals(ScreenshotType.ROLEPLAY) && arguments.length > 1
                    ? this.unicacityAddon.fileService().getNewRoleplayActivityImageFile(arguments[1])
                    : this.unicacityAddon.fileService().getNewActivityImageFile(arguments[0]);
            ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, Arrays.stream(ScreenshotType.values()).map(ScreenshotType::getDirectoryName).collect(Collectors.toList()))
                .build();
    }
}