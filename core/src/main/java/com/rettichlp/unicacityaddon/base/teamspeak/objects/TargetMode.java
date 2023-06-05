package com.rettichlp.unicacityaddon.base.teamspeak.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
@Getter
@AllArgsConstructor
public enum TargetMode {

    PRIVATE(1),
    CHANNEL(2),
    SERVER(3);

    private final int id;

    public static TargetMode byID(int id) {
        for (TargetMode targetMode : TargetMode.values()) {
            if (targetMode.getId() == id)
                return targetMode;
        }

        return null;
    }
}
