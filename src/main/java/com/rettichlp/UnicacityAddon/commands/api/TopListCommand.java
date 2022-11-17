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
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        /*JsonObject response = APIRequest.sendStatisticTopRequest();
        if (response == null) return;

        Map<String, Float> topListMap = new HashMap<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();
            String name = o.get("name").getAsString();
            float kd = o.get("kd").getAsFloat();

            // float points = (0.5f + kd) * (services + revives); // TODO: 30.09.2022 Neue Formel überlegen - Dimiikou
            topListMap.put(name, Float.valueOf(DECIMAL_FORMAT.format(kd)));
        });

        List<Map.Entry<String, Float>> list = new ArrayList<>(topListMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        LinkedHashMap<String, Float> sortedMap = new LinkedHashMap<>();
        list.stream()
                .limit(10)
                .forEach(e -> sortedMap.put(e.getKey(), e.getValue()));

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Top 10 Spieler:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        AtomicInteger place = new AtomicInteger();
        sortedMap.forEach((name, points) -> {
            place.getAndIncrement();
            p.sendMessage(Message.getBuilder()
                    .of(String.valueOf(place.get())).color(ColorCode.GOLD).advance()
                    .of(".").color(ColorCode.GRAY).advance().space()
                    .of(name).color(ColorCode.AQUA).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(points)).color(ColorCode.AQUA).advance().space()
                    .of("Punkte").color(ColorCode.AQUA).advance()
                    .createComponent());
        });*/

        p.sendEmptyMessage();
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