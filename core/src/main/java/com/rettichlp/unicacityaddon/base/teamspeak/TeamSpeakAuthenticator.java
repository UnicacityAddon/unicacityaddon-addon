/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.io.IOUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class TeamSpeakAuthenticator {

    private final UnicacityAddon unicacityAddon;
    private final TeamSpeakAPI teamSpeakAPI;
    private final List<Path> possibleDirectories;

    public TeamSpeakAuthenticator(UnicacityAddon unicacityAddon, TeamSpeakAPI teamSpeakAPI) {
        this.unicacityAddon = unicacityAddon;
        this.teamSpeakAPI = teamSpeakAPI;
        this.possibleDirectories = new ArrayList<>();

        OperatingSystem platform = OperatingSystem.getPlatform();
        if (platform == OperatingSystem.WINDOWS) {
            this.loadWindowsDirectories();
        } else if (platform == OperatingSystem.MACOS) {
            this.loadMacOsDirectories();
        } else if (platform == OperatingSystem.LINUX) {
            this.loadLinuxDirectories();
        } else {
            this.unicacityAddon.logger().warn("Cannot automatically resolve the Auth Key on " + platform + "!");
        }
    }

    public void authenticate() {
        for (Path possibleDirectory : this.possibleDirectories) {
            Path clientQueryPath = possibleDirectory.resolve("clientquery.ini");
            if (!IOUtil.exists(clientQueryPath)) {
                continue;
            }

            try {
                String contents = Files.readString(clientQueryPath);
                String[] split = contents.split("\n");
                for (String line : split) {
                    line = line.trim();
                    if (!line.startsWith("api_key=")) {
                        continue;
                    }

                    String apiKey = line.substring("api_key=".length()).replace("\r", "");
                    this.teamSpeakAPI.authenticate(apiKey);
                }
            } catch (IOException e) {
                this.unicacityAddon.logger().error(e.getMessage());
            }
        }
    }

    private void loadWindowsDirectories() {
        Path appData = Paths.get(System.getenv("AppData"));
        Path local = appData.getParent().resolve("Local");

        this.checkForPossibleDirectories(
                appData.resolve("TS3Client"),
                local.resolve("TS3Client")
        );
    }

    private void loadMacOsDirectories() {
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path library = userHome.resolve("Library");
        Path applicationSupport = library.resolve("Application Support");

        this.checkForPossibleDirectories(
                applicationSupport.resolve("TeamSpeak 3")
        );
    }

    private void loadLinuxDirectories() {
        Path userHome = Paths.get(System.getProperty("user.home"));

        this.checkForPossibleDirectories(
                userHome.resolve(".ts3client")
        );
    }

    private void checkForPossibleDirectories(Path... paths) {
        for (Path path : paths) {
            if (!IOUtil.exists(path)) {
                continue;
            }

            this.possibleDirectories.add(path);
            Path config = path.resolve("config");
            if (IOUtil.exists(config)) {
                this.possibleDirectories.add(config);
            }
        }
    }
}

