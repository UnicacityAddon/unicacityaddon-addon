package com.rettichlp.UnicacityAddon.base.text;

import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
public class PatternHandler {

    /**
     * {@link com.rettichlp.UnicacityAddon.base.faction.FactionHandler}
     */
    public static final Pattern NAME_PATTERN = Pattern.compile("<h4 class=\"h5 g-mb-5\"><strong>([a-zA-Z0-9_]+)");
    public static final Pattern RANK_PATTERN = Pattern.compile("<strong>Rang (\\d)( \\(Leader\\))*</strong>");

    /**
     * {@link com.rettichlp.UnicacityAddon.events.ATMInfoEventHandler}
     */
    public static final Pattern KONTOAUSZUG_PATTERN = Pattern.compile("^Ihr Bankguthaben beträgt: [+-](\\d)+\\$$");

    /**
     * {@link com.rettichlp.UnicacityAddon.modules.BombTimerModule}
     */
    public static final Pattern BOMB_PLACED_PATTERN = Pattern.compile("^News: ACHTUNG! Es wurde eine Bombe in der Nähe von .+ gefunden!$");
    public static final Pattern BOMB_REMOVED_PATTERN = Pattern.compile("^News: Die Bombe konnte (?:nicht|erfolgreich) entschärft werden!$");

    /**
     * {@link com.rettichlp.UnicacityAddon.modules.CarOpenModule}
     */
    public static final Pattern CAR_OPEN_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ aufgeschlossen\\.$");
    public static final Pattern CAR_CLOSE_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ abgeschlossen\\.$");

    /**
     * {@link com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand}
     */
    public static final Pattern REINFORCEMENT_PATTERN = Pattern.compile("^(.+ ((?:\\[UC])*[a-zA-Z0-9_]+)): Benötige Verstärkung! -> X: (-*\\d+) \\| Y: (-*\\d+) \\| Z: (-*\\d+)$");
    public static final Pattern ON_THE_WAY_PATTERN = Pattern.compile("^(.+ (?:\\[UC])*([a-zA-Z0-9_]+)): ((?:\\[UC])*[a-zA-Z0-9_]+), ich bin zu deinem Verstärkungsruf unterwegs! \\((\\d+) Meter entfernt\\)$");

    /**
     * {@link com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler}
     */
    public static final Pattern SERVICE_ARRIVED_PATTERN = Pattern.compile("^Ein Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) \\((\\d+)\\): \"(.*)\"$");
    public static final Pattern SERVICE_ACCEPTED_PATTERN = Pattern.compile("^((?:\\[UC])*[a-zA-Z0-9_]+) hat den Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) angenommen\\. \\((\\d+)m entfernt\\)$");
    public static final Pattern SERVICE_REQUEUED_PATTERN = Pattern.compile("^((?:\\[UC])*[a-zA-Z0-9_]+) hat den Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) \\((\\d+)\\) wieder geöffnet\\.$");
    public static final Pattern SERVICE_DELETED_PATTERN = Pattern.compile("^Der Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) wurde von ((?:\\[UC])*[a-zA-Z0-9_]+) gelöscht\\.$");
    public static final Pattern SERVICE_OVERVIEW_PATTERN = Pattern.compile("^\\nOffene Notrufe \\((\\d+)\\):(.*)$");
    public static final Pattern SERVICE_NO_SERVICE_PATTERN = Pattern.compile("^Fehler: Es ist kein Service offen\\.$");

    /**
     * {@link com.rettichlp.UnicacityAddon.events.faction.police.WantedEventHandler}
     */
    public static final Pattern WANTED_LIST_ENTRY_PATTERN = Pattern.compile("^ {2}- (?:\\[UC])*([a-zA-Z0-9_]+) \\| (\\d+) WPS \\((.+)\\)$");
    public static final Pattern WANTED_GIVEN_REASON_PATTERN = Pattern.compile("^HQ: Gesuchter: (?:\\[UC])*([a-zA-Z0-9_]+)\\. Grund: (.+)$");
    public static final Pattern WANTED_GIVEN_POINTS_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*([a-zA-Z0-9_]+)'s momentanes WantedLevel: (\\d+)$");
    public static final Pattern WANTED_DELETED_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*([a-zA-Z0-9_]+) wurde von (?:\\[UC])*[a-zA-Z0-9_]+ eingesperrt\\.$" +
            "|^HQ: (?:\\[UC])*([a-zA-Z0-9_]+) wurde von (?:\\[UC])*[a-zA-Z0-9_]+ getötet\\.$" +
            "|^HQ: .+ (?:\\[UC])*[a-zA-Z0-9_]+ hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)*(?: seine| ihre)* Akten gelöscht, over\\.$");
}