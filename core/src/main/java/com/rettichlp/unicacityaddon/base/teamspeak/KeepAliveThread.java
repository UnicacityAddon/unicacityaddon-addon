package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.base.teamspeak.commands.WhoAmICommand;

import java.io.Closeable;

/**
 * @author Fuzzlemann
 */
@SuppressWarnings("UnstableApiUsage")
public class KeepAliveThread extends Thread implements Closeable {

    private final TSClientQuery clientQuery;
    private volatile boolean closed;

    public KeepAliveThread(TSClientQuery clientQuery) {
        this.clientQuery = clientQuery;

        setName("UnicacityAddon-TSClientQuery-KeepAliveThread");
    }

    @Override
    public void run() {
        while (!closed) {
            if (!clientQuery.isAuthenticated())
                continue;

            new WhoAmICommand().execute(clientQuery);

//            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MINUTES);
        }
    }

    @Override
    public void close() {
        closed = true;
    }
}
