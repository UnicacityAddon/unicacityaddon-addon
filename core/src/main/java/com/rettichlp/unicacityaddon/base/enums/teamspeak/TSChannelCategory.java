package com.rettichlp.unicacityaddon.base.enums.teamspeak;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum TSChannelCategory {

    UNICACITY_TEAM("UnicaCity Team", 2),
    SERVER_TEAM("Serverteams", 15),
    SERVER_TEAM_BUILDING("Serverteams", 17),
    SUPPORT("Support", 23),
    WAITING_ROOM("Wartezimmer", 40),
    PREMIUM("Premium Lounge", 45),
    EVENTS("Events", 54),
    PUBLIC_TALK("Zivilisten Talks", 58),
    PRIVATE("Private Talks", 88),
    AFK("Abwesend", 90),
    FACTION_POLIZEI("U.C.P.D.", 76),
    FACTION_FBI("FBI", 103),
    FACTION_RETTUNGSDIENST("Rettungsdienst", 117),
    FACTION_LACOSANOSTRA("La Cosa Nostra", 129),
    FACTION_WESTSIDEBALLAS("Westside Ballas", 141),
    FACTION_CALDERON("Calderon Kartell", 153),
    FACTION_KERZAKOV("Kerzakov Familie", 165),
    FACTION_LEMILIEU("Le Milieu", 178),
    FACTION_OBRIEN("O'Brien Familie", 190),
    FACTION_TERRORISTEN("Terroristen", 202),
    FACTION_HITMAN("Hitman", 214),
    FACTION_KIRCHE("Kirche", 226),
    FACTION_NEWS("News Agency", 238);

    private final String categoryName;
    private final int pid;
}