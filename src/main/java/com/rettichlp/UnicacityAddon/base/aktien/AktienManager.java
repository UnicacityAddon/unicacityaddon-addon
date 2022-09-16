package com.rettichlp.UnicacityAddon.base.aktien;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.json.Shares;
import com.rettichlp.UnicacityAddon.base.json.SharesEntry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AktienManager {

    private List<SharesEntry> SHARES = new ArrayList<>();

    public AktienManager() {
        loadSharesFromFile();
    }

    public void addShare(Aktie aktie, int buyValue) {
        SharesEntry sharesEntry = new SharesEntry(aktie, buyValue);
        SHARES.add(sharesEntry);
        saveSharesToFile();
    }

    public void removeShare(Aktie aktie) {
        SharesEntry sharesEntry = SHARES.stream().filter(sE -> sE.getAktie().equals(aktie)).findFirst().orElse(null);
        if (sharesEntry == null) return;
        SHARES.remove(sharesEntry);
        saveSharesToFile();
    }

    public int calculateStockValue(Aktie aktie, int currentValue) {
        return getSharesData(aktie).size() * currentValue;
    }

    public int calculateWin(Aktie aktie, int currentValue) {
        return calculateStockValue(aktie, currentValue) - getBuyValue(aktie);
    }

    public int getBuyValue(Aktie aktie) {
        return getSharesData(aktie).stream().reduce(0, Integer::sum);
    }

    private List<Integer> getSharesData(Aktie aktie) {
        return SHARES.stream().filter(sharesEntry -> sharesEntry.getAktie().equals(aktie))
                .map(SharesEntry::getBuyValue).collect(Collectors.toList());
    }

    private void loadSharesFromFile() {
        try {
            File sharesDataFile = FileManager.getSharesDataFile();
            if (sharesDataFile == null) return;
            Gson g = new Gson();
            String jsonData = FileUtils.readFileToString(sharesDataFile, StandardCharsets.UTF_8.toString());

            if (jsonData.isEmpty()) return;

            SHARES = g.fromJson(jsonData, Shares.class).getSharesEntryList();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void saveSharesToFile() {
        try {
            File sharesDataFile = FileManager.getSharesDataFile();
            if (sharesDataFile == null) return;
            Gson g = new Gson();
            Shares shares = new Shares();
            shares.setSharesEntryList(SHARES);
            FileUtils.writeStringToFile(sharesDataFile, g.toJson(shares), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}