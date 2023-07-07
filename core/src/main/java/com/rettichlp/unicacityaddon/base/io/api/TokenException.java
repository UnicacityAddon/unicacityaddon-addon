package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;

/**
 * @author RettichLP
 */
public class TokenException extends UnicacityAddonException {

    public TokenException(UnicacityAddon unicacityAddon, String message) {
        super(unicacityAddon, message, "LabyConnect nicht verbunden.");
    }
}