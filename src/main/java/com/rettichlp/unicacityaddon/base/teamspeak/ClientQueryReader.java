package com.rettichlp.unicacityaddon.base.teamspeak;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.BaseCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.events.TSEvent;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
@SuppressWarnings("UnstableApiUsage")
public class ClientQueryReader extends Thread implements Closeable {

    private final BlockingQueue<BaseCommand<?>> queue = new LinkedBlockingQueue<>();
    private final BufferedReader reader;
    private volatile boolean closed;

    ClientQueryReader(BufferedReader reader) {
        this.reader = reader;

        setName("UnicacityAddon-TSClientQuery-ClientQueryReader");
    }

    @Override
    public void run() {
        while (!closed) {
            try {
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line.isEmpty())
                        continue;

                    if (line.startsWith("notify")) {
                        TSEvent event = TSEventHandler.getEvent(line);
                        if (event != null) {
                            TSEventHandler.fireEvent(event);
                            continue;
                        }
                    }

                    BaseCommand<?> baseCommand = queue.poll();
                    if (baseCommand != null) {
                        List<String> lines = new ArrayList<>();
                        lines.add(line.trim());

                        if (!line.startsWith("error")) {
                            String statusLine;
                            while ((statusLine = reader.readLine()) != null) {
                                statusLine = statusLine.trim();
                                if (statusLine.isEmpty())
                                    continue;

                                lines.add(statusLine);
                                break;
                            }
                        }

                        line = String.join(" ", lines);

                        ParameterizedType genericSuperclass = (ParameterizedType) baseCommand.getClass().getGenericSuperclass();
                        String className = genericSuperclass.getActualTypeArguments()[0].getTypeName();
                        Class<?> responseClass = Class.forName(className);

                        Object commandResponse = responseClass.getConstructor(String.class).newInstance(line);

                        baseCommand.getResponseFuture().complete(commandResponse);
                    }
                }
            } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
                // TODO Logger.LOGGER.catching(e);
            }

            Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void close() {
        closed = true;
        IOUtils.closeQuietly(reader);
    }

    public BlockingQueue<BaseCommand<?>> getQueue() {
        return queue;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
