package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 * @author Gelegenheitscode
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ContractListener {

    public static final List<String> CONTRACT_LIST = new ArrayList<>();
    private static long hitlistShown;

    private final UnicacityAddon unicacityAddon;

    public ContractListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();
        long currentTime = System.currentTimeMillis();

        Matcher contractSetMatcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(msg);
        if (contractSetMatcher.find()) {
            this.unicacityAddon.soundController().playContractSetSound();
            String target = contractSetMatcher.group(1);
            CONTRACT_LIST.add(target);

            if (this.unicacityAddon.configuration().faction().contract().get()) {
                e.setMessage(Message.getBuilder()
                        .of("CT-SET").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(target).color(ColorCode.DARK_AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(contractSetMatcher.group(2) + "$").color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            }

            return;
        }

        Matcher contractKillMatcher = PatternHandler.CONTRACT_KILL_PATTERN.matcher(msg);
        if (contractKillMatcher.find()) {
            this.unicacityAddon.soundController().playContractFulfilledSound();
            String target = contractKillMatcher.group(2);
            CONTRACT_LIST.remove(target);
            String hitman = contractKillMatcher.group(1);

            if (hitman.equals(p.getName())) {
                this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage(this.unicacityAddon);
            }

            if (this.unicacityAddon.configuration().faction().contract().get()) {
                e.setMessage(Message.getBuilder().of("CT-KILL").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(hitman).color(ColorCode.DARK_AQUA).advance().space()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(target).color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(contractKillMatcher.group(3) + "$").color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            }

            return;
        }

        Matcher contractDeleteMatcher = PatternHandler.CONTRACT_DELETE_PATTERN.matcher(msg);
        if (contractDeleteMatcher.find()) {
            String target = contractDeleteMatcher.group(2);
            CONTRACT_LIST.remove(target);

            if (this.unicacityAddon.configuration().faction().contract().get()) {
                e.setMessage(Message.getBuilder().of("CT-DELETE").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(contractDeleteMatcher.group(1)).color(ColorCode.DARK_AQUA).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(contractDeleteMatcher.group(2)).color(ColorCode.AQUA).advance().createComponent());
            }

            return;
        }

        Matcher contractListHeaderMatcher = PatternHandler.CONTRACT_LIST_HEADER_PATTERN.matcher(msg);
        if (contractListHeaderMatcher.find()) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        Matcher contractListMatcher = PatternHandler.CONTRACT_LIST_PATTERN.matcher(msg);
        if (contractListMatcher.find() && currentTime - hitlistShown < 5000) {
            String name = contractListMatcher.group("name");
            CONTRACT_LIST.add(name);
        }
    }
}