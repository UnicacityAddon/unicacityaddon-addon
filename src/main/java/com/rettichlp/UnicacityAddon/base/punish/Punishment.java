package com.rettichlp.UnicacityAddon.base.punish;

/**
 * @author Dimiikou
 */
public enum Punishment {
    AFK_FARMING("AFK-Farming", "AFK-Farming", 300, 180, 0, true, false, 0, 0),
    ACCOUNT_SHARING("Account-Sharing", "Account Sharing", 0, -1, 0, false, false, 0, 0),
    ABUSE_OF_UWU_SYSTEM_FIRST_VIOLATION("Ausnutzung-des-UserWerbenUser-Systems-(erstes-Vergehen)", "Ausnutzung des UserWerbenUser-Systems", 0, 360, 0, false, false, 0, 0),
    ABUSE_OF_UWU_SYSTEM_SECOND_VIOLATION("Ausnutzung-des-UserWerbenUser-Systems-(zweites-Vergehen)", "Ausnutzung des UserWerbenUser-Systems", 0, 600, 0, false, false, 0, 0),
    INSULATION("Beleidigung", "Beleidigung", 0, 180, 0, false, false, 0, 0),
    HEAVY_BUGUSE_WITH_OWN_INCOME("Schwerwiegender-Buguse-mit-Eigenertrag", "Schwerwiegender Buguse mit Eigenertrag", 0, 20160, 2, false, false, 0, 0),
    BUGUSE_WITH_OWN_INCOME("Buguse-mit-Eigenertrag", "Buguse mit Eigenertrag", 0, 10080, 1, false, false, 0, 0),
    BUGUSE_200_CHECKPOINTS("Buguse-(200-Checkpoints)", "Buguse", 200, 0, 0, false, false, 0, 0),
    BUGUSE_300_CHECKPOINTS("Buguse-(300-Checkpoints)", "Buguse", 300, 0, 0, false, false, 0, 0),
    BUGUSE_400_CHECKPOINTS("Buguse-(400-Checkpoints)", "Buguse", 400, 0, 0, false, false, 0, 0),
    BUGUSE_500_CHECKPOINTS("Buguse-(500-Checkpoints)", "Buguse", 500, 0, 0, false, false, 0, 0),
    CHEATEN_UNDER_LEVEL5("Cheaten-(unter-Level-5)", "Cheaten", 0, -1, 0, false, false, 0, 0),
    CHEATEN_OVER_LEVEL_5("Cheaten-(über-Level-5)", "Cheaten", 0, 10080, 1, false, false, 0, 0),
    EXCESSIVE_USE_OF_CONTRACT("Exzessives-Setzen", "Exzessives Setzen", 0, 2880, 0, false, false, 0, 0),
    THIRD_PARTY_ADVERTISING_UNDER_LEVEL5("Fremdwerbung-(unter-Level-5)", "Fremdwerbung", 0, 43200, 1, false, false, 0, 0),
    THIRD_PARTY_ADVERTISING_OVER_LEVEL5("Fremdwerbung-(über-Level-5)", "Fremdwerbung", 0, 10080, 1, false, false, 0, 0),
    MONEY_STORE_SECONDARY_ACCOUNT("Geldspeicher-(Zweitaccount)", "Geldspeicher", 0, -1, 0, false, false, 0, 0),
    MONEY_STORE_MAIN_ACCOUNT("Geldspeicher-(Mainaccount)", "Geldspeicher", 0, 0, 1, false, false, 0, 0),
    TRADE_WITH_NONGAME_ITEMS("Handel-mit-spielexternen-Dingen", "Handel mit spielexternen Dingen", 0, 20160, 1, false, false, 0, 0),
    DISREGARD_OF_MASK_RULE("Missachten-der-Masken-Regel", "Missachten der Masken-Regel", 200, 0, 0, false, false, 0, 0),
    METAGAMING_FIRST_VIOLATION("Metagaming-(erstes-Vergehen)", "Metagaming", 300, 120, 0, false, false, 0, 0),
    METAGAMING_SECOND_VIOLATION("Metagaming-(zweites-Vergehen)", "Metagaming (zweites Vergehen)", 400, 360, 0, false, false, 0, 0),
    BULLYING("Mobbing", "Mobbing", 0, -1, 0, false, false, 0, 0),
    KNOWLEDGE_OF_COMMUNITY_BANNED_PLAYER_ACCOUNT("Nichtmelden-von-Zweitaccounts-von-Spielern-mit-Community-Ausschluss", "Nichtmelden von Zweitaccounts von Spielern mit Community Ausschluss", 0, -1, 0, false, false, 0, 0),
    RACISM("Rassismus", "Rassismus", 0, -1, 0, false, false, 0, 0),
    EXTREMISM("Extremismus", "Extremismus", 0, -1, 0, false, false, 0, 0),
    REALLIFE_THREAT("Reallife-Drohung", "Reallife Drohung", 0, -1, 0, false, false, 0, 0),
    CALUMNY("Rufmord", "Rufmord", 0, 2880, 0, false, false, 0, 0),
    CALUMNY_WARN("Rufmord-(Warn)", "Rufmord", 0, 2880, 1, false, false, 0, 0),
    SDM_FIRST_VIOLATION("Sinnloses-Deathmatch-(erstes-Vergehen)", "Sinnloses Deathmatch", 300, 0, 0, false, false, 7, 0),
    SDM_SECOND_VIOLATION("Sinnloses-Deathmatch-(zweites-Vergehen)", "Sinnloses Deathmatch (zweites Vergehen)", 300, 0, 0, false, false, 14, 0),
    MULTIPLE_SDM("Multiples-Sinnloses-Deathmatch", "Multiples Sinnloses Deathmatch", 0, 20160, 1, false, false, 14, 0),
    SPAWNTRAPPING("Spawntrapping", "Spawntrapping", 250, 60, 0, false, false, 0, 0),
    SUPPORT_REFUSAL_200_CHECKPOINTS("Supporter-Verweigerung-(200-Checkpoints)", "Supporter Verweigerung", 200, 0, 0, false, false, 0, 0),
    SUPPORT_REFUSAL_300_CHECKPOINTS("Supporter-Verweigerung-(300-Checkpoints)", "Supporter Verweigerung", 300, 0, 0, false, false, 0, 0),
    SUPPORT_REFUSAL_400_CHECKPOINTS("Supporter-Verweigerung-(400-Checkpoints)", "Supporter Verweigerung", 400, 0, 0, false, false, 0, 0),
    SUPPORT_REFUSAL_500_CHECKPOINTS("Supporter-Verweigerung-(500-Checkpoints)", "Supporter Verweigerung", 500, 0, 0, false, false, 0, 0),
    SUPPORT_REFUSAL_500_CHECKPOINTS_WARN("Supporter-Verweigerung-(500-Checkpoints-Warn)", "Supporter Verweigerung", 500, 0, 1, false, false, 0, 0),
    SUPPORT_LIE_200_CHECKPOINTS("Supporter-Belügen-(200-Checkpoints)", "Supporter Belügen", 200, 0, 0, false, false, 0, 0),
    SUPPORT_LIE_300_CHECKPOINTS("Supporter-Belügen-(300-Checkpoints)", "Supporter Belügen", 300, 0, 0, false, false, 0, 0),
    SUPPORT_LIE_400_CHECKPOINTS("Supporter-Belügen-(400-Checkpoints)", "Supporter Belügen", 400, 0, 0, false, false, 0, 0),
    SUPPORT_LIE_500_CHECKPOINTS("Supporter-Belügen-(500-Checkpoints)", "Supporter Belügen", 500, 0, 0, false, false, 0, 0),
    SUPPORT_LIE_500_CHECKPOINTS_WARN("Supporter-Belügen-(500-Checkpoints-Warn)", "Supporter Belügen", 500, 0, 1, false, false, 0, 0),
    SUPPORT_INSULATION_3_HOURS("Supporterbeleidigung-(3h-Timeban)", "Supporterbeleidigung", 0, 180, 0, false, false, 0, 0),
    SUPPORT_INSULATION_4_HOURS("Supporterbeleidigung-(4h-Timeban)", "Supporterbeleidigung", 0, 240, 0, false, false, 0, 0),
    SUPPORT_INSULATION_5_HOURS("Supporterbeleidigung-(5h-Timeban)", "Supporterbeleidigung", 0, 300, 0, false, false, 0, 0),
    FORBIDDEN_HOTKEY_200_CHECKPOINTS("Unerlaubte-Hotkeys-(200-Checkpoints)", "Unerlaubte Hotkeys", 200, 0, 0, false, false, 0, 0),
    FORBIDDEN_HOTKEY_300_CHECKPOINTS("Unerlaubte-Hotkeys-(300-Checkpoints)", "Unerlaubte Hotkeys", 300, 0, 0, false, false, 0, 0),
    FORBIDDEN_HOTKEY_400_CHECKPOINTS("Unerlaubte-Hotkeys-(400-Checkpoints)", "Unerlaubte Hotkeys", 400, 0, 0, false, false, 0, 0),
    BREACH_OF_CONTRACT("Vertragsbruch", "Vertragsbruch", 0, 720, 1, false, false, 0, 0),
    BAD_GOV_FIRST_VIOLATION("Bad-/gov-(erstes-Vergehen)", "Bad /gov", 300, 0, 0, false, false, 0, 0),
    BAD_GOV_SECOND_VIOLATION("Bad-/gov-(zweites-Vergehen)", "Bad /gov (zweites Vergehen)", 300, 120, 0, false, false, 0, 0),
    BAD_NEWS_FIRST_VIOLATION("Bad-/news-(erstes-Vergehen)", "Bad /news", 300, 0, 0, false, false, 0, 0),
    BAD_NEWS_SECOND_VIOLATION("Bad-/news-(zweites-Vergehen)", "Bad /news (zweites Vergehen)", 300, 120, 0, false, false, 0, 0),
    BAD_MEGAPHONE_FIRST_VIOLATION("Bad-/megaphon-(erstes-Vergehen)", "Bad /megaphon", 300, 0, 0, false, false, 0, 0),
    BAD_MEGAPHONE_SECOND_VIOLATION("Bad-/megaphon-(zweites-Vergehen)", "Bad /megaphon (zweites Vergehen)", 300, 120, 0, false, false, 0, 0),
    BLACKLIST_RULE_VIOLATION("Missachten-der-Blacklist-Regel", "Missachten der Blacklist Regel", 300, 0, 0, false, false, 0, 0),
    BOMB_RULE_VIOLATION("Missachten-der-Bomben-Regel", "Missachten der Bomben Regel", 300, 0, 0, false, false, 0, 0),
    FACTION_DECEPTION("Fraktionsbetrug", "Fraktionsbetrug", 0, 0, 1, false, false, 0, 0),
    FACTION_ESCAPE("Fraktionsflucht", "Fraktionsflucht", 250, 120, 0, false, false, 0, 0),
    VIOLATION_OF_HOSTAGE_RULE("Missachten-der-Geiselnahme-Regel", "Missachten der Geiselnahme-Regel", 250, 120, 0, false, false, 0, 0),
    VIOLATION_OF_PLANT_RULE("Missachten-der-Plantagen-Regel", "Missachten der Plantagen-Regel", 300, 0, 0, false, false, 0, 0),
    VIOLATION_OF_ROBBERY_RULE("Missachten-der-Überfall-Regel", "Missachten der Überfall-Regel", 250, 120, 0, false, false, 0, 0),
    BAD_ROLEPLAY_FIRST_VIOLATION("Bad-Roleplay-(erstes-Vergehen)", "Bad Roleplay", 300, 120, 0, false, false, 0, 0),
    BAD_ROLEPLAY_SECOND_VIOLATION("Bad-Roleplay-(zweites-Vergehen)", "Bad Roleplay (zweites Vergehen)", 400, 360, 0, false, false, 0, 0),
    VIOLATION_OF_BOND_RULE("Missachten-der-Fesseln-Regel", "Missachten der Fesseln-Regel", 200, 120, 0, false, false, 0, 0),
    VIOLATION_OF_HUMAND_TRADE_RULE("Missachten-der-Menschenhandel-Regel", "Missachten der Menschenhandel-Regel", 250, 120, 0, false, false, 0, 0),
    VIOLATION_OF_NEW_LIFE_RULE("Missachten-der-New-Life-Regel", "Missachten der New-Life-Regel", 250, 60, 0, false, false, 0, 0),
    OFFLINE_ESCAPE("Offlineflucht", "Offlineflucht", 250, 180, 0, false, false, 0, 0),
    AFK_ESCAPE("AFK-Flucht", "AFK-Flucht", 250, 180, 0, false, false, 0, 0),
    TP_ESCAPE("TP-Flucht", "TP-Flucht", 250, 180, 0, false, false, 0, 0),
    POWERGAMING("Powergaming", "Powergaming", 250, 120, 0, false, false, 0, 0),
    VIOLATION_OF_RAPE_RULE("Missachten-der-Vergewaltigungs-Regel", "Missachten der Vergewaltigungs-Regel", 300, 1440, 0, false, false, 0, 0),
    SPAM("Spam", "Spam", 0, 0, 0, false, true, 0, 0),
    FACTION_ALLIANCE("Fraktionsbündnis", "Fraktionsbündnis", 0, 0, 0, false, false, 0, 7),
    INVITE_WITHOUT_APPLICATION("Annahme-ohne-Bewerbung", "Invite in eine Fraktion ohne Bewerbung", 0, 0, 0, false, false, 0, 7),
    VIOLATION_OF_ROLEPLAY_CHAT("Missachten-der-Roleplay-Chat-Regel-(Kick)", "Missachten der Roleplay Chat-Regel", 0, 0, 0, false, true, 0, 0),
    VIOLATION_OF_ROLEPLAY_CHAT_CHECKPOINTS("Missachten-der-Roleplay-Chat-Regel-(Checkpoints)", "Missachten der Roleplay Chat-Regel", 100, 0, 0, false, true, 0, 0),
    VIOLATION_OF_VOICECHAT_RULE("Missachten-der-VoiceChat-Regel-(unter-Level5)", "Missachten der VoiceChat-Regel", 0, 60, 0, false, true, 0, 0),
    VIOLATION_OF_VOICECHAT_RULE_OVER_LEVEL5("Missachten-der-VoiceChat-Regel-(über-Level5)", "Missachten der VoiceChat-Regel", 150, 280, 0, false, true, 0, 0);

    private final String tabReason;
    private final String reason;
    private final int checkpoints;
    private final int banDuration;
    private final int warnAmmount;
    private final boolean loyalityPointReset;
    private final boolean kick;
    private final int weaponLock;
    private final int factionLock;

    Punishment(String tabReason, String reason, int checkpoints, int banDuration, int warnAmmount, boolean loyalityPointReset, boolean kick, int weaponLock, int factionLock) {
        this.tabReason = tabReason;
        this.reason = reason;
        this.checkpoints = checkpoints;
        this.banDuration = banDuration;
        this.warnAmmount = warnAmmount;
        this.loyalityPointReset = loyalityPointReset;
        this.kick = kick;
        this.weaponLock = weaponLock;
        this.factionLock = factionLock;
    }

    public String getReason() {
        return reason;
    }

    public int getCheckpoints() {
        return checkpoints;
    }

    public int getBanDuration() {
        return banDuration;
    }

    public int getWarnAmmount() {
        return warnAmmount;
    }

    public boolean isLoyalityPointReset() {
        return loyalityPointReset;
    }

    public int getWeaponLock() {
        return weaponLock;
    }

    public String getTabReason() {
        return tabReason;
    }

    public boolean isKick() {
        return kick;
    }

    public int getFactionLock() {
        return factionLock;
    }
}
