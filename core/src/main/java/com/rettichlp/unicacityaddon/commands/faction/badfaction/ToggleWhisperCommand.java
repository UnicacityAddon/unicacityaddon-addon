package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "togglewhisper", aliases = {"togglew", "toggleflüstern", "geknebelt"})
public class ToggleWhisperCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ToggleWhisperCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        p.setWhispering(!p.isWhispering());

        if (p.isWhispering()) {
            p.sendInfoMessage("Ab sofort flüsterst du jede Nachricht.");
        } else {
            p.sendInfoMessage("Ab sofort redest du wieder normal.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}