package com.rettichlp.UnicacityAddon.base.exceptions;

public class ConfigClassCannotBeInstantiated extends RuntimeException {

    public ConfigClassCannotBeInstantiated(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}