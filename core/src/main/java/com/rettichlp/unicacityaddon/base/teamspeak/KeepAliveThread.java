package com.rettichlp.unicacityaddon.base.teamspeak;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.WhoAmICommand;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuzzlemann
 */
public class KeepAliveThread extends Thread implements Closeable {

    private final TSClientQuery clientQuery;
    private volatile boolean closed;

    private final UnicacityAddon unicacityAddon;

    public KeepAliveThread(UnicacityAddon unicacityAddon, TSClientQuery clientQuery) {
        this.unicacityAddon = unicacityAddon;
        this.clientQuery = clientQuery;

        setName("UnicacityAddon-TSClientQuery-KeepAliveThread");
    }

    @Override
    public void run() {
        while (!closed) {
            if (!clientQuery.isAuthenticated())
                continue;

            new WhoAmICommand(KeepAliveThread.this.unicacityAddon).execute(clientQuery);

            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MINUTES);
        }
    }

    @Override
    public void close() {
        closed = true;
    }
}
