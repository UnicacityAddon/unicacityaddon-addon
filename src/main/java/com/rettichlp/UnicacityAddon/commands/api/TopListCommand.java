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
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rettichlp.UnicacityAddon.base.utils.MathUtils.DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
@UCCommand
public class TopListCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "toplist";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/toplist";
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
     * Quote: "Neue Formel überlegen!" - Dimiikou zu "<code>(0.5f + kd) * (services + revives)</code>", 30.09.2022
     */
    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        new Thread(() -> {
            JsonObject response = APIRequest.sendStatisticTopRequest();
            if (response == null) return;
            JsonArray kdJsonArray = response.getAsJsonArray("kd");

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Top 10 Spieler:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            AtomicInteger place = new AtomicInteger();
            kdJsonArray.forEach(jsonElement -> {
                String name = jsonElement.getAsJsonObject().get("name").getAsString();
                String kd = DECIMAL_FORMAT.format(jsonElement.getAsJsonObject().get("value").getAsFloat());

                JsonObject statisticResponse = APIRequest.sendStatisticRequest(name);
                if (statisticResponse == null) return;

                JsonObject gameplayJsonObject = statisticResponse.getAsJsonObject("gameplay");
                int deaths = gameplayJsonObject.get("deaths").getAsInt();
                int kills = gameplayJsonObject.get("kills").getAsInt();

                place.getAndIncrement();
                p.sendMessage(Message.getBuilder()
                        .of(String.valueOf(place.get())).color(ColorCode.GOLD).advance()
                        .of(".").color(ColorCode.GRAY).advance().space()
                        .of(name).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Tode:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(deaths)).color(ColorCode.RED).advance().newline()
                                        .of("Kills:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(kills)).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(kd).color(ColorCode.AQUA).advance().space()
                        .of("Punkte").color(ColorCode.AQUA).advance().space()
                        .createComponent());
            });

            p.sendEmptyMessage();
        }).start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
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