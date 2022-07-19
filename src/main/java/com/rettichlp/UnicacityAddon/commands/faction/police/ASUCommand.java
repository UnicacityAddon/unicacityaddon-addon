package com.rettichlp.UnicacityAddon.commands.faction.police;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
public class ASUCommand extends CommandBase {

    private final Timer timer = new Timer();

    @Override @Nonnull public String getName() {
        return "asu";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/asu [Spieler...] [Grund] (-v/-b/-fsa/-wsa)";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 2) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        Set<Flag> flags = getFlags(args);
        int reasonIndex = args.length - flags.size() - 1;

        List<String> players = Arrays.asList(args).subList(0, reasonIndex);
        String reason = args[reasonIndex];

        WantedReason wantedReason = null;
        for (WantedReason wanted : WantedReason.values()) {
            if (wanted.getReason().equals(reason)) {
                wantedReason = wanted;
            }
        }

        if (wantedReason == null) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Der Wantedgrund wurde nicht gefunden." + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        String wantedReasonString = wantedReason.getReason().replace('-', ' ');
        int wantedReasonAmount = wantedReason.getAmount();

        for (Flag flag : flags) {
            wantedReasonString = flag.modifyWantedReasonString(wantedReasonString);
            wantedReasonAmount = flag.modifyWantedReasonAmount(wantedReasonAmount);
        }

        giveWanteds(p, wantedReasonString, wantedReasonAmount, players);
    }

    private void giveWanteds(UPlayer issuer, String reason, int amount, List<String> players) {
        int maxAmount = Math.min(amount, 69);

        if (players.size() > 14) {
            timer.scheduleAtFixedRate(new TimerTask() {
                private int i;

                @Override
                public void run() {
                    if (i >= players.size()) {
                        cancel();
                        return;
                    }

                    String player = players.get(i++);

                    issuer.sendChatMessage("/su " + maxAmount + " " + player + " " + reason);
                    System.out.println("/su " + maxAmount + " " + player + " " + reason);
                }
            }, 0, TimeUnit.SECONDS.toMillis(1));
        } else{
            for (String player : players) {
                issuer.sendChatMessage("/su " + amount + " " + player + " " + reason);
                System.out.println("/su " + amount + " " + player + " " + reason);
            }
        }
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            List<String> tabCompletions = Arrays.stream(WantedReason.values()).map(WantedReason::getReason).sorted().collect(Collectors.toList());
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());

            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));

            tabCompletions.addAll(Arrays.stream(Flag.values()).map(Flag::getFlagArgument).sorted().collect(Collectors.toList()));

            return tabCompletions;
        }
    }

    private Set<Flag> getFlags(String[] args) {
        Set<Flag> flags = new HashSet<>();

        for (String arg : args) {
            Flag flag = Flag.getFlag(arg);

            if (flag != null)
                flags.add(flag);
        }

        return flags;
    }

    private enum WantedReason {
        WP_1("Massenmord-+-Waffenscheinabnahme", 69),
        WP_2("Massenmord", 69),

        WP_3("Terrorismus", 65),
        WP_4("Doppelmord", 65),
        WP_5("Geiselnahme", 65),

        WP_6("Mord", 60),

        WP_7("Versuchter-Mord", 55),
        WP_8("Grob-fahrlässiges-Überfahren-eines-anderen-Verkehrsteilnehmers-mit-anschließender-Fahrerflucht-+-Führerscheinabnahme", 55),
        WP_9("Grob-fahrlässiges-Überfahren-eines-anderen-Verkehrsteilnehmers", 55),

        WP_10("Totschlag", 50),
        WP_11("Grob-fahrlässige-Tötung", 50),
        WP_12("Gebietsbesetzung", 50),
        WP_13("Unterlassene-Hilfeleistung-mit-anschließender-Todesfolge", 50),
        WP_14("Einbruch-in-geschlossene-Räume-einer-staatlichen-Einrichtung", 50),

        WP_15("Vergewaltigung", 45),
        WP_16("Menschenhandel", 45),
        WP_17("Besitz-von-Betäubungsmitteln-(51+-Gramm)", 45),
        WP_18("Schwerer-Diebstahl", 45),
        WP_19("Sperrgebiet", 45),

        WP_20("Waffentransport", 40),
        WP_21("Vermummen-in-der-Öffentlichkeit", 40),
        WP_22("Schwerer-Hausfriedensbruch", 40),
        WP_23("Schutz-eines-hochgradig-Gesuchten", 40),
        WP_24("Räuberische-Erpressung", 40),
        WP_25("Maskieren-in-der-Öffentlichkeit", 40),
        WP_26("Leichenbewachung", 40),
        WP_27("Herstellen-von-Betäubungsmitteln", 40),
        WP_28("Freiheitsberaubung", 40),
        WP_29("Besitz-von-Betäubungsmitteln-(41-bis-50-Gramm)", 40),
        WP_30("Amtsanmaßung", 40),
        WP_31("Anstiftung-zum-Mord", 40),
        WP_32("Tragen-eines-Tarnanzuges", 40),

        WP_33("Waffen-im-Medic-Dienst", 35),
        WP_34("Volksverhetzung", 35),
        WP_35("Sexuelle-Belästigung", 35),
        WP_36("Tierquälerei", 35),
        WP_37("Schwere-Körperverletzung", 35),
        WP_38("Stalking", 35),
        WP_39("Raub", 35),
        WP_40("Nachstellung", 35),
        WP_41("Morddrohung", 35),
        WP_42("Mitgliedschaft-einer-kriminellen-Gruppierung", 35),
        WP_43("Bilden-bewaffneter-Gruppen", 35),
        WP_44("Besitz-von-Betäubungsmitteln-(31-bis-40-Gramm)", 35),
        WP_45("Nötigung-von-Vollstreckungsbeamten", 35),
        WP_46("Schwere-Sachbeschädigung", 35),
        WP_47("Entziehung-der-staatlichen-Gewalt", 35),
        WP_48("Weiterverkauf-von-Rezepten-oder-rezeptpflichtigen-Medikamenten", 35),

        WP_49("Waffenbesitz-ohne-Waffenschein", 30),
        WP_50("Rufmord", 30),
        WP_51("Illegale-Werbung", 30),
        WP_52("Hausfriedensbruch", 30),
        WP_53("Handeln-mit-Betäubungsmitteln", 30),
        WP_54("Erpressung", 30),
        WP_55("Diebstahl", 30),
        WP_56("Besitz-von-Betäubungsmitteln-(21-bis-30-Gramm)", 30),
        WP_57("Schutzhaft", 30),

        WP_58("Unterlassene-Hilfeleistung", 25),
        WP_59("Sachbeschädigung", 25),
        WP_60("Leichte-Körperverletzung", 25),
        WP_61("Entsagen-von-Anordnungen", 25),
        WP_62("Drohung", 25),
        WP_63("Betrug", 25),
        WP_64("Besitz-von-Betäubungsmitteln-(11-bis-20-Gramm)", 25),
        WP_65("Belästigung", 25),
        WP_66("Anstößiges-Verhalten-in-der-Öffentlichkeit", 25),
        WP_67("Anstößiges-Kleiden-in-der-Öffentlichkeit", 25),
        WP_68("Vandalismus", 25),
        WP_69("Nötigung", 25),
        WP_70("Vortäuschen-falscher-Tatsachen", 25),
        WP_71("Todeswunsch", 25),
        WP_72("Erregung-öffentlichen-Ärgernisses", 25),
        WP_73("Unfähigkeit-zum-Ausweisen-der-Identität-oder-Lizenzen", 25),
        WP_74("Beihilfe-zur-Entziehung-staatlicher-Gewalt", 25),

        WP_75("Störung-der-Religionsausübung", 20),
        WP_76("Provozieren-eines-Vollstreckungsbeamten", 20),
        WP_77("Missbrauch-von-Notrufen", 20),
        WP_78("Beteiligung-an-einer-Schlägerei", 20),
        WP_79("Besitz-von-Betäubungsmitteln-(1-bis-10-Gramm)", 20),
        WP_80("Behinderung-staatlicher-Institutionen", 20),
        WP_81("Behinderung-des-Verkehrs", 20),
        WP_82("Gaffen",20),
        WP_83("Lärmbelästigung", 20),
        WP_84("Zahlungsunfähigkeit-bei-einem-Bußgeldbescheid", 20),
        WP_85("Beleidigung", 20),
        WP_86("Konsum-von-Betäubungsmitteln", 20),

        WP_87("Weiterverkauf-geschützter-Literatur", 15),
        WP_88("Umweltverschmutzung", 15),
        WP_89("Unterschlagung", 15),

        WP_90("Angeln-ohne-Angelschein", 10),

        WP_91("Untersuchungshaft", 1),
        WP_92("Nachzahlen-eines-Bußgeldbescheides-(150$)", 1),
        WP_93("Nachzahlen-eines-Bußgeldbescheides-(250$)", 1),
        WP_94("Nachzahlen-eines-Bußgeldbescheides-(200$)", 1),
        WP_95("Nachzahlen-eines-Bußgeldbescheides-(350$)", 1);

        private final String description;
        private final int wanteds;

        WantedReason(String description, int wanteds) {
            this.description = description;
            this.wanteds = wanteds;
        }

        public String getReason() {
            return description;
        }

        public int getAmount() {
            return wanteds;
        }
    }

    private enum Flag {
        TRIED("-v", "Versuchte/r/s ", "", "x/2"),
        SUBSIDY("-b", "Beihilfe bei der/dem ", "", "x-10"),
        DRIVERS_LICENSE_WITHDRAWAL("-fsa", "", " + Führerscheinabnahme", "x"),
        WEAPONS_LICENSE_WITHDRAWAL("-wsa", "", " + Waffenscheinabnahme", "x");

        private final String flagArgument;
        private final String prependReason;
        private final String postponeReason;
        private final String wantedModification;

        Flag(String flagArgument, String prependReason, String postponeReason, String wantedModification) {
            this.flagArgument = flagArgument;
            this.prependReason = prependReason;
            this.postponeReason = postponeReason;
            this.wantedModification = wantedModification;
        }

        static Flag getFlag(String string) {
            for (Flag flag : Flag.values()) {
                if (flag.flagArgument.equalsIgnoreCase(string)) return flag;
            }

            return null;
        }

        private String getFlagArgument() {
            return flagArgument;
        }

        public String modifyWantedReasonString(String wantedReasonString) {
            return prependReason + wantedReasonString + postponeReason;
        }

        public int modifyWantedReasonAmount(int wantedReasonAmount) {
            return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wantedReasonAmount))).evaluate();
        }
    }
}