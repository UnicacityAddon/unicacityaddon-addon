package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.ScreenshotType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "activity", onlyOnUnicacity = false)
public class ActivityCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        List<ScreenshotType> screenshotTypeList = arguments.length < 1 ?
                new ArrayList<>(Arrays.asList(ScreenshotType.values())) :
                Arrays.stream(ScreenshotType.values())
                        .filter(screenshotType -> screenshotType.getDirectoryName().equalsIgnoreCase(arguments[0]))
                        .toList();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Aktivitäten:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        AtomicInteger overallCount = new AtomicInteger();
        screenshotTypeList.stream()
                .map(ScreenshotType::getDirectoryName)
                .sorted()
                .forEach(s -> {
                    File addonActivityScreenDir = this.unicacityAddon.fileService().getAddonActivityScreenDir(s);
                    File[] files = new File[0];
                    if (addonActivityScreenDir != null) {
                        files = addonActivityScreenDir.listFiles((dir, name) -> dir.isDirectory() || name.endsWith("-" + s + ".jpg"));
                    }

                    // exclude roleplay directory from (overall)count
                    int entryCount = files != null ? files.length : 0;

                    overallCount.addAndGet(entryCount);
                    if (entryCount > 0) {
                        p.sendMessage(Message.getBuilder()
                                .of("»").color(ColorCode.GRAY).advance().space()
                                .of(Character.toUpperCase(s.charAt(0)) + s.substring(1)).color(ColorCode.GRAY).advance()
                                .of(":").color(ColorCode.GRAY).advance().space()
                                .of(String.valueOf(entryCount)).color(ColorCode.AQUA).advance().space()
                                .of("[↗]").color(ColorCode.BLUE)
                                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Ordner öffnen").color(ColorCode.RED).advance().createComponent())
                                        .clickEvent(ClickEvent.Action.OPEN_FILE, this.unicacityAddon.fileService().getAddonActivityScreenDir(s).getAbsolutePath())
                                        .advance()
                                .createComponent());
                    }
                });

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Gesamt").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(overallCount.get())).color(ColorCode.AQUA).advance().space()
                .createComponent());

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, Arrays.stream(ScreenshotType.values()).map(ScreenshotType::getDirectoryName).collect(Collectors.toList()))
                .build();
    }
}