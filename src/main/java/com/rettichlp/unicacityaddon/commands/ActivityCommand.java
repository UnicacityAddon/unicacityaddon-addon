package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.ScreenshotType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ActivityCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "activity";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/activity (Typ)";
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

        List<ScreenshotType> screenshotTypeList = args.length < 1 ?
                new ArrayList<>(Arrays.asList(ScreenshotType.values())) :
                Arrays.stream(ScreenshotType.values())
                        .filter(screenshotType -> screenshotType.getDirectoryName().equalsIgnoreCase(args[0]))
                        .collect(Collectors.toList());

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Aktivitäten:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        int screenshotCount = FileManager.getAddonScreenshotDir().listFiles((dir, name) -> name.endsWith(".jpg")).length;
        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("keine Zuordnung").color(ColorCode.GRAY).advance()
                .of(":").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(screenshotCount)).color(ColorCode.AQUA).advance().space()
                .of("[↗]").color(ColorCode.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Ordner öffnen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.OPEN_FILE, FileManager.getAddonScreenshotDir().getAbsolutePath())
                        .advance()
                .createComponent());

        screenshotTypeList.stream().map(ScreenshotType::getDirectoryName).sorted().forEach(s -> {
            int entryCount = FileManager.getAddonActivityScreenDir(s)
                    .listFiles((dir, name) -> name.endsWith("-" + s + ".jpg"))
                    .length;

            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(Character.toUpperCase(s.charAt(0)) + s.substring(1)).color(ColorCode.GRAY).advance()
                    .of(":").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(entryCount)).color(ColorCode.AQUA).advance().space()
                    .of("[↗]").color(ColorCode.BLUE)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Ordner öffnen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.OPEN_FILE, FileManager.getAddonActivityScreenDir(s).getAbsolutePath())
                            .advance()
                    .createComponent());
        });

        p.sendEmptyMessage();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, Arrays.stream(ScreenshotType.values()).map(ScreenshotType::getDirectoryName).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}