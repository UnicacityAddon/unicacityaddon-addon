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
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CoordlistCommand extends Command {

    public static List<CoordlistEntry> coordlist;

    private static final String usage = "/coordlist [add|remove] [Ort]";

    public CoordlistCommand() {
        super("coordlist");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 0) {
            listCoords(p);
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("add")) {
            addCoord(p, TextUtils.makeStringByArgs(arguments, "-").replace("add-", ""));
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("remove")) {
            removeCoord(p, TextUtils.makeStringByArgs(arguments, "-").replace("remove-", ""));
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
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
        FloatVector3 blockPos = p.getPosition();
        coordlist.add(new CoordlistEntry(name, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        FileManager.saveData();
        p.sendInfoMessage("Koordinaten gespeichert.");
    }

    private void removeCoord(UPlayer p, String name) {
        coordlist.removeIf(coordlistEntry -> coordlistEntry.getName().equalsIgnoreCase(name));
        FileManager.saveData();
        p.sendInfoMessage("Koordinaten gelöscht.");
    }
}