package com.rettichlp.unicacityaddon.base.teamspeak.debug;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.EventHandler;
import com.rettichlp.unicacityaddon.base.teamspeak.TSAPIKeyLoader;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSEventHandler;
import com.rettichlp.unicacityaddon.base.teamspeak.TSListener;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.BaseCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.events.ClientMovedEvent;

import java.io.IOException;
import java.util.Scanner;

/**
 * Console used for debugging
 *
 * @author Fuzzlemann
 */
public class TSConsole implements TSListener {

// TODO: 31.03.2023
//    public static void main(String[] args) throws IOException {
//        new TSAPIKeyLoader().load();
//        TSClientQuery.getInstance();
//
//        TSEventHandler.registerListener(new TSConsole());
//
//        try (Scanner scanner = new Scanner(System.in)) {
//            while (scanner.hasNext()) {
//                String line = scanner.nextLine();
//
//                CommandResponse response = new BaseCommand<CommandResponse>(line) {
//                }.getResponse();
//
//                // TODO Logger.LOGGER.info(response.getRawResponse());
//            }
//        }
//    }

    @EventHandler
    public void onClientMoved(ClientMovedEvent e) {
    }
}
