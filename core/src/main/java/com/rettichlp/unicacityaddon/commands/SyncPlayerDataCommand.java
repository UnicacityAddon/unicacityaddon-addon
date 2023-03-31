package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
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

    private UnicacityAddon unicacityAddon;

    public SyncPlayerDataCommand(UnicacityAddon unicacityAddon) {
        super("syncplayerdata", "spd", "sync", "syncdata");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
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