package com.rettichlp.unicacityaddon.base.models.api.management;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Management {

    private final long requests;
    private final long totalClients;
    private final long activeClients;
    private final String latestVersion;
    private final List<ManagementVersion> versions;
}