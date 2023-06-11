package com.rettichlp.unicacityaddon.api.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Player {

    private final List<PlayerEntry> CEO;
    private final List<PlayerEntry> DEV;
    private final List<PlayerEntry> MOD;
    private final List<PlayerEntry> SUP;
    private final List<PlayerEntry> BETA;
    private final List<PlayerEntry> VIP;
    private final List<PlayerEntry> BLACKLIST;
    private final List<PlayerEntry> DYAVOL;
    private final List<PlayerEntry> NULL;
}