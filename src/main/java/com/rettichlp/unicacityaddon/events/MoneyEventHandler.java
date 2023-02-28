package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.money.ATMFillCommand;
import com.rettichlp.unicacityaddon.commands.money.ReichensteuerCommand;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class MoneyEventHandler {

    private boolean isGRBankCommand;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher jobSalaryMatcher = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);
        if (jobSalaryMatcher.find()) {
            FileManager.DATA.addJobBalance(Integer.parseInt(jobSalaryMatcher.group(1)));
            return;
        }

        Matcher jobExperienceMatcher = PatternHandler.JOB_EXPERIENCE_PATTERN.matcher(msg);
        if (jobExperienceMatcher.find()) {
            int experience = Integer.parseInt(jobExperienceMatcher.group(1));

            if (jobExperienceMatcher.group(3) != null) {
                int multiplier = Integer.parseInt(jobExperienceMatcher.group(3));
                FileManager.DATA.addJobExperience(experience * multiplier);
                return;
            }

            FileManager.DATA.addJobExperience(experience);
        }

        Matcher kontoauszugMatcher = PatternHandler.BANK_STATEMENT_PATTERN.matcher(msg);
        if (kontoauszugMatcher.find()) {
            FileManager.DATA.setBankBalance(Integer.parseInt(kontoauszugMatcher.group(1)));

            if (ConfigElements.getEventATM()) {
                if (ConfigElements.getEventATMFBank()) {
                    p.sendChatMessage("/fbank");
                }

                if (ConfigElements.getEventATMGRKasse()) {
                    p.sendChatMessage("/grkasse info");
                }

                if (ConfigElements.getEventATMInfo()) {
                    p.sendChatMessage("/atminfo");
                }
            }

            return;
        }

        Matcher bankPayDayMatcher = PatternHandler.BANK_STATS_PATTERN.matcher(msg);
        if (bankPayDayMatcher.find()) {
            APIRequest.sendStatisticAddRequest(StatisticType.PLAYTIME);
            FileManager.DATA.setBankBalance(Integer.parseInt(bankPayDayMatcher.group(1)));
            FileManager.DATA.setJobBalance(0);
            FileManager.DATA.setJobExperience(0);
            FileManager.DATA.setPayDayTime(0);
            return;
        }

        Matcher bankNewBalanceMatcher = PatternHandler.BANK_NEW_BALANCE_PATTERN.matcher(msg);
        if (bankNewBalanceMatcher.find()) {
            if (isGRBankCommand) {
                isGRBankCommand = false;
                return;
            }
            FileManager.DATA.setBankBalance(Integer.parseInt(bankNewBalanceMatcher.group(1)));
            return;
        }

        Matcher bankTransferToMatcher = PatternHandler.BANK_TRANSFER_TO_PATTERN.matcher(msg);
        if (bankTransferToMatcher.find()) {
            FileManager.DATA.removeBankBalance(Integer.parseInt(bankTransferToMatcher.group(2)));
            return;
        }

        Matcher bankTransferGetMatcher = PatternHandler.BANK_TRANSFER_GET_PATTERN.matcher(msg);
        if (bankTransferGetMatcher.find()) {
            FileManager.DATA.addBankBalance(Integer.parseInt(bankTransferGetMatcher.group(2)));
            return;
        }

        Matcher lottoWinMatcher = PatternHandler.LOTTO_WIN.matcher(msg);
        if (lottoWinMatcher.find()) {
            FileManager.DATA.addBankBalance(Integer.parseInt(lottoWinMatcher.group(1)));
            return;
        }

        Matcher cashGiveMatcher = PatternHandler.CASH_GIVE_PATTERN.matcher(msg);
        if (cashGiveMatcher.find()) {
            FileManager.DATA.removeCashBalance(Integer.parseInt(cashGiveMatcher.group(2)));
            return;
        }

        Matcher cashTakeMatcher = PatternHandler.CASH_TAKE_PATTERN.matcher(msg);
        if (cashTakeMatcher.find()) {
            FileManager.DATA.addCashBalance(Integer.parseInt(cashTakeMatcher.group(2)));
            return;
        }

        Matcher cashToFBankMatcher = PatternHandler.CASH_TO_FBANK_PATTERN.matcher(msg);
        if (cashToFBankMatcher.find()) {
            FileManager.DATA.removeCashBalance(Integer.parseInt(cashToFBankMatcher.group(1)));
            return;
        }

        Matcher cashFromFBankMatcher = PatternHandler.CASH_FROM_FBANK_PATTERN.matcher(msg);
        if (cashFromFBankMatcher.find()) {
            FileManager.DATA.addCashBalance(Integer.parseInt(cashFromFBankMatcher.group(1)));
            return;
        }

        Matcher cashToBankMatcher = PatternHandler.CASH_TO_BANK_PATTERN.matcher(msg);
        if (cashToBankMatcher.find()) {
            FileManager.DATA.removeCashBalance(Integer.parseInt(cashToBankMatcher.group(1)));
            return;
        }

        Matcher cashFromBankMatcher = PatternHandler.CASH_FROM_BANK_PATTERN.matcher(msg);
        if (cashFromBankMatcher.find()) {
            if (isGRBankCommand)
                return;
            FileManager.DATA.addCashBalance(Integer.parseInt(cashFromBankMatcher.group(1)));
            return;
        }

        Matcher cashGetMatcher = PatternHandler.CASH_GET_PATTERN.matcher(msg);
        if (cashGetMatcher.find()) {
            FileManager.DATA.addCashBalance(Integer.parseInt(cashGetMatcher.group(1)));
            return;
        }

        Matcher cashRemoveMatcher = PatternHandler.CASH_REMOVE_PATTERN.matcher(msg);
        if (cashRemoveMatcher.find()) {
            FileManager.DATA.removeCashBalance(Integer.parseInt(cashRemoveMatcher.group(1)));
            return;
        }

        Matcher cashStatsMatcher = PatternHandler.CASH_STATS_PATTERN.matcher(msg);
        if (cashStatsMatcher.find()) {
            FileManager.DATA.setCashBalance(Integer.parseInt(cashStatsMatcher.group(1)));
            return;
        }

        Matcher atmInfoMatcher = PatternHandler.ATM_INFO_PATTERN.matcher(msg);
        if (atmInfoMatcher.find() && (ReichensteuerCommand.isActive || ATMFillCommand.isActive)) {
            ReichensteuerCommand.cashInATM = ATMFillCommand.cashInATM = Integer.parseInt(atmInfoMatcher.group(2));
            e.setCanceled(true);
        }

        Matcher bankStatementOtherMatcher = PatternHandler.BANK_STATEMENT_OTHER_PATTERN.matcher(msg);
        if (bankStatementOtherMatcher.find()) {
            int cash = Integer.parseInt(bankStatementOtherMatcher.group(2));
            int bankBalance = Integer.parseInt(bankStatementOtherMatcher.group(3));
            int oneQuarterOfAll = (cash + bankBalance) / 4;
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));

            e.setMessage(Message.getBuilder()
                    .of("Finanzen von " + bankStatementOtherMatcher.group(1)).color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.GOLD).advance().space()
                    .of("Geld").color(ColorCode.RED).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(cash + "$ |").color(ColorCode.RED).advance().space()
                    .of("Bank").color(ColorCode.RED).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(bankBalance + "$").color(ColorCode.RED).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(numberFormat.format(oneQuarterOfAll) + "$").color(ColorCode.RED).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
        String msg = e.getMessage();
        isGRBankCommand = msg.startsWith("/grkasse get") || msg.startsWith("/grkasse drop");
    }
}