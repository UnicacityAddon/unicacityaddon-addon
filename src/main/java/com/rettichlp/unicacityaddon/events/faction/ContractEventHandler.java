package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
public class ContractEventHandler {

    public static final List<String> CONTRACT_LIST = new ArrayList<>();
    private static long hitlistShown;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHitlistShown(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        long currentTime = System.currentTimeMillis();

        if (unformattedMessage.equals("=~=~=~Contracts~=~=~=")) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        Matcher contractListMatcher = PatternHandler.CONTRACT_LIST_PATTERN.matcher(unformattedMessage);
        if (contractListMatcher.find() && currentTime - hitlistShown < 5000) {
            String name = contractListMatcher.group("name");
            CONTRACT_LIST.add(name);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractSet(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            AbstractionLayer.getPlayer().playSound(SoundRegistry.CONTRACT_SET_SOUND, 1, 1);
            String name = matcher.group(1);
            CONTRACT_LIST.add(name);
            if (ConfigElements.getContractMessageActivated()) {
                e.setMessage(Message.getBuilder()
                        .of("CT-SET").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(name).color(ColorCode.DARK_AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(matcher.group(2) + "$").color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractRemoved(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = null;
            for (int i = 1; i < matcher.groupCount() + 1; i++) {
                String tempName = matcher.group(i);
                if (tempName == null)
                    continue;

                name = tempName;
                break;
            }

            if (unformattedMessage.contains("getötet")) {
                p.playSound(SoundRegistry.CONTRACT_FULFILLED_SOUND, 1, 1);
            }
            CONTRACT_LIST.remove(name);

            if (ConfigElements.getContractMessageActivated() && unformattedMessage.contains("getötet"))
                e.setMessage(Message.getBuilder().of("CT-KILL").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(matcher.group(1)).color(ColorCode.DARK_AQUA).advance().space()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(matcher.group(2)).color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(matcher.group(3) + "$").color(ColorCode.RED).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());

            if (unformattedMessage.contains("getötet") && unformattedMessage.contains(p.getName())) {
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage();
            }
        }
        matcher = PatternHandler.CONTRACT_DELETE_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            if (ConfigElements.getContractMessageActivated())
                e.setMessage(Message.getBuilder().of("CT-DELETE").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(matcher.group(1)).color(ColorCode.DARK_AQUA).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(matcher.group(2)).color(ColorCode.AQUA).advance().createComponent());
        }
    }
}