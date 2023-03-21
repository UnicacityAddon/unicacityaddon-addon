package com.rettichlp.unicacityaddon.base.enums.location;

import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Arrays;

/**
 * @author RettichLP
 */
public enum Bus {
    ALTSTADT("Altstadt", 263, 70, 596),
    ASIA_PARK("Asia Park", 1278, 70, -74),
    BAR("Bar", -19, 70, 191),
    BASKETBALLPLATZ("Basketballplatz", -265, 70, 320),
    CHERRYS("Cherry's", 100, 70, 455),
    CHINATOWN("ChinaTown", 1080, 70, -106),
    CHINATOWN_CASINO("China Town Casino", 1213, 70, -324),
    CHINATOWN_FLUGHAFEN("ChinaTown Flughafen", 1243, 70, 67),
    CHINATOWN_SUED("ChinaTown Süd", 1137, 70, 266),
    DEATHMATCH_ARENA("Deathmatch-Arena", -492, 70, 336),
    DISKOTHEK("Diskothek", -254, 70, 17),
    FAHRSCHULE("Fahrschule", 436, 70, -69),
    FBI_HQ("F.B.I HQ", 840, 70, -6),
    FEUERWACHE("Feuerwache", -144, 70, -230),
    FISCHERHUETTE("Fischerhütte", -80, 70, 480),
    FLUGHAFEN("Flughafen", -290, 70, 621),
    FRANKREICH("Frankreich", 212, 70, -265),
    FRIEDHOF("Friedhof", 296, 70, -31),
    GEFAENGNIS("Gefängnis", -628, 70, 244),
    HAFEN("Hafen", -359, 70, 167),
    HOCHHAEUSER("Hochhäuser", 1000, 70, 260),
    INDUSTRIEGEBIET("Industriegebiet", 74, 70, -215),
    INSEL("Insel", 672, 66, -450),
    KERZAKOV_GEBIET("Kerzakov Gebiet", 844, 70, 167),
    KRANKENHAUS("Krankenhaus", 229, 70, 243),
    LABOR("Labor", 8, 70, 614),
    LAGERHALLE("Lagerhalle", -68, 70, -427),
    LAS_UNICAS("Las Unicas", 1443, 70, 280),
    LAS_UNICAS_FLUGHAFEN("Las Unicas Flughafen", 1659, 70, 493),
    LITTLE_MEXIKO("Little Mexiko", -306, 70, -101),
    LILLTE_MEXICO_EAST("Little Mexiko East", -50, 70, -157),
    LUIGIS("Luigi's", 238, 70, 65),
    MAKLER("Makler", 185, 70, 296),
    MINE("Mine", 1004, 70, 403),
    MUELLHALDE("Müllhalde", 819, 70, 377),
    NACHRICHTEN_HQ("Nachrichten HQ", -142, 70, -351),
    POLIZEI_HQ("Polizei HQ", -208, 70, -446),
    SCHROTTPLATZ("Schrottplatz", 699, 70, 372),
    STADTHALLE("Stadthalle", 104, 70, 183),
    UNICACITY_NORD("UnicaCity Nord", -54, 70, -589),
    UNICACITY_OST("UnicaCity Ost", 460, 70, 256),
    WESTSIDE_BALLAS_GEBIET("Westside Ballas Gebiet", -161, 70, 231),
    YACHTHAFEN("Yachthafen", 277, 70, -493);

    private final String name;
    private final int x;
    private final int y;
    private final int z;

    Bus(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public FloatVector3 getBlockPos() {
        return new FloatVector3(x, y, z);
    }

    public static Bus getBus(String s) {
        return Arrays.stream(Bus.values())
                .filter(bus -> s.equalsIgnoreCase(bus.name))
                .findFirst()
                .orElse(null);
    }
}