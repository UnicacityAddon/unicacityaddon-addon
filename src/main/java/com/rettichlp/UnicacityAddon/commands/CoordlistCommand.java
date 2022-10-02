package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.json.CoordlistEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
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

/**
 * @author RettichLP
 */
@UCCommand
public class CoordlistCommand implements IClientCommand {

    public static List<CoordlistEntry> coordlist;

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
        return ForgeUtils.getOnlinePlayers();
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
            addCoord(p, TextUtils.makeStringByArgs(args, "-").replace("add-", ""));
        } else if (args.length > 1 && args[0].equalsIgnoreCase("remove")) {
            removeCoord(p, TextUtils.makeStringByArgs(args, "-").replace("remove-", ""));
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    private void listCoords(UPlayer p) {
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Koordinaten:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        coordlist.forEach(coordlistEntry -> p.sendMessage(Message.getBuilder()
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

    private void addCoord(UPlayer p, String name) {
        BlockPos blockPos = p.getPosition();
        coordlist.add(new CoordlistEntry(name, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        FileManager.saveData();
        p.sendInfoMessage("Koordinaten gespeichert.");
    }

    private void removeCoord(UPlayer p, String name) {
        coordlist.removeIf(coordlistEntry -> coordlistEntry.getName().equalsIgnoreCase(name));
        FileManager.saveData();
        p.sendInfoMessage("Koordinaten gelöscht.");
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