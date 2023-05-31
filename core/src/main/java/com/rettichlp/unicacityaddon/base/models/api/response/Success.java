package com.rettichlp.unicacityaddon.base.models.api.response;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class Success extends ResponseSchema {

    private final String info;

    public Success(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}