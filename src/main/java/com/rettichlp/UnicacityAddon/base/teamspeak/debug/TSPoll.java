package com.rettichlp.UnicacityAddon.base.teamspeak.debug;

import com.rettichlp.UnicacityAddon.base.teamspeak.EventHandler;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSAPIKeyLoader;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSClientQuery;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSEventHandler;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSListener;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.SendTextMessageCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.events.ClientMessageReceivedEvent;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.TargetMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fuzzlemann
 */
public class TSPoll implements TSListener {

    private static final TSPoll INSTANCE = new TSPoll();
    private volatile boolean started;
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        new TSAPIKeyLoader().load();
        TSClientQuery.getInstance();

        TSEventHandler.registerListener(INSTANCE);

        new Thread(() -> {
            sendHelp();

            try (Scanner scanner = new Scanner(System.in)) {
                String line;
                while ((line = scanner.nextLine()) != null) {
                    line = line.trim();
                    line = line.toLowerCase();

                    if (line.startsWith("start")) {
                        // TODO Logger.LOGGER.info("TS Quick Poll gestartet");

                        INSTANCE.messages.clear();
                        INSTANCE.started = true;
                        continue;
                    }

                    if (line.startsWith("stop")) {
                        // TODO Logger.LOGGER.info("TS Quick Poll gestoppt");

                        INSTANCE.messages.clear();
                        INSTANCE.started = false;
                        continue;
                    }

                    if (line.startsWith("eval")) {
                        String[] splitted = line.split(" ");
                        if (splitted.length != 2) {
                            sendHelp();
                            continue;
                        }

                        boolean volunteer;
                        if (splitted[1].equals("1")) {
                            volunteer = false;
                        } else if (splitted[1].equals("2")) {
                            volunteer = true;
                        } else {
                            sendHelp();
                            continue;
                        }

                        int yes = 0;
                        int abstention = 0;
                        int no = 0;

                        for (String message : INSTANCE.messages) {
                            message = message.toLowerCase();

                            if (message.startsWith("j") || message.startsWith("y")) {
                                yes++;
                            } else if (message.startsWith("e")) {
                                abstention++;
                            } else if (message.startsWith("n")) {
                                no++;
                            }
                        }

                        String message;
                        if (!volunteer) {
                            message = "Ja: " + yes + " | Enthaltung: " + abstention + " | Nein: " + no;
                        } else {
                            message = "Punkte: " + (yes - no);
                        }

                        // TODO Logger.LOGGER.info("TS Quick Poll evaluiert");
                        new SendTextMessageCommand(TargetMode.CHANNEL, message).execute();
                        continue;
                    }

                    sendHelp();
                }
            }
        }, "Console-Thread").start();
    }

    private static void sendHelp() {
        // TODO Logger.LOGGER.info("=== TS Quick Poll ===");
        // TODO Logger.LOGGER.info("start: Umfrage starten");
        // TODO Logger.LOGGER.info("stop: Umfrage stoppen (ohne Evaluierung)");
        // TODO Logger.LOGGER.info("eval (Modus): Umfrage stoppen | (1): Normale Abstimmung; (2): Volunteer Abstimmung");
    }

    @EventHandler
    public void onMessageReceived(ClientMessageReceivedEvent e) {
        if (!started) return;
        if (e.getTargetMode() != TargetMode.CHANNEL) return;

        messages.add(e.getMessage());
    }
}
