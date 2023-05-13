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
public class ClientQueryConnectionException extends ClientQueryException {

    public ClientQueryConnectionException() {
        super();
    }

    public ClientQueryConnectionException(String message) {
        super(message);
    }

    public ClientQueryConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientQueryConnectionException(Throwable cause) {
        super(cause);
    }
}
