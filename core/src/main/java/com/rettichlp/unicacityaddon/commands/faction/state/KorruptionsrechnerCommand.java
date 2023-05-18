package com.rettichlp.unicacityaddon.commands.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;

import java.util.List;

/**
 * @author Gelegenheitscode
 */
@UCCommand
public class KorruptionsrechnerCommand extends UnicacityCommand {

    private static final String usage = "/korruptionsrechner [Spieler]";

    private final UnicacityAddon unicacityAddon;

    public KorruptionsrechnerCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "korruptionsrechner", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String target = arguments[0];

        WantedListener.Wanted wanted = WantedListener.WANTED_MAP.get(target);
        if (wanted == null) {
            p.sendErrorMessage("Du hast /wanteds noch nicht ausgeführt!");
            return true;
        }

        int money = wanted.getAmount() * 150;
        int drugs = money / 45;
        int methDrugs0 = money / 110;
        int methDrugs1 = money / 100;
        int methDrugs2 = money / 50;

        p.sendMessage(Message.getBuilder()
                .of("Korruptionspreise für").color(ColorCode.DARK_AQUA).advance().space()
                .of(target).color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .createComponent());

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Geld").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(money + "$").color(ColorCode.AQUA).advance()
                .createComponent());

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Drogen").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(drugs + "g").color(ColorCode.AQUA).advance()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Meth").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(methDrugs0 + "g").color(ColorCode.AQUA).advance().space()
                .of("|").color(ColorCode.DARK_GRAY).advance().space()
                .of(methDrugs1 + "g").color(ColorCode.AQUA).advance().space()
                .of("|").color(ColorCode.DARK_GRAY).advance().space()
                .of(methDrugs2 + "g").color(ColorCode.AQUA).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}