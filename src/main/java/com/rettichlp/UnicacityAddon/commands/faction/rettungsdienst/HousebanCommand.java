package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
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
public class HousebanCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "houseban";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/houseban (add|remove) (Spieler) (Grund)";
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
            JsonArray response = APIRequest.sendHouseBanRequest(p.getFaction().equals(Faction.RETTUNGSDIENST));
            if (response == null) return;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbote:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String name = o.get("name").getAsString();
                long durationInMillis = o.get("expirationTime").getAsLong() - System.currentTimeMillis();

                long seconds = durationInMillis / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                String duration = Message.getBuilder()
                        .of(String.valueOf(days)).color(ColorCode.DARK_AQUA).advance().space()
                        .of(days == 1 ? "Tag" : "Tage").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(hours % 24)).color(ColorCode.DARK_AQUA).advance().space()
                        .of(hours % 24 == 1 ? "Stunde" : "Stunden").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(minutes % 60)).color(ColorCode.DARK_AQUA).advance().space()
                        .of(minutes % 60 == 1 ? "Minute" : "Minuten").color(ColorCode.AQUA).advance().space()
                        .of(String.valueOf(seconds % 60)).color(ColorCode.DARK_AQUA).advance().space()
                        .of(seconds % 60 == 1 ? "Sekunde" : "Sekunden").color(ColorCode.AQUA).advance().space()
                        .create();

                ColorCode colorCode = ColorCode.AQUA;
                if (days == 0) colorCode = ColorCode.DARK_GREEN;
                else if (days > 0 && days <= 5) colorCode = ColorCode.GREEN;
                else if (days > 5 && days <= 14) colorCode = ColorCode.YELLOW;
                else if (days > 14 && days <= 25) colorCode = ColorCode.GOLD;
                else if (days > 25 && days <= 50) colorCode = ColorCode.RED;
                else if (days > 50) colorCode = ColorCode.DARK_RED;

                Message.Builder builder = Message.getBuilder();
                o.get("houseBanReasonList").getAsJsonArray().forEach(jsonElement1 -> builder
                        .of(jsonElement1.getAsJsonObject().get("reason").getAsString()).color(ColorCode.RED).advance().space()
                        .of(jsonElement1.getAsJsonObject().has("issuerName") ? "(" : "").color(ColorCode.GRAY).advance()
                        .of(jsonElement1.getAsJsonObject().has("issuerName") ? jsonElement1.getAsJsonObject().get("issuerName").getAsString() : "").color(ColorCode.GRAY).advance()
                        .of(jsonElement1.getAsJsonObject().has("issuerName") ? ")" : "").color(ColorCode.GRAY).advance().space()
                        .newline());

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(name).color(colorCode).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(duration).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, builder.createComponent())
                            .advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendHouseBanAddRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
            Syncer.syncHouseBanList();
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanRemoveRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
            Syncer.syncHouseBanList();
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
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());
        } else if (args.length == 3) {
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