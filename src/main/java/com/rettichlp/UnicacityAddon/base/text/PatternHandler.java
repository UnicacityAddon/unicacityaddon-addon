package com.rettichlp.UnicacityAddon.base.text;

import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;

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
     * {@link com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand}
     */
    public static final Pattern SERVICE_ARRIVED_PATTERN = Pattern.compile("^Ein Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) \\((\\d+)\\): \"(.*)\"$");
    public static final Pattern SERVICE_ACCEPTED_PATTERN = Pattern.compile("^((?:\\[UC])*[a-zA-Z0-9_]+) hat den Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) angenommen\\. \\((\\d+)m entfernt\\)$");
    public static final Pattern SERVICE_REQUEUED_PATTERN = Pattern.compile("^((?:\\[UC])*[a-zA-Z0-9_]+) hat den Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) \\((\\d+)\\) wieder geöffnet\\.$");
    public static final Pattern SERVICE_DELETED_PATTERN = Pattern.compile("^Der Notruf von ((?:\\[UC])*[a-zA-Z0-9_]+) wurde von ((?:\\[UC])*[a-zA-Z0-9_]+) gelöscht\\.$");
    public static final Pattern SERVICE_OVERVIEW_PATTERN = Pattern.compile("^\\nOffene Notrufe \\((\\d+)\\):(.*)$");
    public static final Pattern SERVICE_NO_SERVICE_PATTERN = Pattern.compile("^Fehler: Es ist kein Service offen\\.$");
}