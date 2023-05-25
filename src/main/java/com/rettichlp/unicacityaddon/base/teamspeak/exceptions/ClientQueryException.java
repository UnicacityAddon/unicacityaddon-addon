package com.rettichlp.unicacityaddon.base.teamspeak.exceptions;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public abstract class ClientQueryException extends RuntimeException {

    public ClientQueryException() {
        super();
    }

    public ClientQueryException(String message) {
        super(message);
    }

    public ClientQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientQueryException(Throwable cause) {
        super(cause);
    }
}
