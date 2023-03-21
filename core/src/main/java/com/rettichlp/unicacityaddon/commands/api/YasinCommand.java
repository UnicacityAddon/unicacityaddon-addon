package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class YasinCommand extends Command {

    public YasinCommand() {
        super("yasin");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        new Thread(() -> {
            if (arguments.length == 0) {
                try {
                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Yasin's Liste:").color(ColorCode.DARK_AQUA).bold().advance()
                            .createComponent());

                    APIRequest.sendYasinRequest().getAsJsonArray().forEach(jsonElement -> {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String name = jsonObject.get("name").getAsString();
                        boolean done = jsonObject.get("done").getAsBoolean();

                        p.sendMessage(Message.getBuilder()
                                .of("»").color(ColorCode.GRAY).advance().space()
                                .of((done ? FormattingCode.STRIKETHROUGH.getCode() : "") + name).color(ColorCode.AQUA).advance().space()
                                .of(!done ? "[✔] " : "").color(ColorCode.GREEN)
                                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/yasin done " + name)
                                        .advance()
                                .of("[✕]").color(ColorCode.RED)
                                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/yasin remove " + name)
                                        .advance()
                                .createComponent());
                    });

                    p.sendEmptyMessage();
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = APIRequest.sendYasinAddRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = APIRequest.sendYasinRemoveRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("done")) {
                try {
                    JsonObject response = APIRequest.sendYasinDoneRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "add", "done", "remove")
                .build();
    }
}