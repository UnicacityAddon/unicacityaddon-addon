package com.rettichlp.unicacityaddon.api.management;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class ManagementUser {

    private final boolean active;
    private final String uuid;
    private final String version;
}