package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ShutdownJailCommand extends Command {

    public static boolean shutdownJail = false;

    private final UnicacityAddon unicacityAddon;

    public ShutdownJailCommand(UnicacityAddon unicacityAddon) {
        super("shutdownjail", "shutdowngefängnis");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        shutdownJail = !shutdownJail;

        if (shutdownJail)
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun herunter sobald du entlassen bist.").color(ColorCode.GRAY).advance()
                    .createComponent());
        else
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun").color(ColorCode.GRAY).advance().space()
                    .of("nicht mehr").color(ColorCode.RED).advance().space()
                    .of("herunter sobald du entlassen bist.").color(ColorCode.GRAY).advance()
                    .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}