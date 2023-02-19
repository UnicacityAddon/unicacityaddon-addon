package com.rettichlp.unicacityaddon.events.faction.state;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Gelegenheitscode
 */
@UCEvent
public class WantedListEventHandler {
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e)   {
        if (!ConfigElements.getBetterWantedList())  {
            return;
        }
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.WANTED_LIST_PATTERN.matcher(msg);
        if (m.find())   {
            int WantedAmount = Integer.parseInt(m.group(2));
            if (WantedAmount == 1) {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.DARK_GREEN).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.DARK_GREEN).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.DARK_GREEN).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.DARK_GREEN).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            } else if (WantedAmount <= 15 && WantedAmount >= 2) {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.GREEN).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.GREEN).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.GREEN).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.GREEN).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            } else if (WantedAmount <= 25 && WantedAmount >= 16)    {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.YELLOW).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.YELLOW).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            } else if (WantedAmount <= 49 && WantedAmount >26)  {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.GOLD).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.GOLD).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.GOLD).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.GOLD).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            } else if (WantedAmount <= 59 && WantedAmount >=50) {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.RED).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.RED).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.RED).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.RED).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            } else if (WantedAmount <= 69 && WantedAmount >= 60)    {
                if (msg.contains("AFK")) {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.DARK_RED).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.DARK_RED).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of("AFK").color(ColorCode.GRAY).advance()
                            .createComponent());
                } else  {
                    e.setMessage(Message.getBuilder().of("▶").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.DARK_RED).advance().space()
                            .of("|").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(3)).color(ColorCode.DARK_RED).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(m.group(2)).color(ColorCode.BLUE).advance()
                            .of(")").color(ColorCode.GRAY).advance().space()
                            .createComponent());
                }
            }
        }
    }
}