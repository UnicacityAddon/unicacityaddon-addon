package com.rettichlp.UnicacityAddon.base.exceptions;

public class LabymodClassCannotBeInstantiated extends RuntimeException {

    public LabymodClassCannotBeInstantiated(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
