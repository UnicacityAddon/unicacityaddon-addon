package com.rettichlp.unicacityaddon.base.models.api.response;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class Failure extends ResponseSchema {

    private final int httpStatusCode;
    private final String httpStatus;
    private final String info;

    public Failure(int httpStatusCode, String httpStatus, String info) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.info = info;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getInfo() {
        return info;
    }
}