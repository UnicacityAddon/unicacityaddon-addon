package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class SyncPlayerDataCommand extends UnicacityCommand {

    private static final String usage = "/syncplayerdata";

    private final UnicacityAddon unicacityAddon;

    public SyncPlayerDataCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "sync", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        this.unicacityAddon.player().sendInfoMessage("Synchronisierung gestartet.");
        this.unicacityAddon.api().syncAll();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "teamspeak")
                .build();
    }
}