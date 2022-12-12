package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.base.teamspeak.exceptions.ClientQueryFutureException;

import java.util.concurrent.CompletableFuture;

/**
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
