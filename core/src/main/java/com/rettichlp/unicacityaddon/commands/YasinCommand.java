package com.rettichlp.unicacityaddon.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
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
                yasinList(p);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("add")) {
                JsonObject response = APIRequest.sendYasinAddRequest(arguments[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                JsonObject response = APIRequest.sendYasinRemoveRequest(arguments[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("done")) {
                JsonObject response = APIRequest.sendYasinDoneRequest(arguments[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }

    private void yasinList(AddonPlayer p) {
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Yasin's Liste:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        JsonArray response = APIRequest.sendYasinRequest();
        if (response == null)
            return;

        response.getAsJsonArray().forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            boolean done = jsonObject.get("done").getAsBoolean();

            if (done)
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(name).color(ColorCode.AQUA).strikethrough().advance().space()
                        .of("[✕]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/yasin remove " + name)
                                .advance()
                        .createComponent());
            else
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(name).color(ColorCode.AQUA).advance().space()
                        .of("[✔]").color(ColorCode.GREEN)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/yasin done " + name)
                                .advance().space()
                        .of("[✕]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/yasin remove " + name)
                                .advance()
                        .createComponent());
        });

        p.sendEmptyMessage();
    }
}