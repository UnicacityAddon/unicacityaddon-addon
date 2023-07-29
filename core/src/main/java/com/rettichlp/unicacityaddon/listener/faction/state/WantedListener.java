package com.rettichlp.unicacityaddon.listener.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.function.Predicate;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class WantedListener {

    private static long wantedsShown;

    private final UnicacityAddon unicacityAddon;

    public WantedListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        boolean hqMessages = this.unicacityAddon.configuration().message().hq().get();

        Matcher wantedGivenPointsMatcher = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(msg);
        if (wantedGivenPointsMatcher.find()) {
            String name = wantedGivenPointsMatcher.group(1);
            int wantedPoints = Integer.parseInt(wantedGivenPointsMatcher.group(2));

            this.unicacityAddon.nameTagService().getWantedList().stream()
                    .filter(wanted -> wanted.getName().equals(name))
                    .findFirst()
                    .ifPresent(wanted -> wanted.setAmount(wantedPoints));

            this.unicacityAddon.utilService().debug("[WANTED] " + name + " (set [REASON] = " + wantedPoints + ")");

            if (hqMessages) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(wantedGivenPointsMatcher.group(2)).color(ColorCode.BLUE).advance().space()
                        .of("Wanteds").color(ColorCode.BLUE).advance().createComponent());
            }

            return;
        }

        Matcher wantedGivenReasonMatcher = PatternHandler.WANTED_GIVEN_REASON_PATTERN.matcher(msg);
        if (wantedGivenReasonMatcher.find()) {
            String name = wantedGivenReasonMatcher.group(1);
            String reason = wantedGivenReasonMatcher.group(2);

            this.unicacityAddon.nameTagService().getWantedList().add(new Wanted(name, reason, 0));
            this.unicacityAddon.utilService().debug("[WANTED] " + name + " (set [" + reason + "] = " + 0 + ")");

            if (hqMessages) {
                e.setMessage(Message.getBuilder().of("Gesucht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(wantedGivenReasonMatcher.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(wantedGivenReasonMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            }

            return;
        }

        Matcher wantedReasonMatcher = PatternHandler.WANTED_REASON_PATTERN.matcher(msg);
        if (wantedReasonMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                    .of(wantedReasonMatcher.group(1)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(wantedReasonMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher wantedDeleteMatcher = PatternHandler.WANTED_DELETE_PATTERN.matcher(msg);
        if (wantedDeleteMatcher.find()) {
            String playerName = wantedDeleteMatcher.group(1);
            String targetName = wantedDeleteMatcher.group(2);

            int wpAmount = getWpAmountAndDelete(targetName);

            if (hqMessages) {
                e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(targetName).color(ColorCode.BLUE).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(String.valueOf(wpAmount)).color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(playerName).color(ColorCode.BLUE).advance().space()
                        .createComponent());
            }

            return;
        }

        Matcher wantedKillMatcher = PatternHandler.WANTED_KILL_PATTERN.matcher(msg);
        if (wantedKillMatcher.find()) {
            String targetName = wantedKillMatcher.group(1);
            String playerName = wantedKillMatcher.group(2);

            int wpAmount = getWpAmountAndDelete(targetName);
            boolean isAddonPlayer = playerName.equals(this.unicacityAddon.player().getName());

            if (isAddonPlayer) {
                this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.KILL);
            }

            if (hqMessages) {
                e.setMessage(Message.getBuilder().of("Getötet").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(targetName).color(ColorCode.BLUE).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(String.valueOf(wpAmount)).color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(playerName).color(ColorCode.BLUE).advance()
                        .of(isAddonPlayer ? " [⬆]" : "").color(ColorCode.BLUE)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Aktivität eintragen").color(ColorCode.DARK_BLUE).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/activitytest kills")
                                .advance()
                        .createComponent());
            }

            return;
        }

        Matcher wantedJailMatcher = PatternHandler.WANTED_ARREST_PATTERN.matcher(msg);
        if (wantedJailMatcher.find()) {
            String targetName = wantedJailMatcher.group(1);
            String playerName = wantedJailMatcher.group(2);

            int wpAmount = getWpAmountAndDelete(targetName);
            boolean isAddonPlayer = playerName.equals(this.unicacityAddon.player().getName());

            if (hqMessages) {
                e.setMessage(Message.getBuilder().of("Eingesperrt").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(targetName).color(ColorCode.BLUE).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(String.valueOf(wpAmount)).color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(playerName).color(ColorCode.BLUE).advance()
                        .of(isAddonPlayer ? " [⬆]" : "").color(ColorCode.BLUE)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Aktivität eintragen").color(ColorCode.DARK_BLUE).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/activitytest verhaftung")
                                .advance()
                        .createComponent());
            }

            return;
        }

        Matcher wantedUnarrestMatcher = PatternHandler.WANTED_UNARREST_PATTERN.matcher(msg);
        if (wantedUnarrestMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Entlassung").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(wantedUnarrestMatcher.group(2)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(wantedUnarrestMatcher.group(1)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher wantedListEntryMatcher = PatternHandler.WANTED_LIST_PATTERN.matcher(msg);
        if (wantedListEntryMatcher.find() && System.currentTimeMillis() - wantedsShown < 1000L) {
            String name = wantedListEntryMatcher.group(1);
            String wpReason = wantedListEntryMatcher.group(3);
            int wpAmount = Integer.parseInt(wantedListEntryMatcher.group(2));
            boolean isAfk = wantedListEntryMatcher.group(4).contains("AFK");

            ColorCode colorCode = this.unicacityAddon.nameTagService().getWpColor(wpAmount);

            e.setMessage(Message.getBuilder().space().space()
                    .of("»").color(ColorCode.DARK_GRAY).advance().space()
                    .of(name).color(colorCode).advance().space()
                    .of("|").color(ColorCode.DARK_GRAY).advance().space()
                    .of(wpReason).color(colorCode).advance().space()
                    .of("(").color(ColorCode.GRAY).advance()
                    .of(String.valueOf(wpAmount)).color(ColorCode.BLUE).advance()
                    .of(")").color(ColorCode.GRAY).advance().space()
                    .of(isAfk ? "|" : "").color(ColorCode.DARK_GRAY).advance().space()
                    .of(isAfk ? "AFK" : "").color(ColorCode.GRAY).advance()
                    .createComponent());

            this.unicacityAddon.nameTagService().getWantedList().add(new Wanted(name, wpReason, wpAmount));
            this.unicacityAddon.utilService().debug("[WANTED] " + name + " (set [" + wpReason + "] = " + wpAmount + ")");

            return;
        }

        Matcher giveDrivingLicenseMatcher = PatternHandler.GIVE_DRIVING_LICENSE_PATTERN.matcher(msg);
        if (giveDrivingLicenseMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Führerscheinrückgabe").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(giveDrivingLicenseMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(giveDrivingLicenseMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher takeDrivingLicenseMatcher = PatternHandler.TAKE_DRIVING_LICENSE_PATTERN.matcher(msg);
        if (takeDrivingLicenseMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Führerscheinabnahme").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeDrivingLicenseMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeDrivingLicenseMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher giveGunLicenseMatcher = PatternHandler.GIVE_GUN_LICENSE_PATTERN.matcher(msg);
        if (giveGunLicenseMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Waffenscheinrückgabe").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(giveGunLicenseMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(giveGunLicenseMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher takeGunLicenseMatcher = PatternHandler.TAKE_GUN_LICENSE_PATTERN.matcher(msg);
        if (takeGunLicenseMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Waffenscheinabnahme").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeGunLicenseMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeGunLicenseMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher takeGunsMatcher = PatternHandler.TAKE_GUNS_PATTERN.matcher(msg);
        if (takeGunsMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Waffenabnahme").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeGunsMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeGunsMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher takeDrugsMatcher = PatternHandler.TAKE_DRUGS_PATTERN.matcher(msg);
        if (takeDrugsMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Drogenabnahme").color(ColorCode.RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeDrugsMatcher.group(3)).color(ColorCode.BLUE).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(takeDrugsMatcher.group(2)).color(ColorCode.BLUE).advance().createComponent());
            return;
        }

        Matcher trackerMatcher = PatternHandler.TRACKER_AGENT_PATTERN.matcher(msg);
        if (trackerMatcher.find() && hqMessages) {
            e.setMessage(Message.getBuilder().of("Peilsender").color(ColorCode.RED).bold().advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(trackerMatcher.group(1)).color(ColorCode.DARK_AQUA).advance().space()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(trackerMatcher.group(2)).color(ColorCode.GOLD).advance().createComponent());
            return;
        }

        if (msg.equals("Online Spieler mit WantedPunkten:")) {
            this.unicacityAddon.nameTagService().getWantedList().clear();
            wantedsShown = System.currentTimeMillis();
        }
    }

    private int getWpAmountAndDelete(String targetName) {
        Predicate<Wanted> predicate = wanted -> wanted.getName().equals(targetName);
        int wpAmount = this.unicacityAddon.nameTagService().getWantedList().stream().filter(predicate).findAny().map(Wanted::getAmount).orElse(0);
        this.unicacityAddon.nameTagService().getWantedList().removeIf(predicate);
        this.unicacityAddon.utilService().debug("[WANTED] " + targetName + " (remove)");
        return wpAmount;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Wanted {

        private String name;
        private String reason;
        private int amount;
    }
}