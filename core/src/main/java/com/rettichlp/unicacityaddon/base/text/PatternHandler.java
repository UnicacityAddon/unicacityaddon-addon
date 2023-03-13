package com.rettichlp.unicacityaddon.base.text;


import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.listener.faction.terroristen.BombListener;

import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
public class PatternHandler {

    /**
     * Quote: Rettich: "In welchem Forum ist der Vorschlag drin?" Dimiikou: "In Vorschläge" - RettichLP und Dimiikou, 09.10.2022
     *
     * <br>
     * <br>
     * <br>
     *
     * Pattern for faction and util data
     *
     * @see APIConverter
     * @see com.rettichlp.unicacityaddon.base.utils.ForgeUtils
     */
    public static final Pattern NAME_PATTERN = Pattern.compile("<h4 class=\"h5 g-mb-5\"><strong>(\\w+)");
    public static final Pattern RANK_PATTERN = Pattern.compile("<strong>Rang (\\d)( \\(Leader\\))*</strong>");
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[A-FK-OR0-9]");
    public static final Pattern STRIP_PREFIX_PATTERN = Pattern.compile("\\[[a-zA-Z0-9]+]");

    /**
     * Pattern for bomb timer
     *
     * @see BombListener
     */
    public static final Pattern BOMB_PLACED_PATTERN = Pattern.compile("^News: ACHTUNG! Es wurde eine Bombe in der Nähe von (?<location>.+) gefunden!$");
    public static final Pattern BOMB_REMOVED_PATTERN = Pattern.compile("^News: Die Bombe konnte (nicht|erfolgreich) entschärft werden!$");

    /**
     * Pattern for car interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.CarListener
     */
    public static final Pattern CAR_OPEN_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ aufgeschlossen\\.$");
    public static final Pattern CAR_CLOSE_PATTERN = Pattern.compile("^\\[Car] Du hast deinen .+ abgeschlossen\\.$");
    public static final Pattern CAR_POSITION_PATTERN = Pattern.compile("^\\[Car] Das Fahrzeug befindet sich bei . X: (-?\\d+) \\| Y: (-?\\d+) \\| Z: (-?\\d+)$");
    public static final Pattern CAR_TICKET_PATTERN = Pattern.compile("^\\[Car] Dein Fahrzeug hat ein Strafzettel \\[(.*) \\| (\\d+)\\$]\\.$");
    public static final Pattern CAR_CHECK_KFZ_PATTERN = Pattern.compile("^HQ: Das Fahrzeug mit dem Kennzeichen (?:null|.+) ist auf den Spieler (?:\\[UC])*([a-zA-Z0-9_]+) registriert, over.$" +
            "|^Kennzeichen: (?:null|.+) \\| Type: [a-zA-Z]+ \\| Besitzer: (?:\\[UC])*([a-zA-Z0-9_]+)$");

    /**
     * Pattern for reinforcement and sloc interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.faction.ReinforcementListener
     * @see com.rettichlp.unicacityaddon.listener.faction.ShareLocationListener
     */
    public static final Pattern REINFORCEMENT_PATTERN = Pattern.compile("^(.+ ((?:\\[UC])*\\w+)): Benötige Verstärkung! -> X: (-*\\d+) \\| Y: (-*\\d+) \\| Z: (-*\\d+)$");
    public static final Pattern ON_THE_WAY_PATTERN = Pattern.compile("^(.+ (?:\\[UC])*(\\w+)): ((?:\\[UC])*\\w+), ich bin zu deinem Verstärkungsruf unterwegs! \\((\\d+) Meter entfernt\\)$");
    public static final Pattern SHARE_LOCATION_PATTERN = Pattern.compile("^(.+ (?:\\[UC])*\\w+): Positionsteilung für ([a-zA-Z0-9_, ]+)! -> X: (-*\\d+) \\| Y: (-*\\d+) \\| Z: (-*\\d+)$");

    /**
     * Pattern for service data
     *
     * @see com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceListener
     */
    public static final Pattern SERVICE_ARRIVED_PATTERN = Pattern.compile("^Ein Notruf von ((?:\\[UC])*\\w+) \\((\\d+)\\): \"(.*)\"$");
    public static final Pattern SERVICE_LOCATION_PATTERN = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\.$");
    public static final Pattern SERVICE_LOCATION_ONE_NEAREST_PATTERN = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)\\.$");
    public static final Pattern SERVICE_LOCATION_TWO_NEAREST_PATTERN = Pattern.compile("^Der näheste Punkt ist ([^.]+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\), ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)\\.$");
    public static final Pattern SERVICE_DONE_PATTERN = Pattern.compile("^Du hast den Service von (?:\\[UC])*(\\w+) als 'Erledigt' markiert!$");
    public static final Pattern SERVICE_ACCEPTED_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat den Notruf von ((?:\\[UC])*\\w+) angenommen\\. \\((\\d+)m entfernt\\)$");
    public static final Pattern SERVICE_REQUEUED_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat den Notruf von ((?:\\[UC])*\\w+) \\((\\d+)\\) wieder geöffnet\\.$");
    public static final Pattern SERVICE_DELETED_PATTERN = Pattern.compile("^Der Notruf von ((?:\\[UC])*\\w+) wurde von ((?:\\[UC])*\\w+) gelöscht\\.$");
    public static final Pattern SERVICE_OVERVIEW_PATTERN = Pattern.compile("^\\nOffene Notrufe \\((\\d+)\\):((.|\\n)*)$");
    public static final Pattern SERVICE_NO_SERVICE_PATTERN = Pattern.compile("^Fehler: Es ist kein Service offen\\.$");
    public static final Pattern SERVICE_BLOCKED_PATTERN = Pattern.compile("^Notrufe von ((?:\\[UC])*\\w+) wurden von ((?:\\[UC])*\\w+) blockiert\\.$");
    public static final Pattern SERVICE_UNBLOCKED_PATTERN = Pattern.compile("^Notrufe von ((?:\\[UC])*\\w+) wurden von ((?:\\[UC])*\\w+) wieder zugelassen\\.$");
    public static final Pattern SERVICE_CALL_BOX_PATTERN = Pattern.compile("^(?:HQ:)* {2}➡ (?:\\[UC])*(\\w+) » (.+)$");

    /**
     * Pattern for name tag providing
     *
     * @see com.rettichlp.unicacityaddon.listener.chatlog.ChatLogReceiveChatListener
     * @see com.rettichlp.unicacityaddon.listener.faction.ContractListener
     * @see com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistListener
     * @see com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistModifyListener
     * @see com.rettichlp.unicacityaddon.listener.faction.state.HQMessageListener
     * @see com.rettichlp.unicacityaddon.listener.faction.state.WantedListener
     */
    public static final Pattern WANTED_LIST_PATTERN = Pattern.compile("^ {2}- (?:\\[UC])*(\\w+) \\| (\\d+) WPS \\((.+)\\)(| \\| AFK)$");
    public static final Pattern WANTED_GIVEN_REASON_PATTERN = Pattern.compile("^HQ: Gesuchter: (?:\\[UC])*(\\w+)\\. Grund: (.+)$");
    public static final Pattern WANTED_REASON = Pattern.compile("^HQ: Fahndungsgrund: (.+) \\| Fahndungszeit: (.+)\\.$");
    public static final Pattern WANTED_GIVEN_POINTS_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*(\\w+)'s momentanes WantedLevel: (\\d+)$");
    public static final Pattern WANTED_KILL = Pattern.compile("^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*([a-zA-Z0-9_]+) getötet\\.$");
    public static final Pattern WANTED_DELETE = Pattern.compile("^HQ: .+ (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)'s Akten gelöscht, over\\.$");
    public static final Pattern WANTED_UNARREST_PATTERN = Pattern.compile("^HQ: .+ (?:\\[UC])*(\\w+) hat (?:\\[UC])*(\\w+) aus dem Gefängnis entlassen\\.$");
    public static final Pattern PEILSENDER_PATTERN = Pattern.compile("^HQ: Agent (?:\\[UC])*(\\w+) hat ein Peilsender an (?:\\[UC])*(\\w+) befestigt, over\\.$");
    public static final Pattern WANTED_JAIL = Pattern.compile("^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*([a-zA-Z0-9_]+) eingesperrt\\.$");
    public static final Pattern WANTEDS_TICKET_PATTERN = Pattern.compile("^HQ: .+ (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)*(?: seine| ihre)* Akten gelöscht, over\\.$");
    public static final Pattern WANTED_DELETED_PATTERN = Pattern.compile("^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ eingesperrt\\.$" +
            "|^HQ: (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ getötet\\.$" +
            "|^HQ: .+ (?:\\[UC])*\\w+ hat (?:\\[UC])*(\\w+)(?:'s)*(?: seine| ihre)* Akten gelöscht, over\\.$");
    public static final Pattern TAKE_DRIVING_LICENSE_PATTERN = Pattern.compile("^(Agent|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)* Führerschein abgenommen\\.$");
    public static final Pattern GIVE_DRIVING_LICENSE_PATTERN = Pattern.compile("^(Agent|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)* Führerschein zurückgegeben\\.$");
    public static final Pattern TAKE_GUN_LICENSE_PATTERN = Pattern.compile("^(Agent|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)* Waffenschein abgenommen\\.$");
    public static final Pattern GIVE_GUN_LICENSE_PATTERN = Pattern.compile("^(Agent|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+)(?:'s)* Waffenschein zurückgegeben\\.$");
    public static final Pattern TAKE_GUNS_PATTERN = Pattern.compile("^(Beamtin|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+) die Waffen abgenommen\\.$");
    public static final Pattern TAKE_DRUGS_PATTERN = Pattern.compile("^(Beamtin|Beamter) (?:\\[UC])*(\\w+) hat (?:\\[UC])*([a-zA-Z0-9_]+) (seine|ihre) Drogen abgenommen!$");
    public static final Pattern BLACKLIST_START_PATTERN = Pattern.compile("=== Blacklist .+ ===");
    public static final Pattern BLACKLIST_LIST_PATTERN = Pattern.compile("^ » (?:\\[UC])*(\\w+) \\| (.+) \\| (.+) \\| (\\d+) Kills \\| (\\d+)\\$(| \\(AFK\\))$");
    public static final Pattern BLACKLIST_ADDED_PATTERN = Pattern.compile("^\\[Blacklist] (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ auf die Blacklist gesetzt!$");
    public static final Pattern BLACKLIST_REMOVED_PATTERN = Pattern.compile("^\\[Blacklist] (?:\\[UC])*(\\w+) wurde von (?:\\[UC])*\\w+ von der Blacklist gelöscht!$");
    public static final Pattern CONTRACT_SET_PATTERN = Pattern.compile("^\\[Contract] Es wurde ein Kopfgeld auf (?:\\[UC])*(\\w+) \\(\\d+\\$\\) ausgesetzt\\.$");
    public static final Pattern CONTRACT_REMOVED_PATTERN = Pattern.compile("^\\[Contract] (?:\\[UC])*\\w+ hat (?:\\[UC])*(\\w+) von der Contract Liste gelöscht\\. \\[-\\d+]$" +
            "|^\\[Contract] (?:\\[UC])*(\\w+) hat (?:\\[UC])*(\\w+) getötet\\. Kopfgeld: \\d+\\$");
    public static final Pattern CONTRACT_LIST_PATTERN = Pattern.compile("^ - (?:\\[UC])*(?<name>\\w+) \\[(?<money>\\d+)\\$](| \\(AFK\\))$");

    /**
     * Pattern for job interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.job.FishermanListener
     * @see com.rettichlp.unicacityaddon.listener.job.JobListener
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
     * @see com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FirstAidListener
     * @see com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener
     * @see com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveListener
     */
    public static final Pattern RECIPE_ACCEPT_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) möchte dir ein Rezept für 200\\$ verkaufen\\.$");
    public static final Pattern RECIPE_GIVE_PATTERN = Pattern.compile("^Du hast ((?:\\[UC])*\\w+) ein Rezept für (Antibiotika|Hustensaft|Schmerzmittel) ausgestellt\\.$");
    public static final Pattern REVIVE_BY_MEDIC_START_PATTERN = Pattern.compile("^Du wirst von (?:\\[UC])*(\\w+) wiederbelebt\\.$");
    public static final Pattern REVIVE_START_PATTERN = Pattern.compile("^Du beginnst mit der Wiederbelebung von ((?:\\[UC])*\\w+)\\.");
    public static final Pattern REVIVE_FAILURE_PATTERN = Pattern.compile("^Verdammt\\.\\. mein Kopf dröhnt so\\.\\.\\.$");
    public static final Pattern FIRST_AID_RECEIVE_PATTERN = Pattern.compile("^\\[Erste-Hilfe] Notarzt (?:\\[UC])*(\\w+) hat dir einen Erste-Hilfe-Schein für 14 Tage ausgestellt\\.$");
    public static final Pattern FIRST_AID_LICENCE_PATTERN = Pattern.compile("^ {2}- Erste-Hilfe-Schein: Vorhanden$");
    public static final Pattern FIRST_AID_ISSUE_PATTERN = Pattern.compile("^\\[Erste-Hilfe] Du hast Erste-Hilfe bei (?:\\[UC])*(\\w+) geleistet\\.$");
    public static final Pattern FIRST_AID_USE_PATTERN = Pattern.compile("^ {2}Info: Du bist nun eine Minute länger auf dem Friedhof\\.$");
    public static final Pattern MEDICATION_GET_PATTERN = Pattern.compile("^\\[Apotheke] Du hast (?<amount>\\d+) (?<drugType>.+) erhalten, gute Besserung!$");

    /**
     * Pattern for communication interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.MobileListener
     */
    public static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^Nummer von (?:\\[UC])*\\w+: (\\d+)$");
    public static final Pattern MOBILE_CALL_PATTERN = Pattern.compile("^Dein Handy klingelt! Ein Anruf von (?:\\[UC])*(\\w+)$");
    public static final Pattern MOBILE_SMS_PATTERN = Pattern.compile("^Dein Handy klingelt! Eine Nachricht von (?:\\[UC])*(\\w+) \\((\\d+)\\)\\.$");
    public static final Pattern MOBILE_REMOVE_PATTERN = Pattern.compile("^((?:\\[UC])*\\w+) hat dir deine Kommunikationsgeräte abgenommen\\.$");
    public static final Pattern MOBILE_GET_PATTERN = Pattern.compile("^Du hast dein Handy genommen\\.$" +
            "|^((?:\\[UC])*\\w+) hat dir deine Kommunikationsgeräte wiedergegeben\\.$");
    public static final Pattern MOBILE_TOGGLE_PATTERN = Pattern.compile("^Du hast dein Telefon (ein|aus)geschaltet\\.$");

    /**
     * Pattern for bad faction interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.faction.badfaction.BannerListener
     * @see com.rettichlp.unicacityaddon.listener.faction.badfaction.GiftEigenbedarfListener
     * @see com.rettichlp.unicacityaddon.listener.faction.badfaction.PlantListener
     */
    public static final Pattern PLANT_HARVEST_PATTERN = Pattern.compile("^\\[Plantage] Eine .+-Plantage wurde von (?:\\[UC])*(\\w+) geerntet\\. \\[\\d+g]$");
    public static final Pattern PLANT_USE_PATTERN = Pattern.compile("^\\[Plantage] Eine .+-Plantage wurde von (?:\\[UC])*(\\w+) (gewässert|gedüngt)\\.$");
    public static final Pattern DBANK_GIVE_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat (?<amount>\\d+)g (?<drugType>.+) \\((?<drugPurity>Höchste|Gute|Mittlere|Schlechte) Reinheit\\) \\((\\d+)g\\) eingelagert\\.$");
    public static final Pattern DBANK_GET_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat (?<amount>\\d+)g (?<drugType>.+) \\((?<drugPurity>Höchste|Gute|Mittlere|Schlechte) Reinheit\\) \\((\\d+)g\\) aus der Drogenbank genommen\\.$");
    public static final Pattern DRUG_DEAL_ACCEPTED = Pattern.compile("^\\[Deal] (?:\\[UC])*(\\w+) hat den Deal angenommen\\.$");
    public static final Pattern DRUG_DEAL_ENDED = Pattern.compile("^\\[Deal] (?:\\[UC])*(\\w+) hat den Deal angenommen\\.$" +
            "|^\\[Deal] (?:\\[UC])*(\\w+) hat das Angebot abgelehnt\\.$");
    public static final Pattern DRUG_GIVE_PATTERN = Pattern.compile("^\\[Deal] Du hast (?:\\[UC])*(\\w+) (?<amount>\\d+)g (?<drugType>.+) \\(Reinheit (?<drugPurity>\\d)\\) für (\\d+)\\$ angeboten\\.$");
    public static final Pattern DRUG_GET_PATTERN = Pattern.compile("^\\[Deal] (?:\\[UC])*(\\w+) bietet dir (?<amount>\\d+)g (?<drugType>.+) \\(Reinheit (?<drugPurity>\\d)\\) für (\\d+)\\$ an\\.$");
    public static final Pattern DRUG_USE_PATTERN = Pattern.compile("^(?:\\[UC])*(\\w+) hat (?<drugType>.+) genommen\\.$");
    public static final Pattern DRUG_USE_COMMAND_PATTERN = Pattern.compile("^/use (?<drugType>.+) (?<drugPurity>\\d)$");
    public static final Pattern TRUNK_GET_COMMAND_PATTERN = Pattern.compile("^/trunk get (?<drugType>.+) (?<drugPurity>\\d) (?<amount>\\d+)$");
    public static final Pattern TRUNK_GIVE_COMMAND_PATTERN = Pattern.compile("^/trunk drop (?<drugType>.+) (?<drugPurity>\\d) (?<amount>\\d+)$");
    public static final Pattern TRUNK_INTERACTION_ACCEPTED_PATTERN = Pattern.compile("^\\[Car] Du hast (?<amount>\\d+)g (?<drugType>.+) (aus dem|in den) Kofferraum (genommen|verstaut)\\.$");
    public static final Pattern BANNER_SPRAYED_PATTERN = Pattern.compile("^\\[Graffiti] Du hast das Graffiti mit deiner Fraktionsflagge übersprayt\\.$");

    /**
     * Pattern for money interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.MoneyListener
     * @see com.rettichlp.unicacityaddon.listener.faction.AFbankEinzahlenListener
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
    public static final Pattern CASH_TO_FBANK_PATTERN = Pattern.compile("^\\[F-Bank] \\w+ hat (\\d+)\\$ (|\\(-(\\d+)\\$\\) )in die Fraktionsbank eingezahlt\\.$");
    public static final Pattern CASH_FROM_FBANK_PATTERN = Pattern.compile("^\\[F-Bank] \\w+ hat (\\d+)\\$ aus der Fraktionsbank genommen\\.$");
    public static final Pattern CASH_TO_BANK_PATTERN = Pattern.compile("^ {2}Eingezahlt: \\+(\\d+)\\$$");
    public static final Pattern CASH_FROM_BANK_PATTERN = Pattern.compile("^ {2}Auszahlung: -(\\d+)\\$$");
    public static final Pattern CASH_GET_PATTERN = Pattern.compile("^ {2}\\+(\\d+)\\$$");
    public static final Pattern CASH_REMOVE_PATTERN = Pattern.compile("^ {2}-(\\d+)\\$$");
    public static final Pattern CASH_STATS_PATTERN = Pattern.compile("^ {2}- Geld: (\\d+)\\$$");
    public static final Pattern LOTTO_WIN = Pattern.compile("^\\[Lotto] Du hast im Lotto gewonnen! \\((\\d+)\\$\\)$");
    public static final Pattern FBANK_TAXES = Pattern.compile("^\\[F-Bank] (?:\\[UC])*([a-zA-Z0-9_]+) hat (\\d+)\\$ \\(-(\\d+)\\$\\) in die F-Bank eingezahlt\\.$" +
            "|^\\[F-Bank] (?:\\[UC])*([a-zA-Z0-9_]+) hat (\\d+)\\$ \\(\\+(\\d+)\\$\\) aus der F-Bank genommen\\.$");

    /**
     * Pattern karma interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.KarmaMessageListener
     */
    public static final Pattern KARMA_CHANGED_PATTERN = Pattern.compile("^\\[Karma] ([+-]\\d+) Karma\\.$");
    public static final Pattern KARMA_PATTERN = Pattern.compile("^\\[Karma] Du hast ein Karma von ([+-]\\d+)\\.$");

    /**
     * Pattern for account interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.AccountListener
     */
    public static final Pattern ACCOUNT_WELCOME_BACK_PATTERN = Pattern.compile("^Willkommen zurück!$");
    public static final Pattern RESOURCEPACK_PATTERN = Pattern.compile("^Wir empfehlen dir unser Resourcepack zu nutzen\\.$|" +
            "^Unter https://unicacity\\.de/dl/UnicaCity[_a-zA-Z\\d]+.zip kannst du es dir herunterladen.$");
    public static final Pattern ACCOUNT_PASSWORD_UNPROTECTED_PATTERN = Pattern.compile("^» Schütze deinen Account mit /passwort new \\[Passwort]\\.$");
    public static final Pattern ACCOUNT_PASSWORD_PROTECTED_PATTERN = Pattern.compile("^ {2}Info: Gebe bitte dein Passwort ein\\. /passwort \\[Passwort]$");
    public static final Pattern ACCOUNT_PASSWORD_UNLOCKED_PATTERN = Pattern.compile("^Du hast deinen Account freigeschaltet\\.$");
    public static final Pattern ACCOUNT_AFK_TRUE_PATTERN = Pattern.compile("^Du bist nun im AFK-Modus\\.$");
    public static final Pattern ACCOUNT_AFK_FALSE_PATTERN = Pattern.compile("^Du bist nun nicht mehr im AFK-Modus\\.$");
    public static final Pattern ACCOUNT_AFK_FAILURE_PATTERN = Pattern.compile("^Du kannst noch nicht in den AFK Modus wechseln\\.$");
    public static final Pattern ACCOUNT_PAYDAY_PATTERN = Pattern.compile("^ {2}- Zeit seit PayDay: (\\d+)/60 Minuten$");
    public static final Pattern ACCOUNT_TREUEBONUS_PATTERN = Pattern.compile("^ {2}- Treuebonus: (\\d+) Punkte$");
    public static final Pattern ACCOUNT_FRIEND_JOIN_PATTERN = Pattern.compile(" » Freundesliste: (?:\\[UC])*(?<name>\\w+) ist nun online\\.");
    public static final Pattern ACCOUNT_FRIEND_LEAVE_PATTERN = Pattern.compile(" » Freundesliste: (?:\\[UC])*(?<name>\\w+) ist nun offline\\.");
    public static final Pattern ACCOUNT_MASK_ON_PATTERN = Pattern.compile("^\\[Masken] Du bist nun für 20 Minuten maskiert\\. \\[#\\d+]$");
    public static final Pattern ACCOUNT_MASK_OFF_PATTERN = Pattern.compile("^\\[Masken] Du hast deine Maske abgenommen\\.$");

    /**
     * Pattern for timer interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.TimerListener
     */
    public static final Pattern TIMER_FBI_HACK_START_PATTERN = Pattern.compile("^\\[Polizeicomputer] Du hast einen Hackversuch gestartet\\. Geschätzte Dauer: (\\d+) Sekunden\\.$");
    public static final Pattern TIMER_GRAVEYARD_START_PATTERN = Pattern.compile("^Du bist nun für (20|8|5) Minuten auf dem Friedhof\\.$");
    public static final Pattern TIMER_JAIL_START_PATTERN = Pattern.compile("^\\[Gefängnis] Du bist nun für (\\d+) Minuten im Gefängnis\\.$");
    public static final Pattern TIMER_JAIL_FINISH_PATTERN = Pattern.compile("^\\[Gefängnis] Du bist wieder frei!$");
    public static final Pattern TIMER_JAIL_MODIFY_PATTERN = Pattern.compile("^\\[PI] Gute Arbeit\\. Du darfst nun (\\d)min früher gehen\\.$");

    /**
     * Pattern for uc and vc interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.HotkeyListener
     * @see com.rettichlp.unicacityaddon.listener.team.ReportListener
     */
    public static final Pattern REPORT_PATTERN = Pattern.compile("^Es liegt ein neuer Report \\[\\d+] von (?:\\[UC])*(\\w+) vor! Thema: [a-zA-Z]+$|" +
            "^Es liegt ein neuer Report von (?:\\[UC])*(\\w+) vor! Thema: [a-zA-Z]+$");
    public static final Pattern REPORT_ACCEPTED_PATTERN = Pattern.compile("^\\[Report] Du hast den Report von \\w+ \\[Level \\d+] angenommen! Thema: [a-zA-Z]+$");
    public static final Pattern REPORT_END_PATTERN = Pattern.compile("^\\[Report] Du hast den Report mit \\w+ beendet! \\(#\\d+\\)$");
    public static final Pattern AD_CONTROL_PATTERN = Pattern.compile("^\\[Werbung] (\\w+) hat eine Werbung geschalten: .+$");

    /**
     * Pattern for house interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.house.HouseDataListener
     * @see com.rettichlp.unicacityaddon.listener.house.HouseInteractionListener
     * @see com.rettichlp.unicacityaddon.listener.house.HouseRenterListener
     */
    public static final Pattern HOUSE_BANK_HEADER_PATTERN = Pattern.compile("^=== Hauskasse Haus (\\d+) ===$");
    public static final Pattern HOUSE_BANK_VALUE_PATTERN = Pattern.compile("^ {2}» (\\d+)\\$$");
    public static final Pattern HOUSE_BANK_DEPOSIT_PATTERN = Pattern.compile("^\\[Haus] Du hast (\\d+)\\$ in die Hauskasse gelegt\\.$");
    public static final Pattern HOUSE_BANK_WITHDRAW_PATTERN = Pattern.compile("^\\[Haus] Du hast (\\d+)\\$ aus der Hauskasse genommen\\.$");
    public static final Pattern HOUSE_RENTER_HEADER_PATTERN = Pattern.compile("^=== Mieter in Haus (\\d+) ===$");
    public static final Pattern HOUSE_RENTER_VALUE_PATTERN = Pattern.compile("^ {2}» (?:\\[UC])*(\\w+) \\((Online|Offline seit (\\d+)\\.(\\d+)\\.(\\d+) (\\d+):(\\d+):(\\d+))\\)$");
    public static final Pattern HOUSE_STORAGE_SUCCESSFUL_INTERACTION = Pattern.compile("^\\[Haus] Du hast (\\d+)g (\\w+) (ins|aus dem) Drogenlager (gelegt|genommen)\\.$");
    public static final Pattern HOUSE_STORAGE_ADD_COMMAND_PATTERN = Pattern.compile("^/drogenlager drop (?<drugType>.+) (?<amount>\\d+) (?<drugPurity>\\d)$");
    public static final Pattern HOUSE_STORAGE_REMOVE_COMMAND_PATTERN = Pattern.compile("^/drogenlager get (?<drugType>.+) (?<amount>\\d+) (?<drugPurity>\\d)$");
    public static final Pattern HOUSE_AKKU_PATTERN = Pattern.compile("^Du hast begonnen deinen Akku aufzuladen\\.\\.\\.$");
    public static final Pattern HOUSE_HEAL_PATTERN = Pattern.compile("^Du hast begonnen dich zu heilen\\.\\.\\.$");
    public static final Pattern HOUSE_AMMUNITION_PATTERN = Pattern.compile("^\\[Waffenschrank] Du hast deine (?<weapon>.+) mit (?<amount>\\d+) Kugeln beladen\\.$");

    /**
     * Pattern for equip interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.faction.EquipListener
     */
    public static final Pattern TRACKER_PATTERN = Pattern.compile("^Du hast einen Peilsender an (?:\\[UC])*(\\w+) befestigt\\.$");
    public static final Pattern EQUIP_PATTERN = Pattern.compile("^(?:|\\[Equip] )Du hast (?:dir|dich mit) (?:|ein|eine|einen|einem) (.+) equip(?:|p)t[!.]$");
    public static final Pattern EQUIP_INTERRUPTED_PATTERN = Pattern.compile("^\\[Equip] Du bist nicht im Dienst\\.$");

    /**
     * Pattern for navigation interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.NavigationListener
     */
    public static final Pattern ROUTE_PATTERNS = Pattern.compile("^Du hast keine Route\\.$" +
            "|^Du hast deine Route gelöscht\\.$");

    /**
     * Pattern for shop interaction
     *
     * @see com.rettichlp.unicacityaddon.listener.ABuyListener
     */
    public static final Pattern BUY_INTERRUPTED_PATTERN = Pattern.compile("^Verkäufer: (Tut (uns|mir) Leid|Verzeihung), unser Lager ist derzeit leer\\.$" +
            "|^Verkäufer: Dieses Produkt kostet \\d+\\$\\.$" + "|^Verkäufer: Du hast leider nicht genug Geld dabei\\.$");

    /**
     * Pattern for church interaction
     *
     */
    public static final Pattern PRAYING_START_PATTERN = Pattern.compile("^\\[Kirche] Du hast begonnen für (?:\\[UC])*(?<name>\\w+) zu beten\\.$");

    /**
     * Pattern for state messages
     *
     */
    public static final Pattern DRUG_VAULT_DROP_PATTERN = Pattern.compile("^HQ: (.+) (?:\\[UC])*(\\w+) hat (\\d+)g (Kokain|Methamphetamin|Marihuana|LSD) \\((Höchste Reinheit|Gute Reinheit|Mittlere Reinheit|Schlechte Reinheit)\\) in der Asservatenkammer verstaut\\.$");
    public static final Pattern DRUG_VAULT_GET_PATTERN = Pattern.compile("^HQ: (.+) (?:\\[UC])*(\\w+) hat (\\d+)g (Kokain|Methamphetamin|Marihuana|LSD) \\((Höchste Reinheit|Gute Reinheit|Mittlere Reinheit|Schlechte Reinheit)\\) aus der Asservatenkammer genommen\\.$");
    public static final Pattern DRUG_VAULT_INFOTITLE_PATTERN = Pattern.compile("^ ===== Asservatenkammer \\((\\w+)\\) =====$");
    public static final Pattern DRUG_VAULT_INFO_PATTERN = Pattern.compile("^ {2}» (Mittlere|Höchste|Gute|Schlechte) Reinheit: (\\d+)g {2}» (Mittlere|Höchste|Gute|Schlechte) Reinheit: (\\d+)g {2}» (Mittlere|Höchste|Gute|Schlechte) Reinheit: (\\d+)g {2}» (Mittlere|Höchste|Gute|Schlechte) Reinheit: (\\d+)g$");
    public static final Pattern DRUG_VAULT_INFOLSD_PATTERN = Pattern.compile("^ {2}» LSD: (\\d+) Stück$");
    public static final Pattern DRUG_VAULT_BURN_PATTERN = Pattern.compile("^HQ: (.+) (?:\\[UC])*(\\w+) hat (\\d+)g (Kokain|Methamphetamin|Marihuana|LSD) \\((Höchste Reinheit|Gute Reinheit|Mittlere Reinheit|Schlechte Reinheit)\\) vernichtet\\.$");
    public static final Pattern PLANT_BURN_PATTERN = Pattern.compile("^HQ: (.+) (?:\\[UC])*(\\w+) hat erfolgreich eine (Kokain|Marihuana) Plantage verbrannt, over\\.$");
}