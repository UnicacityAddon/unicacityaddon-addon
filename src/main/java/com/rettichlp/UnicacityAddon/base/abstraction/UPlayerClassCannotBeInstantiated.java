package com.rettichlp.UnicacityAddon.base.abstraction;

/**
 * @author RettichLP
 */
public class UPlayerClassCannotBeInstantiated extends RuntimeException {

    public UPlayerClassCannotBeInstantiated(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
