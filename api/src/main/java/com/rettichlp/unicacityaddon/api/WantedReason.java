package com.rettichlp.unicacityaddon.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class WantedReason {

    private final String reason;
    private final int points;
}