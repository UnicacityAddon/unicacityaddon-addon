package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "servicecount")
public class ServiceCountCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ServiceCountCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length > 0 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.fileService().data().setServiceCount(0);
            p.sendInfoMessage("Servicecount wurde zurückgesetzt.");
            return true;
        }

        p.sendMessage(Message.getBuilder().prefix()
                .of("Du hast bereits").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(this.unicacityAddon.fileService().data().getServiceCount())).color(ColorCode.DARK_AQUA).advance().space()
                .of("Notrufe bearbeitet.").color(ColorCode.GRAY).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }
}