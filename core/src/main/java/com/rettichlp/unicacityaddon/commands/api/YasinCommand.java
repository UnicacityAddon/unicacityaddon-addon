package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.ClickEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "yasin")
public class YasinCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public YasinCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 0) {
                try {
                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Yasin's Liste:").color(ColorCode.DARK_AQUA).bold().advance()
                            .createComponent());

                    this.unicacityAddon.api().sendYasinRequest().getAsJsonArray().forEach(jsonElement -> {
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
                    JsonObject response = this.unicacityAddon.api().sendYasinAddRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendYasinRemoveRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("done")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendYasinDoneRequest(arguments[1]);
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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "done", "remove")
                .build();
    }
}