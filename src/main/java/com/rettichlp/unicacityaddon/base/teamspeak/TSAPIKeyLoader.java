package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
  * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class TSAPIKeyLoader {

    private final String[] teamSpeakClientNames = new String[]{"TS3Client", "TeamSpeak 3 Client", "TeamSpeak", "TeamSpeak 3", "ts3"};
    private final List<File> possibleConfigDirectories = new ArrayList<>();

    public void load() throws IOException {
        if (!ConfigElements.getTeamspeakAPIKey().isEmpty())
            return;

        loadPossibleConfigDirectories();
        for (File possibleConfigDirectory : possibleConfigDirectories) {
            File clientQueryIni = new File(possibleConfigDirectory, "clientquery.ini");
            if (!clientQueryIni.exists())
                continue;

            String apiKey = loadAPIKey(clientQueryIni);
            if (apiKey == null)
                continue;

            ConfigElements.setTeamspeakAPIKey(apiKey);
            return;
        }
    }

    private String loadAPIKey(File clientQueryIni) throws IOException {
        for (String line : FileUtils.readLines(clientQueryIni, StandardCharsets.UTF_8)) {
            if (!line.startsWith("api_key"))
                continue;

            String apiKey = line.split("=")[1];
            if (apiKey.length() != 29)
                continue;

            return apiKey;
        }

        return null;
    }

    private void loadPossibleConfigDirectories() {
        List<File> possibleParentFolders = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            File appData = new File(System.getenv("AppData"));
            File local = new File(appData.getParentFile(), "Local");
            File programFilesX86 = new File(System.getenv("ProgramFiles(X86)"));
            File programFiles = new File(System.getenv("ProgramFiles"));

            possibleParentFolders.add(appData);
            possibleParentFolders.add(local);
            possibleParentFolders.add(programFilesX86);
            possibleParentFolders.add(programFiles);
        } else {
            File userHome = new File(System.getProperty("user.home"));
            File library = new File(userHome, "Library");
            File applicationSupport = new File(library, "Application Support");

            possibleParentFolders.add(userHome);
            possibleParentFolders.add(library);
            possibleParentFolders.add(applicationSupport);
        }

        List<File> possibleTeamSpeakFolders = new ArrayList<>();
        for (File possibleParentFolder : possibleParentFolders) {
            for (String teamSpeakClientName : teamSpeakClientNames) {
                possibleTeamSpeakFolders.add(new File(possibleParentFolder, teamSpeakClientName));
            }
        }

        for (File possibleTeamSpeakFolder : possibleTeamSpeakFolders) {
            possibleConfigDirectories.add(new File(possibleTeamSpeakFolder, "config"));
            possibleConfigDirectories.add(possibleTeamSpeakFolder);
        }

        possibleConfigDirectories.removeIf(config -> !config.exists());
    }
}
