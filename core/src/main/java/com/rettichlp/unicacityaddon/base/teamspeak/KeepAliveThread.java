package com.rettichlp.unicacityaddon.base.teamspeak;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.WhoAmICommand;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
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
