package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.BlackMarketLocation;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "schwarzmarktlocations", aliases = {"schwarzmarktlocs", "smarktlocs"})
public class SchmarzmarktLocationsCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public SchmarzmarktLocationsCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.DARK_GRAY).advance()
                .of("Positionen aller möglichen Schwarzmärkte").color(ColorCode.GRAY).advance()
                .createComponent());

        Arrays.stream(BlackMarketLocation.values()).forEach(blackMarketLocation -> p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.DARK_GRAY).advance()
                .of(blackMarketLocation.getDisplayName()).color(ColorCode.GRAY).advance().space()
                .of("-").color(ColorCode.DARK_GRAY).advance().space()
                .of("Route anzeigen").color(ColorCode.RED).bold()
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, blackMarketLocation.getNaviCommand())
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                        .advance()
                .createComponent()));
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}