package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        return "/broadcast [queue|send] (dd.MM.yyyy) (HH:mm:ss) (Nachricht)";
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
            if (response == null)
                return;

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

        } else if (args.length > 3 && args[0].equalsIgnoreCase("send")) {
            String date = args[1]; // TT.MM.JJJJ
            String time = args[2]; // HH:MM:SS
            String message = TextUtils.makeStringByArgs(args, " ")
                    .replace("send ", "")
                    .replace(date + " ", "")
                    .replace(time + " ", "");

            LocalDateTime localDateTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            long sendTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            JsonObject response = APIRequest.sendBroadcastSendRequest(message, String.valueOf(sendTime));
            if (response == null)
                return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        Date now = new Date();
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "queue", "send")
                .addAtIndex(2, new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(now))
                .addAtIndex(3, new SimpleDateFormat("HH:mm:ss", Locale.GERMAN).format(now))
                .build();
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