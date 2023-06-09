package com.rettichlp.unicacityaddon.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class BlacklistReason {

    private final String reason;
    private final int kills;
    private final int price;
}