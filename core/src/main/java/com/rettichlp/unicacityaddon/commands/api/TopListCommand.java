package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonArray;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rettichlp.unicacityaddon.base.utils.MathUtils.DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
@UCCommand
public class TopListCommand extends Command {

    private static final String usage = "/toplist";

    private final UnicacityAddon unicacityAddon;

    public TopListCommand(UnicacityAddon unicacityAddon) {
        super("toplist");
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Neue Formel überlegen!" - Dimiikou zu "<code>(0.5f + kd) * (services + revives)</code>", 30.09.2022
     */
    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            try {
                JsonArray kdJsonArray = this.unicacityAddon.api().sendStatisticTopRequest().getAsJsonArray("kd");

                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Top 10 Spieler:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());

                AtomicInteger place = new AtomicInteger();
                kdJsonArray.forEach(jsonElement -> {
                    String name = jsonElement.getAsJsonObject().get("name").getAsString();
                    String kd = DECIMAL_FORMAT.format(jsonElement.getAsJsonObject().get("value").getAsFloat());
                    String deaths = String.valueOf(jsonElement.getAsJsonObject().get("deaths").getAsInt());
                    String kills = String.valueOf(jsonElement.getAsJsonObject().get("kills").getAsInt());

                    place.getAndIncrement();
                    p.sendMessage(Message.getBuilder()
                            .of(String.valueOf(place.get())).color(ColorCode.GOLD).advance()
                            .of(".").color(ColorCode.GRAY).advance().space()
                            .of(name).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Tode:").color(ColorCode.GRAY).advance().space()
                                    .of(deaths).color(ColorCode.RED).advance().newline()
                                    .of("Kills:").color(ColorCode.GRAY).advance().space()
                                    .of(kills).color(ColorCode.RED).advance()
                                    .createComponent())
                            .advance().space()
                            .of("-").color(ColorCode.GRAY).advance().space()
                            .of(kd).color(ColorCode.AQUA).advance().space()
                            .of("Punkte").color(ColorCode.AQUA).advance().space()
                            .createComponent());
                });

                p.sendEmptyMessage();
            } catch (APIResponseException e) {
                e.sendInfo();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}