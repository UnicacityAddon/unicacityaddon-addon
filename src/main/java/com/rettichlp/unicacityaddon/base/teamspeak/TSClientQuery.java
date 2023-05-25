package com.rettichlp.unicacityaddon.base.teamspeak;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.AuthCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.BaseCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientNotifyRegisterCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.CurrentSchandlerIDCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.exceptions.ClientQueryAuthenticationException;
import com.rettichlp.unicacityaddon.base.teamspeak.exceptions.ClientQueryConnectionException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 * @author RettichLP
 */
@SuppressWarnings("UnstableApiUsage")
public class TSClientQuery implements Closeable {

    public static boolean clientQueryConnected = false;
    private static TSClientQuery instance;
    private Socket socket;
    private volatile ClientQueryWriter writer;
    private volatile ClientQueryReader reader;
    private volatile KeepAliveThread keepAliveThread;
    private volatile boolean authenticated;
    private volatile int schandlerID;

    private TSClientQuery() {
    }

    public static TSClientQuery getInstance() {
        if (instance == null) {
            instance = new TSClientQuery();
            try {
                instance.connect();
            } catch (IOException e) {
                clientQueryConnected = false;
                throw new ClientQueryConnectionException("TeamSpeak ClientQuery failed setting up a connection", e);
            }
        }

        clientQueryConnected = true;
        return instance;
    }

    public static void reconnect() {
        disconnect();
        getInstance();
        AbstractionLayer.getPlayer().sendInfoMessage("TeamSpeak ClientQuery Wiederherstellung abgeschlossen.");
    }

    public static void disconnect() {
        if (instance != null) {
            // TODO Logger.LOGGER.info("Closing the TeamSpeak Client Query Connection...");
            instance.close();
        }
    }

    public void executeCommand(BaseCommand<?> command) {
        Uninterruptibles.putUninterruptibly(writer.getQueue(), command);
    }

    private void connect() throws IOException {
        // TODO Logger.LOGGER.info("Setting up the TeamSpeak Client Query Connection...");

        setupConnection();
        authenticate();
        setupSchandlerID();
        registerEvents();
    }

    private void setupConnection() throws IOException {
        socket = new Socket("127.0.0.1", 25639);

        socket.setTcpNoDelay(true);
        socket.setSoTimeout(4000);

        writer = new ClientQueryWriter(this, new PrintWriter(socket.getOutputStream(), true));
        reader = new ClientQueryReader(new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));

        // welcome messages
        while (reader.getReader().ready()) {
            reader.getReader().readLine();
        }

        writer.start();
        reader.start();

        keepAliveThread = new KeepAliveThread(this);
        keepAliveThread.start();
    }

    private void authenticate() {
        AuthCommand authCommand = new AuthCommand(ConfigElements.getTeamspeakAPIKey());
        authCommand.execute(this);

        CommandResponse response = authCommand.getResponse();
        if (!response.succeeded())
            throw new ClientQueryAuthenticationException("API Key was not entered correctly");

        authenticated = true;
    }

    private void setupSchandlerID() {
        CurrentSchandlerIDCommand.Response response = new CurrentSchandlerIDCommand().getResponse();
        this.schandlerID = response.getSchandlerID();
    }

    private void registerEvents() {
        int schandlerID = TSClientQuery.getInstance().getSchandlerID();
        for (String eventName : TSEventHandler.TEAMSPEAK_EVENTS.keySet()) {
            new ClientNotifyRegisterCommand(schandlerID, eventName).execute();
        }
    }

    public ClientQueryReader getReader() {
        return reader;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public int getSchandlerID() {
        return schandlerID;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(socket, writer, reader, keepAliveThread);
        TSClientQuery.instance = null;
    }
}