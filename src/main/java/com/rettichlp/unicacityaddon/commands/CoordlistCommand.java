package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.CoordlistEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class CoordlistCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "coordlist";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/coordlist [add|remove] [Ort]";
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
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "add")
                .addAtIndex(1, "remove")
                .addAtIndex(2, FileManager.DATA.getCoordlist().stream().map(CoordlistEntry::getName).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 0) {
            listCoords(p);
        } else if (args.length > 1 && args[0].equalsIgnoreCase("add")) {
            String name = TextUtils.makeStringByArgs(args, "-").replace("add-", "");
            if (FileManager.DATA.addCoordToCoordlist(name, p.getPosition())) {
                p.sendInfoMessage("Koordinaten gespeichert.");
            }
        } else if (args.length > 1 && args[0].equalsIgnoreCase("remove")) {
            String name = TextUtils.makeStringByArgs(args, "-").replace("remove-", "");
            if (FileManager.DATA.removeCoordFromCoordlist(name)) {
                p.sendInfoMessage("Koordinaten gelöscht.");
            }
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    private void listCoords(UPlayer p) {
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Koordinaten:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        FileManager.DATA.getCoordlist().forEach(coordlistEntry -> p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(coordlistEntry.getName().replace("-", " ")).color(ColorCode.AQUA).advance().space()
                .of("-").color(ColorCode.GRAY).advance().space()
                .of("[➤]").color(ColorCode.GREEN)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + coordlistEntry.getX() + "/" + coordlistEntry.getY() + "/" + coordlistEntry.getZ())
                        .advance().space()
                .of("[✕]").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Löschen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/coordlist remove " + coordlistEntry.getName())
                        .advance()
                .createComponent()));

        p.sendEmptyMessage();
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