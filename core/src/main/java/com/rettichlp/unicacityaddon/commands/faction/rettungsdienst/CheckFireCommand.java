package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collection;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CheckFireCommand extends Command {

    private static final String usage = "/checkfire";

    private final UnicacityAddon unicacityAddon;

    public CheckFireCommand(UnicacityAddon unicacityAddon) {
        super("checkfire");
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Meine Öffi-Nachricht geht nicht... oh... ich habe den Imgur-Link eingefügt..." - [UC]laaurin_, 02.10.2022
     */
    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        FloatVector3 pos = p.getPosition();
        FloatVector3 one = pos.copy().add(-30, -30, -30);
        FloatVector3 two = pos.copy().add(30, 30, 30);

        Collection<FloatVector3> fireBlocks = this.unicacityAddon.worldInteractionController().getFireBlocksInBox(one, two);
        if (!fireBlocks.isEmpty()) {
            p.sendMessage(Message.getBuilder()
                    .of("Feuer in der Nähe:").color(ColorCode.DARK_RED).advance()
                    .createComponent());

            fireBlocks.forEach(floatVector3 -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of("X: " + (int) floatVector3.getX() + " | Y: " + (int) floatVector3.getY() + " | Z: " + (int) floatVector3.getZ()).color(ColorCode.RED).advance().space()
                    .of("[➤]").color(ColorCode.GREEN)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + (int) floatVector3.getX() + "/" + (int) floatVector3.getY() + "/" + (int) floatVector3.getZ())
                            .advance()
                    .createComponent()));
        } else {
            p.sendMessage(Message.getBuilder()
                    .of("Es wurde").color(ColorCode.GRAY).advance().space()
                    .of("kein").color(ColorCode.RED).advance().space()
                    .of("Feuer im Umkreis gefunden!").color(ColorCode.GRAY).advance()
                    .createComponent());
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}