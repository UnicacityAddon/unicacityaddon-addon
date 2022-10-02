package com.rettichlp.UnicacityAddon.base.teamspeak.debug;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.EventHandler;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSAPIKeyLoader;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSClientQuery;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSEventHandler;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSListener;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.BaseCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.events.ClientMovedEvent;

import java.io.IOException;
import java.util.Scanner;

/**
 * Console used for debugging
 *
 * @author Fuzzlemann
 */
public class TSConsole implements TSListener {

    public static void main(String[] args) throws IOException {
        new TSAPIKeyLoader().load();
        TSClientQuery.getInstance();

        TSEventHandler.registerListener(new TSConsole());

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                CommandResponse response = new BaseCommand<CommandResponse>(line) {
                }.getResponse();

                // TODO Logger.LOGGER.info(response.getRawResponse());
            }
        }
    }

    @EventHandler
    public void onClientMoved(ClientMovedEvent e) {
    }
}
