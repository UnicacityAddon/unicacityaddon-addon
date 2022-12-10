package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.HouseBanReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class HousebanCommand extends Command {

    private static final String usage = "/houseban (add|remove) (Spieler) (Grund)";

    @Inject
    private HousebanCommand() {
        super("houseban");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbote:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            Syncer.getHouseBanEntryList().forEach(houseBanEntry -> {
                long durationInMillis = houseBanEntry.getExpirationTime() - System.currentTimeMillis();

                long seconds = durationInMillis / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                String duration = Message.getBuilder()
                        .of(String.valueOf(days)).color(ColorCode.DARK_AQUA).advance()
                        .of("d").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(hours % 24)).color(ColorCode.DARK_AQUA).advance()
                        .of("h").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(minutes % 60)).color(ColorCode.DARK_AQUA).advance()
                        .of("m").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(seconds % 60)).color(ColorCode.DARK_AQUA).advance()
                        .of("s").color(ColorCode.AQUA).advance().space()
                        .create();

                ColorCode colorCode = ColorCode.AQUA;
                if (days == 0)
                    colorCode = ColorCode.DARK_GREEN;
                else if (days > 0 && days <= 5)
                    colorCode = ColorCode.GREEN;
                else if (days > 5 && days <= 14)
                    colorCode = ColorCode.YELLOW;
                else if (days > 14 && days <= 25)
                    colorCode = ColorCode.GOLD;
                else if (days > 25 && days <= 50)
                    colorCode = ColorCode.RED;
                else if (days > 50)
                    colorCode = ColorCode.DARK_RED;

                Message.Builder builder = Message.getBuilder();
                houseBanEntry.getHouseBanReasonList().forEach(houseBanReasonEntry -> builder
                        .of(houseBanReasonEntry.getReason()).color(ColorCode.RED).advance().space()
                        .of(houseBanReasonEntry.getCreatorName() != null ? "(" : "").color(ColorCode.GRAY).advance()
                        .of(houseBanReasonEntry.getCreatorName() != null ? houseBanReasonEntry.getCreatorName() : "").color(ColorCode.GRAY).advance()
                        .of(houseBanReasonEntry.getCreatorName() != null ? ")" : "").color(ColorCode.GRAY).advance().space()
                        .newline());

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(houseBanEntry.getName()).color(colorCode).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(duration).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, builder.createComponent())
                                .advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendHouseBanAddRequest(arguments[1], arguments[2]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanRemoveRequest(arguments[1], arguments[2]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(3, Syncer.getHouseBanReasonEntryList().stream().map(HouseBanReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}