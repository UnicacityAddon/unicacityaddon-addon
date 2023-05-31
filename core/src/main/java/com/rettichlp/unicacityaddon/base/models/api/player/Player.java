package com.rettichlp.unicacityaddon.base.models.api.player;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

import java.util.List;

/**
 * @author RettichLP
 */
public class Player extends ResponseSchema {

    private final List<PlayerEntry> CEO;
    private final List<PlayerEntry> DEV;
    private final List<PlayerEntry> MOD;
    private final List<PlayerEntry> SUP;
    private final List<PlayerEntry> BETA;
    private final List<PlayerEntry> VIP;
    private final List<PlayerEntry> BLACKLIST;
    private final List<PlayerEntry> DYAVOL;
    private final List<PlayerEntry> NULL;

    public Player(List<PlayerEntry> CEO, List<PlayerEntry> DEV, List<PlayerEntry> MOD, List<PlayerEntry> SUP, List<PlayerEntry> BETA, List<PlayerEntry> VIP, List<PlayerEntry> BLACKLIST, List<PlayerEntry> DYAVOL, List<PlayerEntry> NULL) {
        this.CEO = CEO;
        this.DEV = DEV;
        this.MOD = MOD;
        this.SUP = SUP;
        this.BETA = BETA;
        this.VIP = VIP;
        this.BLACKLIST = BLACKLIST;
        this.DYAVOL = DYAVOL;
        this.NULL = NULL;
    }

    public List<PlayerEntry> getCEO() {
        return CEO;
    }

    public List<PlayerEntry> getDEV() {
        return DEV;
    }

    public List<PlayerEntry> getMOD() {
        return MOD;
    }

    public List<PlayerEntry> getSUP() {
        return SUP;
    }

    public List<PlayerEntry> getBETA() {
        return BETA;
    }

    public List<PlayerEntry> getVIP() {
        return VIP;
    }

    public List<PlayerEntry> getBLACKLIST() {
        return BLACKLIST;
    }

    public List<PlayerEntry> getDYAVOL() {
        return DYAVOL;
    }

    public List<PlayerEntry> getNULL() {
        return NULL;
    }
}