package com.rettichlp.UnicacityAddon.base.updater;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.labymod.main.LabyMod;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/UpdateCommand.java">UCUtils by paulzhng</a>
 */
public class Updater {

    public static String latestVersion = UnicacityAddon.VERSION;
    public static File modFile;

    private static final File UPDATE_FILE = new File(System.getProperty("java.io.tmpdir"), "UnicacityAddon-update.jar");
    private static boolean replace;

    public static void updateChecker() {
        latestVersion = getLatestVersion();
        if (latestVersion.equals(UnicacityAddon.VERSION)) return;

        if (!ConfigElements.getAutomatedUpdate()) {
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Update verfügbar!", "Es ist Version " + ColorCode.DARK_AQUA.getCode() + "v" + latestVersion + ColorCode.WHITE.getCode() + " verfügbar.");

            AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                    .info().space()
                    .of("Es ist").advance().space()
                    .of("v" + latestVersion).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Changelog").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/latest")
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
        } else update();
    }

    public static void update() {
        UPlayer p = AbstractionLayer.getPlayer();

        try {
            if (SystemUtils.IS_OS_WINDOWS) windowsUpdate();
            else unixUpdate();
        } catch (IOException e) {
            UnicacityAddon.LOGGER.catching(e);
            p.sendErrorMessage("Update konnte nicht heruntergeladen werden.");
            LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Update konnte nicht heruntergeladen werden.");
            return;
        }

        p.sendInfoMessage("Die neuste Version wurde heruntergeladen. Starte dein Spiel neu um das Update abzuschließen!");
        LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Update heruntergeladen!", "Starte dein Spiel neu!");
    }

    private static void windowsUpdate() throws IOException {
        FileUtils.copyURLToFile(new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/download/v" + Updater.latestVersion + "/UnicacityAddon-" + Updater.latestVersion + ".jar"), UPDATE_FILE, 10000, 10000);

        replace = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!replace) return;
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
        FileUtils.copyURLToFile(new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/download/v" + Updater.latestVersion + "/UnicacityAddon-" + Updater.latestVersion + ".jar"), modFile, 10000, 10000);
    }

    private static void replaceJar() throws IOException {
        String batContent = "@echo off\n" +
                "\n" +
                "set /a \"i=0\"\n" +
                "set /a \"x=60\"\n" +
                "set from_file=" + UPDATE_FILE.getAbsolutePath() + "\n" +
                "set to_file=" + modFile.getAbsolutePath() + "\n" +
                "set to_file_rename=" + modFile.getName().replace(UnicacityAddon.VERSION, Updater.latestVersion) + "\n" +
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
            URLConnection con = new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/latest").openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            is.close();
            return con.getURL().toString().replace("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/tag/v", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}