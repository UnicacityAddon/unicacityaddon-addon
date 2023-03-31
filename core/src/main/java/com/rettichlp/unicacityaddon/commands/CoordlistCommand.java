package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.CoordlistEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class CoordlistCommand extends Command {

    private static final String usage = "/coordlist [add|remove] [Ort]";

    private final UnicacityAddon unicacityAddon;

    public CoordlistCommand(UnicacityAddon unicacityAddon) {
        super("coordlist");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            listCoords(p);
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("add")) {
            String name = TextUtils.makeStringByArgs(arguments, "-").replace("add-", "");
            this.unicacityAddon.data().addCoordToCoordlist(name, p.getPosition());
            p.sendInfoMessage("Koordinaten gespeichert.");
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("remove")) {
            String name = TextUtils.makeStringByArgs(arguments, "-").replace("remove-", "");
            if (this.unicacityAddon.data().removeCoordFromCoordlist(name)) {
                p.sendInfoMessage("Koordinaten gelöscht.");
            }
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add")
                .addAtIndex(1, "remove")
                .addAtIndex(2, this.unicacityAddon.data().getCoordlist().stream().map(CoordlistEntry::getName).collect(Collectors.toList()))
                .build();
    }

    private void listCoords(AddonPlayer p) {
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Koordinaten:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        this.unicacityAddon.data().getCoordlist().forEach(coordlistEntry -> p.sendMessage(Message.getBuilder()
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
}