package com.rettichlp.unicacityaddon.base.teamspeak;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.BaseCommand;
import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuzzlemann
 */
@SuppressWarnings("UnstableApiUsage")
public class ClientQueryWriter extends Thread implements Closeable {

    private final BlockingQueue<BaseCommand<?>> queue = new LinkedBlockingQueue<>();
    private final TSClientQuery query;
    private final PrintWriter writer;
    private volatile boolean closed;

    ClientQueryWriter(TSClientQuery query, PrintWriter writer) {
        this.query = query;
        this.writer = writer;

        setName("UnicacityAddon-TSClientQuery-ClientQueryWriter");
    }

    @Override
    public void run() {
        while (!closed) {
            BaseCommand<?> command;
            while ((command = queue.poll()) != null) {
                Uninterruptibles.putUninterruptibly(query.getReader().getQueue(), command);
                writer.println(command.getCommand());
            }

            Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void close() {
        closed = true;
        IOUtils.closeQuietly(writer);
    }

    public BlockingQueue<BaseCommand<?>> getQueue() {
        return queue;
    }
}
