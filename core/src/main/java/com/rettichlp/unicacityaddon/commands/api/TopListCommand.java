package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rettichlp.unicacityaddon.base.utils.MathUtils.DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
@UCCommand
public class TopListCommand extends Command {

    private static final String usage = "/toplist";

    @Inject
    private TopListCommand() {
        super("toplist");
    }

    /**
     * Quote: "Neue Formel überlegen!" - Dimiikou zu "<code>(0.5f + kd) * (services + revives)</code>", 30.09.2022
     */
    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        new Thread(() -> {
            JsonObject response = APIRequest.sendStatisticTopRequest();
            if (response == null)
                return;
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
                if (statisticResponse == null)
                    return;

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
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}