package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collection;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "checkfire", deactivated = true)
public class CheckFireCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public CheckFireCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Meine Öffi-Nachricht geht nicht... oh... ich habe den Imgur-Link eingefügt..." - [UC]laaurin_, 02.10.2022
     */
    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        FloatVector3 location = p.getLocation();

        if (location != null) {
            FloatVector3 one = location.copy().add(-30, -30, -30);
            FloatVector3 two = location.copy().add(30, 30, 30);

            Collection<FloatVector3> fireBlocks = this.unicacityAddon.worldInteractionController().getFireBlocksInBox(one, two);
            if (!fireBlocks.isEmpty()) {
                p.sendMessage(Message.getBuilder()
                        .of("Feuer in der Nähe:").color(ColorCode.DARK_RED).advance()
                        .createComponent());

                fireBlocks.forEach(block -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of("X: " + (int) block.getX() + " | Y: " + (int) block.getY() + " | Z: " + (int) block.getZ()).color(ColorCode.RED).advance().space()
                        .of("[➤]").color(ColorCode.GREEN)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + (int) block.getX() + "/" + (int) block.getY() + "/" + (int) block.getZ())
                        .advance()
                        .createComponent()));
            } else {
                p.sendMessage(Message.getBuilder()
                        .of("Es wurde").color(ColorCode.GRAY).advance().space()
                        .of("kein").color(ColorCode.RED).advance().space()
                        .of("Feuer im Umkreis gefunden!").color(ColorCode.GRAY).advance()
                        .createComponent());
            }
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}