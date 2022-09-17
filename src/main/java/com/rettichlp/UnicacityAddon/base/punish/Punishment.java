package com.rettichlp.UnicacityAddon.base.punish;

/**
 * @author Dimiikou
 */
public enum Punishment {
    AFK_FARMING("AFK-Farming", 300, 180, 0, true, 0),
    ACCOUNT_SHARING("Account Sharing", 0, -1, 0, false, 0),
    ABUSE_OF_UWU_SYSTEM_FIRST_VIOLATION("Ausnutzung des UserWerbenUser-Systems (erstes Vergehen)", 0, 360, 0, false, 0),
    ABUSE_OF_UWU_SYSTEM_SECOND_VIOLATION("Ausnutzung des UserWerbenUser-Systems (zweites Vergehen)", 0, 600, 0, false, 0),
    INSULATION("Beleidigung", 0, 180, 0, false, 0),
    HEAVY_BUGUSE_WITH_OWN_INCOME("Schwerwiegender Buguse mit Eigenertrag", 0, 20160, 2, false, 0),
    BUGUSE_WITH_OWN_INCOME("Buguse mit Eigenertrag", 0, 10080, 1, false, 0),
    BUGUSE("Buguse", 300, 0, 0, false, 0),
    CHEATEN_UNDER_LEVEL4("Cheaten", 0, -1, 0, false, 0),
    CHEATEN_OVER_LEVEL_4("Cheaten", 0, 10080, 1, false, 0),
    EXCESSIVE_USE_OF_CONTRACT("Exzessives Setzen", 0, 2880, 0, false, 0),
    THIRD_PARTY_ADVERTISING_UNDER_LEVEL5("Fremdwerbung", 0, 43200, 1, false, 0),
    THIRD_PARTY_ADVERTISING_OVER_LEVEL5("Fremdwerbung", 0, 10080, 1, false, 0),
    MONEY_STORE_SECONDARY_ACCOUNT("Geldspeicher (Zweitaccount)", 0, -1, 0, false, 0),
    MONEY_STORE_MAIN_ACCOUNT("Geldspeicher (Mainaccount)", 0, 0, 1, false, 0),
    TRADE_WITH_NONGAME_ITEMS("Handel mit spielexternen Dingen", 0, 20160, 1, false, 0),
    DISREGARD_OF_MASK_RULE("Missachten der Masken-Regel", 200, 0, 0, false, 0),
    METAGAMING_FIRST_VIOLATION("Metagaming (erstes Vergehen)", 300, 120, 0, false, 0),
    METAGAMING_SECOND_VIOLATION("Metagaming (zweites Vergehen)", 400, 360, 0, false, 0),
    BULLYING("Mobbing", 0, -1, 0, false, 0),
    KNOWLEDGE_OF_COMMUNITY_BANNED_PLAYER_ACCOUNT("Nichtmelden von Zweitaccounts von Spielern mit Community Ausschluss", 0, -1, 0, false, 0),
    RACISM("Rassismus", 0, -1, 0, false, 0),
    EXTREMISM("Extremismus", 0, -1, 0, false, 0),
    REALLIFE_THREAT("Reallife Drohung", 0, -1, 0, false, 0),
    CALUMNY("Rufmord", 0, 2880, 0, false, 0),
    SDM_FIRST_VIOLATION("Sinnloses Deathmatch (erstes Vergehen)", 300, 0, 0, false, 7),
    SDM_SECOND_VIOLATION("Sinnloses Deathmatch (zweites Vergehen)", 300, 0, 0, false, 14),
    MULTIPLE_SDM("Multiples Sinnloses Deathmatch", 0, 20160, 1, false, 14),
    SPAWNTRAPPING("Spawntrapping", 250, 60, 0, false, 0),
    SUPPORT_REFUSAL("Supporter Verweigerung", 300, 0, 0, false, 0),
    SUPPORT_LIE("Supporter Belügen", 300, 0, 0, false, 0),
    FORBIDDEN_HOTKEY("Unerlaubte Hotkeys", 300, 0, 0, false, 0),
    BREACH_OF_CONTRACT("Vertragsbruch", 0, 720, 1, false, 0),
    BAD_GOV_FIRST_VIOLATION("Bad /gov (erstes Vergehen)", 300, 0, 0, false, 0),
    BAD_GOV_SECOND_VIOLATION("Bad /gov (zweites Vergehen)", 300, 120, 0, false, 0),
    BAD_NEWS_FIRST_VIOLATION("Bad /news (erstes Vergehen)", 300, 0, 0, false, 0),
    BAD_NEWS_SECOND_VIOLATION("Bad /news (zweites Vergehen)", 300, 120, 0, false, 0),
    BAD_MEGAPHONE_FIRST_VIOLATION("Bad /megaphon (erstes Vergehen)", 300, 0, 0, false, 0),
    BAD_MEGAPHONE_SECOND_VIOLATION("Bad /megaphon (zweies Vergehen)", 300, 120, 0, false, 0),
    BLACKLIST_RULE_VIOLATION("Blacklist Regel", 300, 0, 0, false, 0),
    BOMB_RULE_VIOLATION("Bomben Regel", 300, 0, 0, false, 0),
    FACTION_DECEPTION("Fraktionsbetrug", 0, 0, 1, false, 0),
    FACTION_ESCAPE("Fraktionsflucht", 250, 120, 0, false, 0),
    VIOLATION_OF_HOSTAGE_RULE("Missachten der Geiselnahme-Regel", 250,120, 0, false, 0),
    VIOLATION_OF_PLANT_RULE("Missachten der Plantagen-Regel", 300, 0, 0, false, 0),
    VIOLATION_OF_ROBBERY_RULE("Missachten der Überfall-Regel", 250, 120, 0, false, 0),
    BAD_ROLEPLAY_FIRST_VIOLATION("Bad Roleplay (erstes Vergehen)", 300, 120, 0, false, 0),
    BAD_ROLEPLAY_SECOND_VIOLATION("Bad Roleplay (zweites Vergehen)", 400, 360, 0, false, 0),
    VIOLATION_OF_BOND_RULE("Missachten der Fesseln-Regel", 200, 120, 0, false, 0),
    VIOLATION_OF_HUMAND_TRADE_RULE("Missachten der Menschenhandel-Regel", 250, 120, 0, false, 0),
    VIOLATION_OF_NEW_LIFE_RULE("Missachten der New-Life-Regel", 250, 60, 0, false, 0),
    OFFLINE_ESCAPE("Offlineflucht", 250, 180, 0, false, 0),
    AFK_ESCAPE("AFK-Flucht", 250, 180, 0, false, 0),
    TP_ESCAPE("TP-Flucht", 250, 180, 0, false, 0),
    POWERGAMING("Powergaming", 250, 120, 0, false, 0),
    VIOLATION_OF_RAPE_RULE("Missachten der Vergewaltigungs-Regel", 300, 1440, 0, false, 0);

    private final String reason;
    private final int checkpoints;
    private final int banDuration;
    private final int warnAmmount;
    private final boolean loyalityPointReset;
    private final int weaponLock;

    Punishment(String reason, int checkpoints, int banDuration, int warnAmmount, boolean loyalityPointReset, int weaponLock) {
        this.reason = reason;
        this.checkpoints = checkpoints;
        this.banDuration = banDuration;
        this.warnAmmount = warnAmmount;
        this.loyalityPointReset = loyalityPointReset;
        this.weaponLock = weaponLock;
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

}
