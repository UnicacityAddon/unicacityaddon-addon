package com.rettichlp.unicacityaddon.base.enums.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Gangzone {

    LE_MILIEU(List.of(
            Pair.of(new FloatVector3(262, 69, -313), new FloatVector3(262, 69, -219)),
            Pair.of(new FloatVector3(262, 69, -219), new FloatVector3(337, 69, -219)),
            Pair.of(new FloatVector3(337, 69, -219), new FloatVector3(337, 69, -313)),
            Pair.of(new FloatVector3(337, 69, -313), new FloatVector3(262, 69, -313)))),
    MAFIA(List.of(
            Pair.of(new FloatVector3(-56, 69, -478), new FloatVector3(-56, 69, -427)),
            Pair.of(new FloatVector3(-56, 69, -427), new FloatVector3(47, 69, -427)),
            Pair.of(new FloatVector3(47, 69, -427), new FloatVector3(47, 69, -509)),
            Pair.of(new FloatVector3(47, 69, -509), new FloatVector3(-35, 69, -509)),
            Pair.of(new FloatVector3(-35, 69, -509), new FloatVector3(-35, 69, -478)),
            Pair.of(new FloatVector3(-35, 69, -478), new FloatVector3(-56, 69, -478)))),
    MEX(List.of(
            Pair.of(new FloatVector3(-343, 69, -149), new FloatVector3(-343, 69, -84)),
            Pair.of(new FloatVector3(-343, 69, -84), new FloatVector3(-251, 69, -84)),
            Pair.of(new FloatVector3(-251, 69, -84), new FloatVector3(-251, 69, -149)),
            Pair.of(new FloatVector3(-251, 69, -149), new FloatVector3(-343, 69, -149)))),
    HAFEN(List.of(
            Pair.of(new FloatVector3(-423, 69, 2), new FloatVector3(-423, 69, 177)),
            Pair.of(new FloatVector3(-423, 69, 177), new FloatVector3(-322, 69, 177)),
            Pair.of(new FloatVector3(-322, 69, 177), new FloatVector3(-322, 69, 2)),
            Pair.of(new FloatVector3(-322, 69, 2), new FloatVector3(-423, 69, 2)))),
    WESTSIDEBALLAS(List.of(
            Pair.of(new FloatVector3(-199, 69, 142), new FloatVector3(-199, 69, 272)),
            Pair.of(new FloatVector3(-199, 69, 272), new FloatVector3(-121, 69, 272)),
            Pair.of(new FloatVector3(-121, 69, 272), new FloatVector3(-121, 69, 142)),
            Pair.of(new FloatVector3(-121, 69, 142), new FloatVector3(-199, 69, 142)))),
//    HAFEN(List.of(
//            Pair.of(new FloatVector3(, 69, ), new FloatVector3(, 69, )),
//            Pair.of(new FloatVector3(, 69, ), new FloatVector3(, 69, )),
//            Pair.of(new FloatVector3(, 69, ), new FloatVector3(, 69, )),
//            Pair.of(new FloatVector3(, 69, ), new FloatVector3(, 69, )))),
    FARM(List.of(
            Pair.of(new FloatVector3(455, 69, 483), new FloatVector3(455, 69, 562)),
            Pair.of(new FloatVector3(455, 69, 562), new FloatVector3(522, 69, 562)),
            Pair.of(new FloatVector3(522, 69, 562), new FloatVector3(522, 69, 568)),
            Pair.of(new FloatVector3(522, 69, 568), new FloatVector3(550, 69, 568)),
            Pair.of(new FloatVector3(550, 69, 568), new FloatVector3(550, 69, 562)),
            Pair.of(new FloatVector3(550, 69, 562), new FloatVector3(551, 69, 562)),
            Pair.of(new FloatVector3(551, 69, 562), new FloatVector3(551, 69, 551)),
            Pair.of(new FloatVector3(551, 69, 551), new FloatVector3(550, 69, 551)),
            Pair.of(new FloatVector3(550, 69, 551), new FloatVector3(550, 69, 544)),
            Pair.of(new FloatVector3(550, 69, 544), new FloatVector3(525, 69, 544)),
            Pair.of(new FloatVector3(525, 69, 544), new FloatVector3(525, 69, 483)),
            Pair.of(new FloatVector3(525, 69, 483), new FloatVector3(455, 69, 483)))),
    YACHTHAFEN(List.of(
            Pair.of(new FloatVector3(236, 69, -551), new FloatVector3(236, 69, -495)),
            Pair.of(new FloatVector3(236, 69, -495), new FloatVector3(292, 69, -495)),
            Pair.of(new FloatVector3(292, 69, -495), new FloatVector3(292, 69, -551)),
            Pair.of(new FloatVector3(292, 69, -551), new FloatVector3(236, 69, -551))));

    private final List<Pair<FloatVector3, FloatVector3>> facades;
}
