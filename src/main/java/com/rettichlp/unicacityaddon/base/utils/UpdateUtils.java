package com.rettichlp.unicacityaddon.base.utils;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.models.ManagementUser;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.main.LabyMod;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/UpdateCommand.java">UCUtils by paulzhng</a>
 */
public class UpdateUtils {

    public static File modFile;

    private static String latestVersion = UnicacityAddon.VERSION;
    private static final File UPDATE_FILE = new File(System.getProperty("java.io.tmpdir"), "UnicacityAddon-update.jar");
    private static boolean replace;

    public static void update(boolean forceUpdate) {
        new Thread(() -> {
            UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Es wird nach einem Update gesucht...", true);
            if (updateChecker()) {
                if (ConfigElements.getAutomatedUpdate() || forceUpdate) {
                    // do update
                    try {
                        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Ein Update wird installiert...", true);

                        if (SystemUtils.IS_OS_WINDOWS) {
                            windowsUpdate();
                        } else {
                            unixUpdate();
                        }

                        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Update installiert! Starte Dein Spiel neu!", true);
                        LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Update installiert!", "Starte Dein Spiel neu!");
                    } catch (IOException e) {
                        UnicacityAddon.LOGGER.catching(e);
                        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.RED.getCode() + FormattingCode.BOLD.getCode() + "Update konnte nicht installiert werden!", true);
                        LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Update konnte nicht installiert werden!");
                    }
                } else {
                    // notify
                    AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                            .info().space()
                            .of("Es ist").advance().space()
                            .of("v" + latestVersion).color(ColorCode.AQUA)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Changelog").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/UnicacityAddon/unicacityaddon-addon/releases/latest")
                                    .advance().space()
                            .of("von").advance().space()
                            .of("UnicacityAddon").color(ColorCode.AQUA).advance().space()
                            .of("verfügbar!").advance().space()
                            .of("[").color(ColorCode.DARK_GRAY).advance()
                            .of("⬇").color(ColorCode.GREEN).underline()
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Aktualisieren").color(ColorCode.GREEN).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/updateunicacityaddon")
                                    .advance()
                            .of("]").color(ColorCode.DARK_GRAY).advance()
                            .createComponent());

                    LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Update verfügbar!", "Es ist Version " + ColorCode.DARK_AQUA.getCode() + "v" + latestVersion + ColorCode.WHITE.getCode() + " verfügbar.");
                }
            } else if (forceUpdate) {
                UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Du spielst bereits mit der neusten Version.", true);
            }
        }).start();
    }

    private static boolean updateChecker() {
        latestVersion = getLatestVersion();
        return !latestVersion.equals(UnicacityAddon.VERSION) && !UnicacityAddon.VERSION.contains("dev");
    }

    private static void windowsUpdate() throws IOException {
        FileUtils.copyURLToFile(new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/download/v" + UpdateUtils.latestVersion + "/UnicacityAddon-" + UpdateUtils.latestVersion + ".jar"), UPDATE_FILE, 10000, 10000);

        replace = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!replace)
                return;
            replace = false;

            try {
                replaceJar();
            } catch (IOException e) {
                UnicacityAddon.LOGGER.catching(e);
            }
        }));
    }

    private static void unixUpdate() throws IOException {
        // on unix, we can just overwrite the original file directly as there's no file locking in place
        FileUtils.copyURLToFile(new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/download/v" + UpdateUtils.latestVersion + "/UnicacityAddon-" + UpdateUtils.latestVersion + ".jar"), modFile, 10000, 10000);
    }

    private static void replaceJar() throws IOException {
        String batContent = "@echo off\n" +
                "\n" +
                "set /a \"i=0\"\n" +
                "set /a \"x=60\"\n" +
                "set from_file=" + UPDATE_FILE.getAbsolutePath() + "\n" +
                "set to_file=" + modFile.getAbsolutePath() + "\n" +
                "set to_file_rename=" + modFile.getName().replace(UnicacityAddon.VERSION, UpdateUtils.latestVersion) + "\n" +
                "\n" +
                "echo update-file: %from_file%\n" +
                "echo to-file: %to_file%\n" +
                "\n" +
                ":delete_loop\n" +
                "\ttimeout /T 1 > nul\n" +
                "\n" +
                "\t2>nul (\n" +
                "\t  move /Y \"%from_file%\" \"%to_file%\"\n" +
                "\t) && (\n" +
                "\t  ren \"%to_file%\" \"%to_file_rename%\"\n" +
                "\t) && (\n" +
                "\t\techo updated UnicacityAddon\n" +
                "\t\texit\n" +
                "\t) || (\n" +
                "\t\tset /a \"i = i + 1\"\n" +
                "\t\t\n" +
                "\t\tif %i% leq 60 (\n" +
                "\t\t\tset /a \"x = x - 1\"\n" +
                "\t\t\techo jar not yet available; waiting another second... [%x% tries left]\n" +
                "\t\t\tgoto delete_loop\n" +
                "\t\t)\n" +
                "\t\t\n" +
                "\t\techo 60 tries over; cancel update\n" +
                "\t\texit\n" +
                "\t)";

        File batFile = new File(System.getProperty("java.io.tmpdir"), "UnicacityAddon-update.bat");
        FileUtils.write(batFile, batContent, Charset.defaultCharset());

        Process proc = Runtime.getRuntime().exec("cmd /c start \"\" " + batFile.getAbsolutePath());
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            UnicacityAddon.LOGGER.catching(e);
        }
    }

    private static String getLatestVersion() {
        try {
            JsonObject response = APIRequest.sendManagementRequest();
            return response.get("latestVersion").getAsString();
        } catch (APIResponseException e) {
            e.sendInfo();
            return UnicacityAddon.VERSION;
        }
    }

    public static boolean hasPlayerLatestAddonVersion(String name) {
        Optional<ManagementUser> managementUserOptional = Syncer.MANAGEMENTUSERLIST.stream()
                .filter(mu -> mu.getUuid().equals(UnicacityAddon.MINECRAFT.getConnection().getPlayerInfo(name).getGameProfile().getId().toString().replace("-", "")))
                .findAny();

        return managementUserOptional.isPresent() && (managementUserOptional.get().getVersion().equals(latestVersion) || managementUserOptional.get().getVersion().contains("dev"));
    }
}