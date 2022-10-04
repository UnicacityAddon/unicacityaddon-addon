package com.rettichlp.UnicacityAddon.commands.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class BroadcastCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "broadcast";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/broadcast [queue|send]";
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

    /**
     * Quote: *Stille* "Hat das Vorteile?" - RettichLP zu der WG von Fio, 29.09.2022
     */
    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 1 && args[0].equalsIgnoreCase("queue")) {
            JsonArray response = APIRequest.sendBroadcastQueueRequest();
            if (response == null) return;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Aktive Broadcasts:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String broadcast = o.get("broadcast").getAsString();
                String issuerName = o.get("issuerName").getAsString();

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(broadcast).color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(issuerName).color(ColorCode.GRAY).advance()
                        .of(")").color(ColorCode.GRAY).advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (args.length > 1 && args[0].equalsIgnoreCase("send")) {
            String broadcast = TextUtils.makeStringByArgs(args, " ").replace("send ", "");
            JsonObject response = APIRequest.sendBroadcastSendRequest(broadcast);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions.add("queue");
            tabCompletions.add("send");
        } else {
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());
        }
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
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