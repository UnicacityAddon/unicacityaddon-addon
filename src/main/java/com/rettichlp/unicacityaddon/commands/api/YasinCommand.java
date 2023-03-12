package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class YasinCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "yasin";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/yasin [add|done|remove] [Name]";
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
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "add", "done", "remove")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        new Thread(() -> {
            if (args.length == 0) {
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
            } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = APIRequest.sendYasinAddRequest(args[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = APIRequest.sendYasinRemoveRequest(args[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (args.length > 1 && args[0].equalsIgnoreCase("done")) {
                try {
                    JsonObject response = APIRequest.sendYasinDoneRequest(args[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            }
        }).start();
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