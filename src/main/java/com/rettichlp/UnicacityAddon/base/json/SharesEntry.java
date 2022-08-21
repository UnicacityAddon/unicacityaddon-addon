package com.rettichlp.UnicacityAddon.base.json;

import com.rettichlp.UnicacityAddon.base.aktien.Aktie;

/**
 * @author RettichLP
 */
public class SharesEntry {

    private final Aktie aktie;
    private final int buyValue;

    public SharesEntry(Aktie aktie, int buyValue) {
        this.aktie = aktie;
        this.buyValue = buyValue;
    }

    public Aktie getAktie() {
        return aktie;
    }

    public int getBuyValue() {
        return buyValue;
    }
}