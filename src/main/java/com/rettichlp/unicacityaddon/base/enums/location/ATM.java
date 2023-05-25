package com.rettichlp.unicacityaddon.base.enums.location;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public enum ATM {
    ATM_1(1, 114, 70, 166),
    ATM_2(2, -7, 70, 196),
    ATM_3(3, -268, 70, 235),
    ATM_4(4, -358, 70, 360),
    ATM_5(5, 855, 70, 359),
    ATM_6(6, 296, 70, 49),
    ATM_7(7, 472, 70, 61),
    ATM_8(8, 476, 70, 60),
    ATM_9(9, 480, 70, 60),
    ATM_10(10, 232, 70, 264),
    ATM_11(11, -153, 70, -45),
    ATM_12(12, 613, 70, 153),
    ATM_13(13, 164, 70, 344),
    ATM_14(14, 50, 70, 396),
    ATM_15(15, -86, 70, -404),
    ATM_16(16, 188, 70, -410),
    ATM_17(17, -170, 70, -374),
    ATM_18(18, -169, 70, -504),
    ATM_19(19, 754, 70, 390),
    ATM_20(20, -120, 70, -298),
    ATM_21(21, -62, 70, -353),
    ATM_22(22, -62, 70, -358),
    ATM_23(23, -228, 70, 5),
    ATM_24(24, 181, 70, 397),
    ATM_25(25, 181, 70, 394),
    ATM_26(26, -368, 70, 533),
    ATM_27(27, 782, 70, 48),
    ATM_28(28, 1042, 70, -81),
    ATM_29(29, 1214, 70, -35),
    ATM_30(30, 1163, 70, -176),
    ATM_31(31, 1146, 70, -283),
    ATM_32(32, 1160, 70, 236),
    ATM_33(33, 1018, 70, 203),
    ATM_34(34, 777, 70, 288),
    ATM_35(35, -510, 70, 336),
    ATM_36(36, 730, 70, 502),
    ATM_37(37, 719, 70, 575),
    ATM_38(38, 834, 53, 264),
    ATM_39(39, 1491, 70, 244),
    ATM_40(40, 1542, 70, 357),
    ATM_41(41, 1671, 70, 480),
    ATM_42(42, 1628, 70, 425),
    ATM_43(43, 97, 70, -202),
    ATM_44(44, 215, 70, 603),
    ATM_45(45, 248, 70, 735),
    ATM_46(46, 1437, 70, 238),
    ATM_47(47, 1546, 70, 52), // Stadion
    ATM_48(48, 1546, 70, 44), // Stadion
    ATM_49(49, 1405, 70, -111), // Funpark
    ATM_50(50, 1405, 70, -98), // Funpark
    ATM_51(51, -365, 70, -252), // Mex Reichenviertel
    ATM_52(52, 428, 70, -85), // Fahrschule
    ATM_53(53, 11, 70, 602), // Labor
    ATM_54(54, -401, 70, 171), // Hochseefischer (Transport)
    ATM_55(55, 1443, 72, 126), // LU Bank
    ATM_56(56, 1440, 72, 126), // LU Bank
    ATM_57(57, 1439, 72, 124), // LU Bank
    ATM_58(58, 1439, 72, 121), // LU BANK
    ATM_59(59, 222, 69, -240); // le Milieu ATM

    private final int id;
    private final int x;
    private final int y;
    private final int z;

    ATM(int id, int x, int y, int z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getID() {
        return id;
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

    public String getNaviCommand() {
        return "/navi " + getX() + "/" + getY() + "/" + getZ();
    }
}
