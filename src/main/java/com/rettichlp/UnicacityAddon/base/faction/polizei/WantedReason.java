package com.rettichlp.UnicacityAddon.base.faction.polizei;

public enum WantedReason {
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