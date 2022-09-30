package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
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
public class BlacklistReasonCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "blacklistreason";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/blacklistreason (add|remove) (Name) (Grund)";
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
            JsonArray response = APIRequest.sendBlacklistReasonRequest();
            if (response == null) return;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Blacklist-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String reason = o.get("reason").getAsString();
                int price = o.get("price").getAsInt();
                int kills = o.get("kills").getAsInt();

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(reason).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Preis:").color(ColorCode.RED).advance().space()
                                    .of(String.valueOf(price)).color(ColorCode.DARK_RED).advance().space()
                                    .of("Kills:").color(ColorCode.RED).advance().space()
                                    .of(String.valueOf(kills)).color(ColorCode.DARK_RED).advance()
                                    .createComponent())
                            .advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (args.length == 4 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendBlacklistReasonAddRequest(args[1], args[2], args[3]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendBlacklistReasonRemoveRequest(args[1]);
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
            JsonArray response = APIRequest.sendBlacklistReasonRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsJsonObject().get("reason").getAsString()));
            }
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