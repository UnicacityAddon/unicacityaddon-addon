package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.BlacklistReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class BlacklistReasonCommand extends Command {

    private static final String usage = "/blacklistreason (add|remove) (Grund) (Preis) (Kills)";

    public BlacklistReasonCommand() {
        super("blacklistreason");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Blacklist-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            Syncer.getBlacklistReasonEntryList().forEach(blacklistReasonEntry -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(blacklistReasonEntry.getReason()).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Preis:").color(ColorCode.RED).advance().space()
                                    .of(String.valueOf(blacklistReasonEntry.getPrice())).color(ColorCode.DARK_RED).advance().space()
                                    .of("Kills:").color(ColorCode.RED).advance().space()
                                    .of(String.valueOf(blacklistReasonEntry.getKills())).color(ColorCode.DARK_RED).advance()
                                    .createComponent())
                            .advance()
                    .createComponent()));

            p.sendEmptyMessage();

        } else if (arguments.length == 4 && arguments[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendBlacklistReasonAddRequest(arguments[1], arguments[2], arguments[3]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendBlacklistReasonRemoveRequest(arguments[1]);
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
                .addAtIndex(2, Syncer.getBlacklistReasonEntryList().stream().map(BlacklistReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}