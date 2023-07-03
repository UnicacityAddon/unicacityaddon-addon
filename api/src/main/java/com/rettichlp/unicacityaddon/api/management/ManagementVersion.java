package com.rettichlp.unicacityaddon.api.management;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class ManagementVersion {

    private final String version;
    private final long users;
}