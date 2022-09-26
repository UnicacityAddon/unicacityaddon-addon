package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.HoverEvent;
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
public class HousebanReasonCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "housebanreason";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/housebanreason (add|remove) (Grund) (Tage)";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            JsonArray response = APIRequest.sendHouseBanReasonRequest();
            if (response == null) return;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbot-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String reason = o.get("reason").getAsString();
                int days = o.get("days").getAsInt();
                String creatorName = o.get("creatorName").getAsString();

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(reason).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Hinzugefügt von").color(ColorCode.GRAY).advance().space()
                                        .of(creatorName).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(String.valueOf(days)).color(ColorCode.AQUA).advance().space()
                        .of(days == 1 ? "Tag" : "Tage").color(ColorCode.AQUA).advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendHouseBanReasonAddRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanReasonRemoveRequest(args[1]);
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
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            JsonArray response = APIRequest.sendHouseBanReasonRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsJsonObject().get("reason").getAsString()));
            }
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