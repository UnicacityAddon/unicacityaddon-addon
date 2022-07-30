package com.rettichlp.UnicacityAddon.base.faction.blacklist;

import com.rettichlp.UnicacityAddon.base.faction.Faction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Blacklist {

    private List<BlacklistEntry> calderon;
    private List<BlacklistEntry> kerzakov;
    private List<BlacklistEntry> lacosanostra;
    private List<BlacklistEntry> obrien;
    private List<BlacklistEntry> triaden;
    private List<BlacklistEntry> westsideballas;

    public Blacklist() {
    }

    public void setBlacklist(Faction faction, List<BlacklistEntry> blacklistEntryList) {
        switch (faction) {
            case CALDERON:
                this.calderon = blacklistEntryList;
                break;
            case KERZAKOV:
                this.kerzakov = blacklistEntryList;
                break;
            case LACOSANOSTRA:
                this.lacosanostra = blacklistEntryList;
                break;
            case OBRIEN:
                this.obrien = blacklistEntryList;
                break;
            case TRIADEN:
                this.triaden = blacklistEntryList;
                break;
            case WESTSIDEBALLAS:
                this.westsideballas = blacklistEntryList;
                break;
        }
    }

    public List<BlacklistEntry> getBlacklist(Faction faction) {
        switch (faction) {
            case CALDERON:
                return this.calderon;
            case KERZAKOV:
                return this.kerzakov;
            case LACOSANOSTRA:
                return this.lacosanostra;
            case OBRIEN:
                return this.obrien;
            case TRIADEN:
                return this.triaden;
            case WESTSIDEBALLAS:
                return this.westsideballas;
        }
        return Collections.emptyList();
    }

    public static String getBlacklistData() {
        String blacklistData;
        try {
            File blacklistDataFile = new File("blacklistDataFile.json");
            Path path = Paths.get(blacklistDataFile.toURI());
            Stream<String> lines = Files.lines(path);
            blacklistData = lines.collect(Collectors.joining("\n"));
            lines.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return blacklistData;
    }
}