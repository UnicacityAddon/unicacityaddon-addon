package com.rettichlp.unicacityaddon.base.models.api.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class UnicacityAddon {

    private final long tokenGenerateTime;
    private final Groups groups;
    private final boolean blacklisted;
    private final String version;
}