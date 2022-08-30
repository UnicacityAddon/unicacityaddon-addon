// ############################################################################
//
//       /.--------------.\
//      //                \\
//     //                  \\
//    || .-..----. .-. .--. ||
//    ||( ( '-..-'|.-.||.-.|||
//    || \ \  ||  || ||||_||||
//    ||._) ) ||  \'-'/||-' ||
//     \\'-'  `'   `-' `'  //
//      \\                //
//       \\______________//
//        '--------------'
//              |_|_
//       ____ _/ _)_)
//           '  | (_)
//        .--'"\| ()
//              | |
//              | |
//              |_|
//
// This file should not be modified and is therefore listed in the .gitignore
// file.
//
// The complete list of navigation points should not be published. As a
// result, here is only an incomplete list of navigation points.
// However, the functionality is the same as in the actual, complete list.
//
// When a release is created, the full list of navi points is used.
// ############################################################################

package com.rettichlp.UnicacityAddon.base.location;

import net.minecraft.util.math.BlockPos;

public enum NaviPoint {
    ALTSTADT("Altstadt", 255, 69, 592),
    ANGELSCHEIN("Angelschein",  -343, 63, 60),
    ANWALTSKANZLEI("Anwaltskanzlei", -132, 71, -501),
    APOTHEKE("Apotheke", 270, 69, 15),
    APOTHEKE_ZENTRALE("Apotheke [Zentrale]", -87, 70, -395),
    ASSERVATENKAMMER("Asservatenkammer", 118, 69, -256),
    ATOMKRAFTWERK("Atomkraftwerk", 1145, 69, 395),
    AUTOHAENDLER_CHINA_TOWN("Autohändler [China Town]", 1048, 69, -280),
    BANK("Bank", -71, 69, -356),
    BAR("Bar", -47, 69, 194),
    BAR_ROTLICHT("Bar [Rotlicht]", 803, 69, 29),
    BLUMENLADEN("Blumenladen", 500, 69, 74),
    BRAUEREI("Brauerei", 195, 69, 665),
    BRUECKE("Brücke", 81, 69, -118),
    BUECHERLADEN("Bücherladen", 508, 76, 77),
    CASINO("Casino", 249, 69, -13),
    CASINO_CHINA_TOWN("Casino [China Town]", 1238, 69, -314),
    CFK_BALLAS("CFK [Ballas]", -327, 69, 350),
    CHERRYS("Cherrys", 39, 69, 402),
    CHINA_TOWN("China Town", 943, 69, -42),
    CTA("CTA", 1261, 69, 46),
    DEATHMATCH_ARENA("Deathmatch Arena", -503, 69, 336),
    DIEB("Dieb", -380, 69, 392),
    DISKO("Disko", -237, 69, -3),
    EISENSTOLLEN("Eisenstollen", 1061, 51, 325),
    FAHRSCHULE("Fahrschule", 420, 69, -81),
    FAHRZEUGHAENDLER("Fahrzeughändler", 167, 69, -212),
    FAHRZEUGHAENDLER_LUXUS("Fahrzeughändler [Luxus]", -172, 69, -528),
    FARM("Farm", 486, 67, 535),
    FBI("FBI", 867, 69, -61),
    FEINKOSTLADEN("Feinkostladen", -95, 69, -337),
    FEUERWEHR("Feuerwehr", -117, 69, -251),
    FEUERWEHRSTATION("Feuerwehrstation", -114, 69, -247),
    FEUERWERKSLADEN("Feuerwerksladen", -278, 69, 134),
    FISCHERHUETTE("Fischerhütte", -124, 69, 369),
    FITNESSSTUDIO("Fitnessstudio", 758, 69, 391),
    FLUGHAFEN("Flughafen", -283, 69, 642),
    FREIBAD("Freibad", -268, 69, -462),
    FUNPARK("Funpark", 1407, 69, -94),
    GANG("Gang", -161, 69, 202),
    GEFAENGNIS("Gefängnis", -635, 69, 238),
    GEMUESELADEN("Gemüseladen", 489, 69, 14),
    GOLF("Golf", 1290, 69, -291),
    GRUPPIERUNG("Gruppierung", 136, 98, 157),
    HAFEN("Hafen", -370, 69, 84),
    HAFEN_CHINA_TOWN("Hafen [China Town]", 1203, 69, -400),
    HANKYS_ELEKTROLADEN("Hanky's Elektroladen", 488, 69, 25),
    HAUSADDON_SHOP("Hausaddon Shop", 94, 69, -210),
    HOCHSEEFISCHER("Hochseefischer", -504, 69, 212),
    INSEL("Insel", 675, 65, -451),
    JAGDHUETTE("Jagdhütte", 381, 71, -275),
    KERZAKOV_FAMILIE("Kerzakov Familie", 848, 69, 203),
    KERZAKOV_FAMILIEN_BAR("KF-Bar", 779, 69, 285),
    KFZ_WERKSTATT("KFZ Werkstatt", -153, 69, 255),
    KINO("Kino", 758, 69, 287),
    KIRCHE("Kirche", 307, 72, -97),
    KRANKENHAUS("Krankenhaus", 289, 69, 244),
    LABOR("Labor", -1, 69, 609),
    LASERTAG_ARENA("Lasertag Arena", 78, 69, -284),
    LAS_UNICAS("Las Unicas", 1365, 69, 269),
    LUIGIS("Luigi's", 250, 69, 58),
    MAFIA("Mafia", -13, 69, -465),
    MAKLERBUERO("Maklerbüro", 161, 69, 304),
    MALL("Mall", 474, 69, 80),
    METZGEREI("Metzgerei", -147, 69, -325),
    MEXIKANISCHES_KARTELL("Mexikanisches Kartell", -283, 69, -124),
    MILITAERBASIS("Militärbasis", -345, 58, -472),
    MOTEL("Motel", -376, 69, 511),
    MUELLHALDE("Müllhalde", 845, 69, 367),
    MURICA_WAFFENLADEN("Murica Waffenladen", -59, 69, -386),
    MUSIKLADEN("Musikladen", 507, 76, 64),
    NACHRICHTEN("Nachrichten", -87, 69, -353),
    NEULINGSHOTEL("Neulingshotel", -364, 69, 541),
    OBRIEN("O'Brien", 709, 69, 565),
    PAPIERFABRIK("Papierfabrik", -252, 69, -411),
    PARK("Park", 96, 69, 284),
    PIZZALIEFERANT("Pizzalieferant", 259, 69, 67),
    POLIZEISTATION("Polizeistation", -225, 69, -465),
    POSTZENTRALE("Postzentrale", -88, 71, -364),
    PSYCHIATRIE("Psychiatrie", 1579, 67, -404),
    PULVERMINE("Pulvermine", 510, 63, 178),
    RAFFINERIE("Raffinerie", 1772, 69, 669),
    RESTAURANT_CHERRYS("Restaurant [Cherrys]", -57, 71, 349),
    RESTAURANT_CHINA_TOWN("Restaurant [China Town]", 1174, 78, -289),
    RESTAURANT_MEXIKANISCHES_VIERTEL("Restaurant [Mexikanisches Viertel]", -493, 72, -274),
    ROTLICHTHOTEL("Rotlichthotel", 781, 69, -44),
    ROTLICHTVIERTEL("Rotlichtviertel", 564, 69, 113),
    SAEGEWERK("Sägewerk", 433, 64, 420),
    SCHUHLADEN("Schuhladen", 499, 69, 52),
    SHISHABAR("Shishabar", -133, 69, -78),
    SHISHABAR_MALL("Shishabar [Mall]", 481, 76, 54),
    SHOP_CHINA_TOWN("Shop [China Town]", 1060, 69, -188),
    STADTHALLE("Stadthalle", 110, 69, 157),
    STALL("Stall", 378, 64, 478),
    SUPERMARKT("Supermarkt", 45, 69, 192),
    TABAKPLANTAGE("Tabakplantage", 405, 64, 625),
    TANKSTELLE("Tankstelle", 232, 69, -145),
    TANKSTELLE_CHINA_TOWN("Tankstelle [China Town]", 1100, 69, -205),
    TANKSTELLE_MEXIKANISCHES_VIERTEL("Tankstelle [Mexikanisches Viertel]", -171, 69, 4),
    TANKSTELLE_POLIZEI("Tankstelle [Polizei]", -186, 69, -352),
    TAXI_ZENTRALE("Taxi Zentrale", 863, 69, 139),
    TERRORISTEN("Terroristen", -55, 69, 491),
    TIERHANDLUNG("Tierhandlung", 760, 69, 340),
    TRIADEN("Triaden", 1008, 69, -102),
    UCM("UCM", 1155, 69, -184),
    UNICA17("Unica17", 615, 69, 108),
    URANMINE("Uranmine", -500, 67, 677),
    UBAHN_ALTSTADT("U-Bahn [Altstadt]", 207, 69, 485),
    UBAHN_FEUERWEHR("U-Bahn [Feuerwehr]", -119, 69, -292),
    UBAHN_FLUGHAFEN("U-Bahn [Flughafen]", -304, 69, 619),
    UBAHN_GANG_GEBIET("U-Bahn [Gang Gebiet]", -342, 69, 416),
    UBAHN_JVA_UNICACITY("U-Bahn [JVA UnicaCity]", 423, 69, -1),
    UBAHN_KERZAKOV_GEBIET("U-Bahn [Kerzakov Gebiet]", 853, 69, 268),
    UBAHN_LITTLE_MEXIKO("U-Bahn [Little Mexiko]", -146, 69, -35),
    UBAHN_MALL("U-Bahn [Mall]", 75, 69, -194),
    UBAHN_STADTHALLE("U-Bahn [Stadthalle]", -14, 69, 280),
    WAFFENFABRIK("Waffenfabrik", -224, 69, -438),
    WAFFENLADEN("Waffenladen", -180, 69, 237),
    WAFFENSCHEIN_ANMELDESTELLE("Waffenschein", 153, 80, 172),
    WAFFENTRAINING("Waffentraining", -59, 69, -388),
    WALTERS_POLIZEI("Walter's [Polizei]", -170, 69, -426),
    WEINBERG("Weinberg", 10, 91, 565),
    WINZER("Winzer", 10, 91, 568),
    YACHT("Yacht", 267, 69, -514),
    SWAT("Swat", 413, 69, -75),
    HITMAN("Hitman", 310, 69, 60),

    X3_1("x3", 139, 50, 230),
    X3_2("x3", 139, 100, 230),
    X3_3("x3", 139, 150, 230),
    X3_4("x3", 139, 200, 230);

    private final String name;
    private final int x;
    private final int y;
    private final int z;

    NaviPoint(String name, int x, int y, int z) {
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

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public long getDistance(BlockPos blockPos) {
        return Math.round(blockPos.getDistance(x, y, z));
    }
}