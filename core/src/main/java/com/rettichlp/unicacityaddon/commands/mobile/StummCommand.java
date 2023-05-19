package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "stumm", aliases = {"nichtstören", "donotdisturb"})
public class StummCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public StummCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        MobileListener.muted = !MobileListener.muted;

        if (MobileListener.muted)
            p.sendInfoMessage("Du hast dein Handy auf stumm gestellt.");
        else
            p.sendInfoMessage("Du hast dein Handy wieder laut gestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}