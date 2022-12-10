package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GiftEigenbedarfCommand extends Command {

    public static boolean checkWeed = false;
    public static boolean checkMeth = false;

    private static final String usage = "/gifteigenbedarf [Spieler]";

    @Inject
    private GiftEigenbedarfCommand() {
        super("gifteigenbedarf");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (ConfigElements.getCocainActivated()) {
            p.sendChatMessage("/selldrug " + arguments[0] + " Kokain " + ConfigElements.getCocainDrugPurity().getPurity() + " " + ConfigElements.getCocaineAmount() + " 0");

            if (ConfigElements.getMarihuanaActivated())
                checkWeed = true;
            if (ConfigElements.getMethActivated())
                checkMeth = true;
            return true;
        }

        if (ConfigElements.getMarihuanaActivated()) {
            p.sendChatMessage("/selldrug " + arguments[0] + " Gras " + ConfigElements.getMarihuanaDrugPurity().getPurity() + " " + ConfigElements.getMarihuanaAmount() + " 0");

            if (ConfigElements.getMethActivated())
                checkMeth = true;
            return true;
        }

        if (ConfigElements.getMethActivated()) {
            p.sendChatMessage("/selldrug " + arguments[0] + " Meth " + ConfigElements.getMethDrugPurity().getPurity() + " " + ConfigElements.getMethAmount() + " 0");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}