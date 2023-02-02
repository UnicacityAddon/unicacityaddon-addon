package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.HouseBanReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class HousebanReasonCommand extends Command {

    private static final String usage = "/housebanreason (add|remove) (Grund) (Tage)";

    public HousebanReasonCommand() {
    super("housebanreason");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbot-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            Syncer.getHouseBanReasonEntryList().forEach(houseBanReasonEntry -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(houseBanReasonEntry.getReason()).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Hinzugefügt von").color(ColorCode.GRAY).advance().space()
                                    .of(houseBanReasonEntry.getCreatorName()).color(ColorCode.RED).advance()
                                    .createComponent())
                            .advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(houseBanReasonEntry.getDays())).color(ColorCode.AQUA).advance().space()
                    .of(houseBanReasonEntry.getDays() == 1 ? "Tag" : "Tage").color(ColorCode.AQUA).advance()
                    .createComponent()));

            p.sendEmptyMessage();

        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add") && MathUtils.isInteger(arguments[2])) {
            JsonObject response = APIRequest.sendHouseBanReasonAddRequest(arguments[1], arguments[2]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanReasonRemoveRequest(arguments[1]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    /**
     * Lou, 16, sortiert nachts um 04:11 Uhr ihre Bücher
     */
    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, Syncer.getHouseBanReasonEntryList().stream().map(HouseBanReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}