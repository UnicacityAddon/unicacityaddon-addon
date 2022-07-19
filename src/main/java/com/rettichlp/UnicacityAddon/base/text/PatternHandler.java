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
}