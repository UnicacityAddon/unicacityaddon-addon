package com.rettichlp.unicacityaddon.base.models.api.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Groups {

    private final boolean CEO;
    private final boolean DEV;
    private final boolean MOD;
    private final boolean SUP;
    private final boolean BET;
    private final boolean VIP;
}