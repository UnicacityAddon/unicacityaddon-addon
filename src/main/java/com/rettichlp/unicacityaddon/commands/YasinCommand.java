package com.rettichlp.unicacityaddon.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
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
        return TabCompletionBuilder.getBuilder(args).build();
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
                yasinList(p);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
                JsonObject response = APIRequest.sendYasinAddRequest(args[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                JsonObject response = APIRequest.sendYasinRemoveRequest(args[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            } else if (args.length > 1 && args[0].equalsIgnoreCase("done")) {
                JsonObject response = APIRequest.sendYasinDoneRequest(args[1]);
                if (response == null)
                    return;
                p.sendAPIMessage(response.get("info").getAsString(), true);
            }
        }).start();
    }

    private void yasinList(UPlayer p) {
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

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}