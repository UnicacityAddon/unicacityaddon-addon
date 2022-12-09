package com.rettichlp.unicacityaddon.base.abstraction;

/**
 * @author RettichLP
 */
public class UPlayerClassCannotBeInstantiated extends RuntimeException {

    public UPlayerClassCannotBeInstantiated(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
