package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "sync", onlyOnUnicacity = false)
public class SyncCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public SyncCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        this.unicacityAddon.player().sendInfoMessage("Synchronisierung gestartet.");
        this.unicacityAddon.api().sync(this.unicacityAddon.player());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "teamspeak")
                .build();
    }
}