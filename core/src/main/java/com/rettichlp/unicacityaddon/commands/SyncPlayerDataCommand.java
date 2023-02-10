package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
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
        UnicacityAddon.PLAYER.sendInfoMessage("Synchronisierung gestartet.");
        FactionManager.generateFactionData();
        APIConverter.syncAll();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "teamspeak")
                .build();
    }
}