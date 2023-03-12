package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ManagementUser {

    private final boolean active;
    private final String uuid;
    private final String version;
}