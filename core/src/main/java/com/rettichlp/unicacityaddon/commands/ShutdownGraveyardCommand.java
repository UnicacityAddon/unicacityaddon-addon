package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "shutdowngraveyard", aliases = {"shutdownfriedhof"})
public class ShutdownGraveyardCommand extends UnicacityCommand {

    public static boolean shutdownGraveyard = false;

    private final UnicacityAddon unicacityAddon;

    public ShutdownGraveyardCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        shutdownGraveyard = !shutdownGraveyard;

        if (shutdownGraveyard)
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun herunter sobald du wieder lebst.").color(ColorCode.GRAY).advance()
                    .createComponent());
        else
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun").color(ColorCode.GRAY).advance().space()
                    .of("nicht mehr").color(ColorCode.RED).advance().space()
                    .of("herunter sobald du wieder lebst.").color(ColorCode.GRAY).advance()
                    .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}