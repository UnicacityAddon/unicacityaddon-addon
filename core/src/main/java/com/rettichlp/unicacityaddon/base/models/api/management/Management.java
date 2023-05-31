package com.rettichlp.unicacityaddon.base.models.api.management;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

import java.util.List;

/**
 * @author RettichLP
 */
public class Management extends ResponseSchema {

    private final long requests;
    private final long totalClients;
    private final long activeClients;
    private final String latestVersion;
    private final List<ManagementVersion> versions;

    public Management(long requests, long totalClients, long activeClients, String latestVersion, List<ManagementVersion> versions) {
        this.requests = requests;
        this.totalClients = totalClients;
        this.activeClients = activeClients;
        this.latestVersion = latestVersion;
        this.versions = versions;
    }

    public long getRequests() {
        return requests;
    }

    public long getTotalClients() {
        return totalClients;
    }

    public long getActiveClients() {
        return activeClients;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public List<ManagementVersion> getVersions() {
        return versions;
    }
}