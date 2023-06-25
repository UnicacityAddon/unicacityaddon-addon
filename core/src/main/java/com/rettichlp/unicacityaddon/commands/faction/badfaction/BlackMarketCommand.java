package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "schwarzmarktlocations", aliases = {"schwarzmarktlocs", "smarktlocs"})
public class BlackMarketCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public BlackMarketCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Schwarzmarkt-Positionen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        this.unicacityAddon.api().getBlackMarketLocationList().forEach(blackMarketLocation -> p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(blackMarketLocation.getName()).color(ColorCode.AQUA).advance().space()
                .of("[➤]").color(ColorCode.GREEN)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + blackMarketLocation.getX() + "/" + blackMarketLocation.getY() + "/" + blackMarketLocation.getZ())
                        .advance()
                .createComponent()));

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}