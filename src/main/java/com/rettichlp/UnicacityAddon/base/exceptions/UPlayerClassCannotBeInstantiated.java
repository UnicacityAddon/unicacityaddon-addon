package com.rettichlp.UnicacityAddon.base.exceptions;

public class UPlayerClassCannotBeInstantiated extends RuntimeException {

    public UPlayerClassCannotBeInstantiated(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
