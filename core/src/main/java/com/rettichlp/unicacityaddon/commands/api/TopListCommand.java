package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.statisticTop.StatisticTopKdEntry;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rettichlp.unicacityaddon.base.services.utils.MathUtils.DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "toplist", deactivated = true)
public class TopListCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TopListCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Neue Formel überlegen!" - Dimiikou zu "<code>(0.5f + kd) * (services + revives)</code>", 30.09.2022
     */
    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            List<StatisticTopKdEntry> statisticTopKdEntryList = this.unicacityAddon.api().sendStatisticTopRequest().getKd();

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Top 10 Spieler:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            AtomicInteger place = new AtomicInteger();
            statisticTopKdEntryList.forEach(statisticTopKdEntry -> {
                place.getAndIncrement();
                p.sendMessage(Message.getBuilder()
                        .of(String.valueOf(place.get())).color(ColorCode.GOLD).advance()
                        .of(".").color(ColorCode.GRAY).advance().space()
                        .of(statisticTopKdEntry.getName()).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Tode:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(statisticTopKdEntry.getDeaths())).color(ColorCode.RED).advance().newline()
                                        .of("Kills:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(statisticTopKdEntry.getKills())).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(DECIMAL_FORMAT.format(statisticTopKdEntry.getValue())).color(ColorCode.AQUA).advance().space()
                        .of("Punkte").color(ColorCode.AQUA).advance().space()
                        .createComponent());
            });

            p.sendEmptyMessage();
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}