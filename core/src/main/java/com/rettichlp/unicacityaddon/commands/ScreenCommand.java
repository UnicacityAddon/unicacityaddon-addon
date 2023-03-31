package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.ScreenshotType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class ScreenCommand extends Command {

    private static final String usage = "/screen [Typ]";

    private UnicacityAddon unicacityAddon;

    public ScreenCommand(UnicacityAddon unicacityAddon) {
        super("screen", "activitytest");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        ScreenshotType screenshotType = Arrays.stream(ScreenshotType.values())
                .filter(st -> st.getDirectoryName().equals(arguments[0]))
                .findFirst()
                .orElse(null);

        if (screenshotType == null) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        try {
            File file = FileManager.getNewActivityImageFile(arguments[0]);
            // TODO: 08.02.2023
//            if (this.unicacityAddon.configuration().screenUpload().get())
//                HotkeyListener.handleScreenshotWithUpload(file);
//            else
//                HotkeyListener.handleScreenshotWithoutUpload(file);
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