package com.rettichlp.unicacityaddon.base.enums.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Gangzone {

    LE_MILIEU(Color.DARK_GRAY, List.of(
            Pair.of(new FloatVector3(262, 69, -313), new FloatVector3(262, 69, -219)),
            Pair.of(new FloatVector3(262, 69, -219), new FloatVector3(337, 69, -219)),
            Pair.of(new FloatVector3(337, 69, -219), new FloatVector3(337, 69, -313)),
            Pair.of(new FloatVector3(337, 69, -313), new FloatVector3(262, 69, -313)))),
    MAFIA(Color.CYAN, List.of(
            Pair.of(new FloatVector3(-56, 69, -478), new FloatVector3(-56, 69, -427)),
            Pair.of(new FloatVector3(-56, 69, -427), new FloatVector3(47, 69, -427)),
            Pair.of(new FloatVector3(47, 69, -427), new FloatVector3(47, 69, -509)),
            Pair.of(new FloatVector3(47, 69, -509), new FloatVector3(-35, 69, -509)),
            Pair.of(new FloatVector3(-35, 69, -509), new FloatVector3(-35, 69, -478)),
            Pair.of(new FloatVector3(-35, 69, -478), new FloatVector3(-56, 69, -478)))),
    MEX(Color.YELLOW, List.of(
            Pair.of(new FloatVector3(-343, 69, -149), new FloatVector3(-343, 69, -84)),
            Pair.of(new FloatVector3(-343, 69, -84), new FloatVector3(-251, 69, -84)),
            Pair.of(new FloatVector3(-251, 69, -84), new FloatVector3(-251, 69, -149)),
            Pair.of(new FloatVector3(-251, 69, -149), new FloatVector3(-343, 69, -149)))),
    WESTSIDEBALLAS(Color.MAGENTA, List.of(
            Pair.of(new FloatVector3(-199, 69, 142), new FloatVector3(-199, 69, 272)),
            Pair.of(new FloatVector3(-199, 69, 272), new FloatVector3(-121, 69, 272)),
            Pair.of(new FloatVector3(-121, 69, 272), new FloatVector3(-121, 69, 142)),
            Pair.of(new FloatVector3(-121, 69, 142), new FloatVector3(-199, 69, 142)))),
    OBRIEN(Color.GREEN, List.of(
            Pair.of(new FloatVector3(687, 69, 490), new FloatVector3(687, 69, 593)),
            Pair.of(new FloatVector3(687, 69, 593), new FloatVector3(761, 69, 593)),
            Pair.of(new FloatVector3(761, 69, 593), new FloatVector3(761, 69, 490)),
            Pair.of(new FloatVector3(761, 69, 490), new FloatVector3(687, 69, 490)))),
    KERZAKOV(Color.RED, List.of(
            Pair.of(new FloatVector3(863, 69, 160), new FloatVector3(863, 69, 239)),
            Pair.of(new FloatVector3(863, 69, 239), new FloatVector3(956, 69, 239)),
            Pair.of(new FloatVector3(956, 69, 239), new FloatVector3(956, 69, 160)),
            Pair.of(new FloatVector3(956, 69, 160), new FloatVector3(863, 69, 160)))),
    FARM(Color.LIGHT_GRAY, List.of(
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
    ROTLICHT(Color.LIGHT_GRAY, List.of(
            Pair.of(new FloatVector3(691, 69, -29), new FloatVector3(691, 69, 60)),
            Pair.of(new FloatVector3(691, 69, 60), new FloatVector3(763, 69, 60)),
            Pair.of(new FloatVector3(763, 69, 60), new FloatVector3(763, 69, 58)),
            Pair.of(new FloatVector3(763, 69, 58), new FloatVector3(764, 69, 58)),
            Pair.of(new FloatVector3(764, 69, 58), new FloatVector3(764, 69, 56)),
            Pair.of(new FloatVector3(764, 69, 56), new FloatVector3(765, 69, 56)),
            Pair.of(new FloatVector3(765, 69, 56), new FloatVector3(765, 69, 55)),
            Pair.of(new FloatVector3(765, 69, 55), new FloatVector3(767, 69, 55)),
            Pair.of(new FloatVector3(767, 69, 55), new FloatVector3(767, 69, 54)),
            Pair.of(new FloatVector3(767, 69, 54), new FloatVector3(807, 69, 54)),
            Pair.of(new FloatVector3(807, 69, 54), new FloatVector3(807, 69, -29)),
            Pair.of(new FloatVector3(807, 69, -29), new FloatVector3(691, 69, -29)))),
    HAFEN(Color.LIGHT_GRAY, List.of(
            Pair.of(new FloatVector3(-423, 69, 2), new FloatVector3(-423, 69, 177)),
            Pair.of(new FloatVector3(-423, 69, 177), new FloatVector3(-322, 69, 177)),
            Pair.of(new FloatVector3(-322, 69, 177), new FloatVector3(-322, 69, 2)),
            Pair.of(new FloatVector3(-322, 69, 2), new FloatVector3(-423, 69, 2)))),
    PLATTENBAU(Color.LIGHT_GRAY, List.of(
            Pair.of(new FloatVector3(-525, 69, 415), new FloatVector3(-525, 69, 447)),
            Pair.of(new FloatVector3(-525, 69, 447), new FloatVector3(-510, 69, 447)),
            Pair.of(new FloatVector3(-510, 69, 447), new FloatVector3(-510, 69, 439)),
            Pair.of(new FloatVector3(-510, 69, 439), new FloatVector3(-486, 69, 439)),
            Pair.of(new FloatVector3(-486, 69, 439), new FloatVector3(-486, 69, 429)),
            Pair.of(new FloatVector3(-486, 69, 429), new FloatVector3(-411, 69, 429)),
            Pair.of(new FloatVector3(-411, 69, 429), new FloatVector3(-411, 69, 392)),
            Pair.of(new FloatVector3(-411, 69, 392), new FloatVector3(-418, 69, 392)),
            Pair.of(new FloatVector3(-418, 69, 392), new FloatVector3(-418, 69, 378)),
            Pair.of(new FloatVector3(-418, 69, 378), new FloatVector3(-480, 69, 378)),
            Pair.of(new FloatVector3(-480, 69, 378), new FloatVector3(-480, 69, 413)),
            Pair.of(new FloatVector3(-480, 69, 413), new FloatVector3(-486, 69, 413)),
            Pair.of(new FloatVector3(-486, 69, 413), new FloatVector3(-486, 69, 406)),
            Pair.of(new FloatVector3(-486, 69, 406), new FloatVector3(-496, 69, 406)),
            Pair.of(new FloatVector3(-496, 69, 406), new FloatVector3(-496, 69, 415)),
            Pair.of(new FloatVector3(-496, 69, 415), new FloatVector3(-525, 69, 415)))),
    ALTSTADT(Color.LIGHT_GRAY, List.of(
            Pair.of(new FloatVector3(195, 69, 589), new FloatVector3(195, 69, 637)),
            Pair.of(new FloatVector3(195, 69, 637), new FloatVector3(182, 69, 637)),
            Pair.of(new FloatVector3(182, 69, 637), new FloatVector3(182, 69, 647)),
            Pair.of(new FloatVector3(182, 69, 647), new FloatVector3(174, 69, 647)),
            Pair.of(new FloatVector3(174, 69, 647), new FloatVector3(174, 69, 677)),
            Pair.of(new FloatVector3(174, 69, 677), new FloatVector3(178, 69, 677)),
            Pair.of(new FloatVector3(178, 69, 677), new FloatVector3(178, 69, 687)),
            Pair.of(new FloatVector3(178, 69, 687), new FloatVector3(182, 69, 687)),
            Pair.of(new FloatVector3(182, 69, 687), new FloatVector3(182, 69, 745)),
            Pair.of(new FloatVector3(182, 69, 745), new FloatVector3(176, 69, 745)),
            Pair.of(new FloatVector3(176, 69, 745), new FloatVector3(176, 69, 768)),
            Pair.of(new FloatVector3(176, 69, 768), new FloatVector3(182, 69, 768)),
            Pair.of(new FloatVector3(182, 69, 768), new FloatVector3(182, 69, 794)),
            Pair.of(new FloatVector3(182, 69, 794), new FloatVector3(192, 69, 794)),
            Pair.of(new FloatVector3(192, 69, 794), new FloatVector3(192, 69, 848)),
            Pair.of(new FloatVector3(192, 69, 848), new FloatVector3(304, 69, 848)),
            Pair.of(new FloatVector3(304, 69, 848), new FloatVector3(304, 69, 824)),
            Pair.of(new FloatVector3(304, 69, 824), new FloatVector3(316, 69, 824)),
            Pair.of(new FloatVector3(316, 69, 824), new FloatVector3(316, 69, 803)),
            Pair.of(new FloatVector3(316, 69, 803), new FloatVector3(314, 69, 803)),
            Pair.of(new FloatVector3(314, 69, 803), new FloatVector3(314, 69, 786)),
            Pair.of(new FloatVector3(314, 69, 786), new FloatVector3(313, 69, 786)),
            Pair.of(new FloatVector3(313, 69, 786), new FloatVector3(313, 69, 589)),
            Pair.of(new FloatVector3(313, 69, 589), new FloatVector3(195, 69, 589)))),
    YACHTHAFEN(Color.LIGHT_GRAY, List.of(
            Pair.of(new FloatVector3(236, 69, -551), new FloatVector3(236, 69, -495)),
            Pair.of(new FloatVector3(236, 69, -495), new FloatVector3(292, 69, -495)),
            Pair.of(new FloatVector3(292, 69, -495), new FloatVector3(292, 69, -551)),
            Pair.of(new FloatVector3(292, 69, -551), new FloatVector3(236, 69, -551))));

    private final Color color;
    private final List<Pair<FloatVector3, FloatVector3>> facades;
}
