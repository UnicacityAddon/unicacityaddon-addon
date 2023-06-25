package com.rettichlp.unicacityaddon.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Failure {

    private final int httpStatusCode;
    private final String httpStatus;
    private final String info;
}