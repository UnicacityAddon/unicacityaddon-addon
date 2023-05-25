package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.base.teamspeak.exceptions.ClientQueryFutureException;

import java.util.concurrent.CompletableFuture;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class CommandFuture<T> extends CompletableFuture<T> {

    @Override
    public T get() {
        try {
            return super.get();
        } catch (Exception e) {
            throw new ClientQueryFutureException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean complete(Object commandResponse) {
        return super.complete((T) commandResponse);
    }
}
