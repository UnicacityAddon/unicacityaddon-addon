package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class SyncPlayerDataCommand extends Command {

    private static final String usage = "/syncplayerdata";

    public SyncPlayerDataCommand() {
        super("syncplayerdata", "spd", "sync", "syncdata");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AbstractionLayer.getPlayer().sendInfoMessage("Synchronisierung gestartet.");
        Syncer.syncAll();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "teamspeak")
                .build();
    }
}