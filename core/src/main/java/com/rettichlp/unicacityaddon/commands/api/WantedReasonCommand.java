package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.WantedReason;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class WantedReasonCommand extends Command {

    private static final String usage = "/wantedreason (add|remove) (Grund) (Wanted-Punkte)";

    private final UnicacityAddon unicacityAddon;

    public WantedReasonCommand(UnicacityAddon unicacityAddon) {
        super("wantedreason");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendWantedReasonAddRequest(arguments[1], arguments[2]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendWantedReasonRemoveRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else {
                p.sendSyntaxMessage(usage);
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, this.unicacityAddon.api().getWantedReasonList().stream().map(WantedReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}