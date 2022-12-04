package com.rettichlp.UnicacityAddon.base.text;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;

import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
public class PatternHandler {

    /**
     * Quote: Rettich: "In welchem Forum ist der Vorschlag drin?" Dimiikou: "In Vorschläge" - RettichLP und Dimiikou, 09.10.2022
     */
    private static final String playerName = AbstractionLayer.getPlayer().getName();

    /**
     * Pattern for faction and util data
     *
     * @see com.rettichlp.UnicacityAddon.base.api.Syncer
     * @see com.rettichlp.UnicacityAddon.base.utils.ForgeUtils
     */
    public static final Pattern NAME_PATTERN = Pattern.compile("<h4 class=\"h5 g-mb-5\"><strong>(\\w+)");
    public static final Pattern RANK_PATTERN = Pattern.compile("<strong>Rang (\\d)( \\(Leader\\))*</strong>");
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[A-FK-OR0-9]");
    public static final Pattern STRIP_PREFIX_PATTERN = Pattern.compile("\\[[a-zA-Z0-9]+]");

    /**
     * Pattern for bomb timer
     *
     * @see com.rettichlp.UnicacityAddon.modules.BombTimerModule
     */
    public static final Pattern BOMB_PLACED_PATTERN = Pattern.compile("^News: ACHTUNG! Es wurde eine Bombe in der Nähe von .+ gefunden!$");
    public static final Pattern BOMB_REMOVED_PATTERN = Pattern.compile("^News: Die Bombe konnte (nicht|erfolgreich) entschärft werden!$");

    /**
     * Pattern for car interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.CarEventHandler
     */
    public static final Pattern CAR_OPEN_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ aufgeschlossen\\.$");
    public static final Pattern CAR_CLOSE_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ abgeschlossen\\.$");
    public static final Pattern CAR_POSITION_PATTERN = Pattern.compile("^\\[Car] Das Fahrzeug befindet sich bei . X: (-?\\d+) \\| Y: (-?\\d+) \\| Z: (-?\\d+)$");
    public static final Pattern CAR_CHECK_KFZ_PATTERN = Pattern.compile("^HQ: Das Fahrzeug mit dem Kennzeichen (?:null|.+) ist auf den Spieler (?:\\[UC])*([a-zA-Z0-9_]+) registriert, over.$" +
            "|^Kennzeichen: (?:null|.+) \\| Type: [a-zA-Z]+ \\| Besitzer: (?:\\[UC])*([a-zA-Z0-9_]+)$");

    /**
     * Pattern for reinforcement and sloc interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.faction.ReinforcementEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.ShareLocationEventHandler
     */
    public static final Pattern REINFORCEMENT_PATTERN = Pattern.compile("^(.+ ((?:\\[UC])*\\w+)): Benötige Verstärkung! -> X: (-*\\d+) \\| Y: (-*\\d+) \\| Z: (-*\\d+)$");
    public static final Pattern ON_THE_WAY_PATTERN = Pattern.compile("^(.+ (?:\\[UC])*(\\w+)): ((?:\\[UC])*\\w+), ich bin zu deinem Verstärkungsruf unterwegs! \\((\\d+) Meter entfernt\\)$");
    public static final Pattern SHARE_LOCATION_PATTERN = Pattern.compile("^(.+ (?:\\[UC])*\\w+): Positionsteilung für ([a-zA-Z0-9_, ]+)! -> X: (-*\\d+) \\| Y: (-*\\d+) \\| Z: (-*\\d+)$");

    /**
     * Pattern for service data
     *
     * @see com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.ServiceMessageEventHandler
     */
    public static final Pattern SERVICE_ARRIVED_PATTERN = Pattern.compile("^Ein Notruf von ((?:\\[UC])*\\w+) \\((\\d+)\\): \"(.*)\"$");
    public static final Pattern SERVICE_LOCATION_PATTERN = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\.$");
    public static final Pattern SERVICE_LOCATION_PATTERN_ONE_NEAREST = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)\\.$");
    public static final Pattern SERVICE_LOCATION_PATTERN_TWO_NEAREST = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\), ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)\\.$");
    public static final Pattern SERVICE_DONE_PATTERN = Pattern.compile("^Du hast den Service von (?:\\[UC])*(\\w+) als 'Erledigt' markiert!$");
    public static final Pattern SERVICE_ACCEPTED_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat den Notruf von ((?:\\[UC])*\\w+) angenommen\\. \\((\\d+)m entfernt\\)$");
    public static final Pattern SERVICE_REQUEUED_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat den Notruf von ((?:\\[UC])*\\w+) \\((\\d+)\\) wieder geöffnet\\.$");
    public static final Pattern SERVICE_DELETED_PATTERN = Pattern.compile("^Der Notruf von ((?:\\[UC])*\\w+) wurde von ((?:\\[UC])*\\w+) gelöscht\\.$");
    public static final Pattern SERVICE_OVERVIEW_PATTERN = Pattern.compile("^\\nOffene Notrufe \\((\\d+)\\):((.|\\n)*)$");
    public static final Pattern SERVICE_NO_SERVICE_PATTERN = Pattern.compile("^Fehler: Es ist kein Service offen\\.$");
    public static final Pattern SERVICE_BLOCKED_PATTERN = Pattern.compile("^Notrufe von ((?:\\[UC])*\\w+) wurden von ((?:\\[UC])*\\w+) blockiert\\.$");
    public static final Pattern SERVICE_UNBLOCKED_PATTERN = Pattern.compile("^Notrufe von ((?:\\[UC])*\\w+) wurden von ((?:\\[UC])*\\w+) wieder zugelassen\\.$");
    public static final Pattern SERVICE_CALL_BOX_PATTERN = Pattern.compile("^ {2}➡ (?:\\[UC])*([a-zA-Z0-9_]+) » (.+)$");

    /**
     * Pattern for name tag providing
     *
     * @see com.rettichlp.UnicacityAddon.events.DeathsKillsEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.blacklist.BlacklistEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.blacklist.BlacklistModifyEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.polizei.HQMessageEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler
     */
    public static final Pattern WANTED_LIST_ENTRY_PATTERN = Pattern.compile("^ {2}- (?:\\[UC])*(\\w+) \\| (\\d+) WPS \\((.+)\\)(| \\| AFK)$");
    public static final Pattern WANTED_GIVEN_REASON_PATTERN = Pattern.compile("^HQ: Gesuchter: (?:\\[UC])*(\\w+)\\. Grund: (.+)$");
    public static final Pattern WANTED_REASON = Pattern.compile("^HQ: Fahndungsgrund: (.+) \\| Fahndungszeit: (.+)\\.$");
    public static final Pattern WANTED_GIVEN_POINTS_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*(\\w+)'s momentanes WantedLevel: (\\d+)$");
    public static final Pattern WANTED_KILL = Pattern.compile("^HQ: (?:\\[UC])*([a-zA-Z0-9_]+) wurde von (?:\\[UC])*([a-zA-Z0-9_]+) getötet\\.$");
    public static final Pattern WANTED_DELETE = Pattern.compile("^HQ: .+ (?:\\[UC])*([a-zA-Z0-9_]+) hat (?:\\[UC])*([a-zA-Z0-9_]+)'s Akten gelöscht, over\\.$");
    public static final Pattern WANTED_JAIL = Pattern.compile("^HQ: (?:\\[UC])*([a-zA-Z0-9_]+) wurde von (?:\\[UC])*([a-zA-Z0-9_]+) eingesperrt\\.$");
    public static final Pattern WANTEDS_TICKET_PATTERN = Pattern.compile("^HQ: .+ (?:\\[UC])*([a-zA-Z0-9_]+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)*(?: seine| ihre)* Akten gelöscht, over\\.$");
    public static final Pattern WANTED_DELETED_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ eingesperrt\\.$" +
            "|^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ getötet\\.$" +
            "|^HQ: .+ (?:\\[UC])*\\w+ hat (?:\\[UC])*(\\w+)(?:'s)*(?: seine| ihre)* Akten gelöscht, over\\.$");
    public static final Pattern BLACKLIST_START_PATTERN = Pattern.compile("=== Blacklist .+ ===");
    public static final Pattern BLACKLIST_LIST_PATTERN = Pattern.compile("^ » (?:\\[UC])*(\\w+) \\| (.+) \\| (.+) \\| (\\d+) Kills \\| (\\d+)\\$(| \\(AFK\\))$");
    public static final Pattern BLACKLIST_ADDED_PATTERN = Pattern.compile("^\\[Blacklist] (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ auf die Blacklist gesetzt!$");
    public static final Pattern BLACKLIST_REMOVED_PATTERN = Pattern.compile("^\\[Blacklist] (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ von der Blacklist gelöscht!$");
    public static final Pattern CONTRACT_SET_PATTERN = Pattern.compile("^\\[Contract] Es wurde ein Kopfgeld auf (?:\\[UC])*(\\w+) \\(\\d+\\$\\) ausgesetzt\\.$");
    public static final Pattern CONTRACT_REMOVED_PATTERN = Pattern.compile("^\\[Contract] (?:\\[UC])*\\w+ hat (?:\\[UC])*(\\w+) von der Contract Liste gelöscht\\. \\[-\\d+]$" +
            "|^\\[Contract] (?:\\[UC])*(\\w+) hat (?:\\[UC])*(\\w+) getötet\\. Kopfgeld: \\d+\\$");

    /**
     * Pattern for job interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.job.FishermanEventHandler
     * @see com.rettichlp.UnicacityAddon.events.job.JobEventHandler
     */
    public static final Pattern FISHER_START = Pattern.compile("^\\[Fischer] Mit /findschwarm kannst du dir den nächsten Fischschwarm anzeigen lassen\\.$");
    public static final Pattern FISHER_SPOT_FOUND = Pattern.compile("^\\[Fischer] Du hast einen Fischschwarm gefunden!$");
    public static final Pattern FISHER_SPOT_LOSE = Pattern.compile("^\\[Fischer] Du hast dich dem Fischschwarm zu weit entfernt\\.$");
    public static final Pattern FISHER_CATCH_START = Pattern.compile("^\\[Fischer] Du hast ein Fischernetz ausgeworfen\\.$");
    public static final Pattern FISHER_CATCH_SUCCESS = Pattern.compile("^\\[Fischer] Du hast \\d+kg frischen Fisch gefangen! \\(\\d+kg\\)$");
    public static final Pattern FISHER_CATCH_FAILURE = Pattern.compile("^\\[Fischer] Du hast das Fischernetz verloren\\.\\.\\.$");
    public static final Pattern FISHER_END = Pattern.compile("^\\[Fischer] Du hast keine Netze mehr\\. Bring den gefangenen Fisch zurück zum Steg\\.$");
    public static final Pattern WASTE_JOB_START_PATTERN = Pattern.compile("^\\[Müllmann] Entleere bis zu \\d Mülltonnen an den Haustüren der Häuser und entlade hier alles\\.$");
    public static final Pattern WASTE_JOB_END_PATTERN = Pattern.compile("^\\[Müllmann] Du hast den Job beendet\\.$");
    public static final Pattern WASTE_JOB_COLLECT_END_PATTERN = Pattern.compile("^\\[Müllmann] Du hast genug Mülltonnen entleert\\.$");
    public static final Pattern NEWSPAPER_JOB_START_PATTERN = Pattern.compile("^\\[Zeitung] Bring bitte das alles zu Häuser deiner Wahl\\.$");
    public static final Pattern NEWSPAPER_JOB_END_PATTERN = Pattern.compile("^\\[Zeitung] Du hast den Job beendet\\.$");
    public static final Pattern PIZZA_START_PATTERN = Pattern.compile("^\\[Pizzalieferant] Hier kannst du die frischen Pizzen mit /getpizza abholen\\.$");
    public static final Pattern PIZZA_PICKUP_PATTERN = Pattern.compile("^\\[Pizzalieferant] Du hast eine Pizza für die Lieferung fertig gemacht\\. \\[(?<count>\\d+)/15]$");
    public static final Pattern TRANSPORT_DROP_PATTERN = Pattern.compile("^\\[Transport] Du hast eine (Kiste|Waffenkiste) abgeliefert\\.$" +
            "|^\\[Transport] Du hast ein Weizen Paket abgeliefert\\.$" +
            "|^\\[Transport] Du hast eine Schwarzpulverkiste abgeliefert\\.$");
    public static final Pattern DRINK_DROP_PATTERN = Pattern.compile("^\\[Bar] Du hast eine Flasche abgegeben!$");
    public static final Pattern TABAK_DROP_PATTERN = Pattern.compile("^\\[Tabakplantage] Bringe es nun zur Shishabar und gib es mit /droptabak ab\\.$");
    public static final Pattern TABAK_FINISH_PATTERN = Pattern.compile("^\\[Tabakplantage] Du hast (\\d+)g Tabak abgegeben\\.$");
    public static final Pattern MINERS_JOB_END_PATTERN = Pattern.compile("^\\[Steinbruch] Bring alles nun zum Lagerraum\\.$");

    /**
     * Pattern for medic interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.FirstAidEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.MedicationEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.ReviveEventHandler
     */
    public static final Pattern RECIPE_ACCEPT_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) möchte dir ein Rezept für 200\\$ verkaufen\\.$");
    public static final Pattern RECIPE_GIVE_PATTERN = Pattern.compile("^Du hast ((?:\\[UC])*\\w+) ein Rezept für (Antibiotika|Hustensaft|Schmerzmittel) ausgestellt\\.$");
    public static final Pattern REVIVE_BY_MEDIC_START_PATTERN = Pattern.compile("^Du wirst von (?:\\[UC])*(\\w+) wiederbelebt\\.$");
    public static final Pattern REVIVE_BY_MEDIC_FINISH_PATTERN = Pattern.compile("^Du lebst nun wieder\\.$");
    public static final Pattern REVIVE_START_PATTERN = Pattern.compile("^Du beginnst mit der Wiederbelebung von ((?:\\[UC])*\\w+)\\.");
    public static final Pattern FIRST_AID_RECEIVE_PATTERN = Pattern.compile("^\\[Erste-Hilfe] Notarzt (?:\\[UC])*(\\w+) hat dir einen Erste-Hilfe-Schein für 14 Tage ausgestellt\\.$");
    public static final Pattern FIRST_AID_LICENCE_PATTERN = Pattern.compile("^ {2}- Erste-Hilfe-Schein: Vorhanden$");

    /**
     * Pattern for communication interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.MobileEventHandler
     */
    public static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^Nummer von (?:\\[UC])*\\w+: (\\d+)$");
    public static final Pattern MOBILE_SMS_PATTERN = Pattern.compile("^Dein Handy klingelt! Eine Nachricht von (?:\\[UC])*(\\w+) \\((\\d+)\\)\\.$");
    public static final Pattern MOBILE_REMOVE_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat dir deine Kommunikationsgeräte abgenommen\\.$");
    public static final Pattern MOBILE_GET_PATTERN = Pattern.compile("^Du hast dein Handy genommen\\.$" +
            "|^((?:\\[UC])*\\w+) hat dir deine Kommunikationsgeräte wiedergegeben\\.$");

    /**
     * Pattern for bad faction interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.DBankMessageEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.FBIHackEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.GiftEigenbedarfEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.badfaction.PlantEventHandler
     */
    public static final Pattern PLANT_HARVEST_PATTERN = Pattern.compile("^\\[Plantage] Eine .+-Plantage wurde von (?:\\[UC])*(\\w+) geerntet\\. \\[\\d+g]$");
    public static final Pattern PLANT_USE_PATTERN = Pattern.compile("^\\[Plantage] Eine .+-Plantage wurde von (?:\\[UC])*(" + playerName + ") (gewässert|gedüngt)\\.$");
    public static final Pattern FBI_HACK_STARTED_PATTERN = Pattern.compile("^\\[Polizeicomputer] Du hast einen Hackversuch gestartet\\. Geschätzte Dauer: (\\d+) Sekunden\\.$");
    public static final Pattern DRUGDEAL_ENDED = Pattern.compile("^\\[Deal] (?:\\[UC])*(\\w+) hat den Deal angenommen\\.$" +
            "|^\\[Deal] (?:\\[UC])*(\\w+) hat das Angebot abgelehnt\\.$");
    public static final Pattern DBANK_DROP_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat (\\d+)g (Kokain|Marihuana|Methamphetamin|LSD) \\((Höchste|Gute|Mittlere|Schlechte) Reinheit\\) \\((\\d+)g\\) eingelagert\\.$");
    public static final Pattern DBANK_GET_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat (\\d+)g (Kokain|Marihuana|Methamphetamin|LSD) \\((Höchste|Gute|Mittlere|Schlechte) Reinheit\\) \\((\\d+)g\\) aus der Drogenbank genommen\\.$");

    /**
     * Pattern for money interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.MoneyEventHandler
     * @see com.rettichlp.UnicacityAddon.events.faction.AFbankEinzahlenEventHandler
     */
    public static final Pattern JOB_SALARY_PATTERN = Pattern.compile("^\\[PayDay] Du bekommst dein Gehalt von (\\d+)\\$ am PayDay ausgezahlt\\.$");
    public static final Pattern JOB_EXPERIENCE_PATTERN = Pattern.compile("^ {2}\\+(\\d+) Exp!(| \\(x(\\d+)\\))$");
    public static final Pattern ATM_INFO_PATTERN = Pattern.compile("^ATM (\\d+): (\\d+)\\$/100000\\$$");
    public static final Pattern BANK_STATEMENT_PATTERN = Pattern.compile("^Ihr Bankguthaben beträgt: [+-](\\d+)\\$$");
    public static final Pattern BANK_STATEMENT_OTHER_PATTERN = Pattern.compile("^Finanzen von (?:\\[UC])*(\\w+): Geld: (\\d+)\\$ \\| Bank: (\\d+)\\$$");
    public static final Pattern BANK_STATS_PATTERN = Pattern.compile("^Neuer Betrag: (\\d+)\\$ \\([+-]\\d+\\$\\)$");
    public static final Pattern BANK_NEW_BALANCE_PATTERN = Pattern.compile("^ {2}Neuer Kontostand: (\\d+)\\$$");
    public static final Pattern BANK_TRANSFER_TO_PATTERN = Pattern.compile("^Du hast (?:\\[UC])*(\\w+) (\\d+)\\$ überwiesen!$");
    public static final Pattern BANK_TRANSFER_GET_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat dir (\\d+)\\$ überwiesen!$");
    public static final Pattern CASH_GIVE_PATTERN = Pattern.compile("^Du hast (?:\\[UC])*(\\w+) (\\d+)\\$ gegeben!$");
    public static final Pattern CASH_TAKE_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat dir (\\d+)\\$ gegeben!$");
    public static final Pattern CASH_TO_FBANK_PATTERN = Pattern.compile("^\\[F-Bank] " + playerName + " hat (\\d+)\\$ (|\\(-(\\d+)\\$\\) )in die Fraktionsbank eingezahlt\\.$");
    public static final Pattern CASH_FROM_FBANK_PATTERN = Pattern.compile("^\\[F-Bank] " + playerName + " hat (\\d+)\\$ aus der Fraktionsbank genommen\\.$");
    public static final Pattern CASH_TO_BANK_PATTERN = Pattern.compile("^ {2}Eingezahlt: \\+(\\d+)\\$$");
    public static final Pattern CASH_FROM_BANK_PATTERN = Pattern.compile("^ {2}Auszahlung: -(\\d+)\\$$");
    public static final Pattern CASH_GET_PATTERN = Pattern.compile("^ {2}\\+(\\d+)\\$$");
    public static final Pattern CASH_REMOVE_PATTERN = Pattern.compile("^ {2}-(\\d+)\\$$");
    public static final Pattern CASH_STATS_PATTERN = Pattern.compile("^ {2}- Geld: (\\d+)\\$$");
    public static final Pattern LOTTO_WIN = Pattern.compile("^\\[Lotto] Du hast im Lotto gewonnen! \\((\\d+)\\$\\)$");
    public static final Pattern FBANK_TAXES = Pattern.compile("^\\[F-Bank] (?:\\[UC])*([a-zA-Z0-9_]+) hat (\\d+)\\$ \\(-(\\d+)\\$\\) in die F-Bank eingezahlt\\.$" +
            "|^\\[F-Bank] (?:\\[UC])*([a-zA-Z0-9_]+) hat (\\d+)\\$ \\(\\+(\\d+)\\$\\) aus der F-Bank genommen\\.$");

    /**
     * Pattern for kill and death interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.DeathsKillsEventHandler
     * @see com.rettichlp.UnicacityAddon.events.KarmaMessageEventHandler
     */
    public static final Pattern DEATH_PATTERN = Pattern.compile("^Du bist nun für (\\d+) Minuten auf dem Friedhof\\.$");
    public static final Pattern KARMA_CHANGED_PATTERN = Pattern.compile("^\\[Karma] ([+-]\\d+) Karma\\.$");
    public static final Pattern KARMA_PATTERN = Pattern.compile("^\\[Karma] Du hast ein Karma von ([+-]\\d+)\\.$");

    /**
     * Pattern for account interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.AccountEventHandler
     * @see com.rettichlp.UnicacityAddon.events.DeathsKillsEventHandler
     */
    public static final Pattern ACCOUNT_WELCOME_BACK_PATTERN = Pattern.compile("^Willkommen zurück!$");
    public static final Pattern RESOURCEPACK_PATTERN = Pattern.compile("^Wir empfehlen dir unser Resourcepack zu nutzen\\.$|" +
            "^Unter https://unicacity\\.de/dl/UnicaCity[_a-zA-Z\\d]+.zip kannst du es dir herunterladen.$");
    public static final Pattern ACCOUNT_PASSWORD_UNPROTECTED_PATTERN = Pattern.compile("^» Schütze deinen Account mit /passwort new \\[Passwort]\\.$");
    public static final Pattern ACCOUNT_PASSWORD_PROTECTED_PATTERN = Pattern.compile("^ {2}Info: Gebe bitte dein Passwort ein\\. /passwort \\[Passwort]$");
    public static final Pattern ACCOUNT_PASSWORD_UNLOCKED_PATTERN = Pattern.compile("^Du hast deinen Account freigeschaltet\\.$");
    public static final Pattern ACCOUNT_AFK_TRUE_PATTERN = Pattern.compile("^Du bist nun im AFK-Modus\\.$");
    public static final Pattern ACCOUNT_AFK_FALSE_PATTERN = Pattern.compile("^Du bist nun nicht mehr im AFK-Modus\\.$");
    public static final Pattern ACCOUNT_PAYDAY_PATTERN = Pattern.compile("^ {2}- Zeit seit PayDay: (\\d+)/60 Minuten$");
    public static final Pattern ACCOUNT_TREUEBONUS_PATTERN = Pattern.compile("^ {2}- Treuebonus: (\\d+) Punkte$");
    public static final Pattern ACCOUNT_FRIEND_JOIN_PATTERN = Pattern.compile(" » Freundesliste: (?:\\[UC])*(?<name>\\w+) ist nun online\\.");
    public static final Pattern ACCOUNT_FRIEND_LEAVE_PATTERN = Pattern.compile(" » Freundesliste: (?:\\[UC])*(?<name>\\w+) ist nun offline\\.");

    /**
     * Pattern for uc and vc interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.HotkeyEventHandler
     * @see com.rettichlp.UnicacityAddon.events.team.ReportEventHandler
     */
    public static final Pattern REPORT_ACCEPTED_PATTERN = Pattern.compile("^\\[Report] Du hast den Report von \\w+ \\[Level \\d+] angenommen! Thema: [a-zA-Z]+$");
    public static final Pattern REPORT_END_PATTERN = Pattern.compile("^\\[Report] Du hast den Report mit \\w+ beendet! \\(#\\d+\\)$");
    public static final Pattern AD_CONTROL_PATTERN = Pattern.compile("^\\[Werbung] (\\w+) hat eine Werbung geschalten: .+$");

    /**
     * Pattern for account interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.house.HouseBankEventHandler
     * @see com.rettichlp.UnicacityAddon.events.house.HouseRenterEventHandler
     */
    public static final Pattern HOUSE_BANK_HEADER_PATTERN = Pattern.compile("^=== Hauskasse Haus (\\d+) ===$");
    public static final Pattern HOUSE_BANK_VALUE_PATTERN = Pattern.compile("^ {2}» (\\d+)\\$$");
    public static final Pattern HOUSE_BANK_DEPOSIT_PATTERN = Pattern.compile("^\\[Haus] Du hast (\\d+)\\$ in die Hauskasse gelegt\\.$");
    public static final Pattern HOUSE_BANK_WITHDRAW_PATTERN = Pattern.compile("^\\[Haus] Du hast (\\d+)\\$ aus der Hauskasse genommen\\.$");
    public static final Pattern HOUSE_RENTER_HEADER_PATTERN = Pattern.compile("^=== Mieter in Haus (\\d+) ===$");
    public static final Pattern HOUSE_RENTER_VALUE_PATTERN = Pattern.compile("^ {2}» (?:\\[UC])*(\\w+) \\((Online|Offline seit (\\d+)\\.(\\d+)\\.(\\d+) (\\d+):(\\d+):(\\d+))\\)$");

    /**
     * Pattern for equip interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.faction.EquipEventHandler
     */
    public static final Pattern TRACKER_PATTERN = Pattern.compile("^Du hast einen Peilsender an (?:\\[UC])*(\\w+) befestigt\\.$");
    public static final Pattern EQUIP_PATTERN = Pattern.compile("^(|\\[Equip] )Du hast (dir|dich mit) (|ein |eine |einen |einem )([a-zA-Z-äöüßÄÖÜ ]+) equip(|p)t[!.]$");
    public static final Pattern EQUIP_INTERRUPTED_PATTERN = Pattern.compile("^\\[Equip] Du bist nicht im Dienst\\.$");

    /**
     * Pattern for navigation interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.NavigationEventHandler
     */
    public static final Pattern ROUTE_PATTERNS = Pattern.compile("^Du hast keine Route\\.$" +
            "|^Du hast deine Route gelöscht\\.$");

    /**
     * Pattern for shop interaction
     *
     * @see com.rettichlp.UnicacityAddon.events.ABuyEventHandler
     */
    public static final Pattern BUY_INTERRUPTED_PATTERN = Pattern.compile("^Verkäufer: (Tut (uns|mir) Leid|Verzeihung), unser Lager ist derzeit leer\\.$" +
            "|^Verkäufer: Dieses Produkt kostet \\d+\\$\\.$" + "|^Verkäufer: Du hast leider nicht genug Geld dabei\\.$");
}