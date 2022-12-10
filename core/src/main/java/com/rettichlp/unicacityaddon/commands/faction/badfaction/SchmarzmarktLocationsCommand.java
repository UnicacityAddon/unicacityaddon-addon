package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.BlackMarketLocation;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class SchmarzmarktLocationsCommand extends Command {

    private static final String usage = "/schwarzmarktlocations";

    @Inject
    private SchmarzmarktLocationsCommand() {
        super("schwarzmarktlocations", "schwarzmarktlocs", "smarktlocs");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.DARK_GRAY).advance()
                .of("Positionen aller möglichen Schwarzmärkte").color(ColorCode.GRAY).advance()
                .createComponent());

        Arrays.stream(BlackMarketLocation.values()).forEach(blackMarketLocation -> {
            p.sendMessage(Message.getBuilder()
                    .of("» ").color(ColorCode.DARK_GRAY).advance()
                    .of(blackMarketLocation.getDisplayName()).color(ColorCode.GRAY).advance().space()
                    .of("-").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Route anzeigen").color(ColorCode.RED).bold()
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, blackMarketLocation.getNaviCommand())
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                            .advance()
                    .createComponent());
        });
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}