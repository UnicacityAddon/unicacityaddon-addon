package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import com.rettichlp.UnicacityAddon.modules.CashModule;
import com.rettichlp.UnicacityAddon.modules.JobMoneyModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author Dimiikou, RettichLP
 */
public class MoneyEventHandler {

    private long reviveByMedicStartTime;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher jobSalaryMatcher = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);
        if (jobSalaryMatcher.find()) {
            JobMoneyModule.addBalance(Integer.parseInt(jobSalaryMatcher.group(1)));
            return false;
        }

        Matcher kontoauszugMatcher = PatternHandler.BANK_STATEMENT_PATTERN.matcher(msg);
        if (kontoauszugMatcher.find()) {
            BankMoneyModule.setBalance(Integer.parseInt(kontoauszugMatcher.group(1)));

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

            return false;
        }

        Matcher bankPaydayMatcher = PatternHandler.BANK_PAYDAY_PATTERN.matcher(msg);
        if (bankPaydayMatcher.find()) {
            BankMoneyModule.setBalance(Integer.parseInt(bankPaydayMatcher.group(1)));
            JobMoneyModule.setBalance(0);
            return false;
        }

        Matcher bankNewBalanceMatcher = PatternHandler.BANK_NEW_BALANCE_PATTERN.matcher(msg);
        if (bankNewBalanceMatcher.find()) {
            BankMoneyModule.setBalance(Integer.parseInt(bankNewBalanceMatcher.group(1)));
            return false;
        }

        Matcher bankTransferToMatcher = PatternHandler.BANK_TRANSFER_TO_PATTERN.matcher(msg);
        if (bankTransferToMatcher.find()) {
            BankMoneyModule.removeBalance(Integer.parseInt(bankTransferToMatcher.group(2)));
            return false;
        }

        Matcher bankTransferGetMatcher = PatternHandler.BANK_TRANSFER_GET_PATTERN.matcher(msg);
        if (bankTransferGetMatcher.find()) {
            BankMoneyModule.addBalance(Integer.parseInt(bankTransferGetMatcher.group(2)));
            return false;
        }


        Matcher reviveByMedicStartMatcher = PatternHandler.REVIVE_BY_MEDIC_START_PATTERN.matcher(msg);
        if (reviveByMedicStartMatcher.find()) {
            reviveByMedicStartTime = System.currentTimeMillis();
            return false;
        }

        Matcher reviveByMedicFinishMatcher = PatternHandler.REVIVE_BY_MEDIC_FINISH_PATTERN.matcher(msg);
        if (reviveByMedicFinishMatcher.find()) {
            if (System.currentTimeMillis() - reviveByMedicStartTime > TimeUnit.SECONDS.toMillis(10)) return false;
            BankMoneyModule.removeBalance(27); // successfully revived by medic = 27$
            return false;
        }

        Matcher lottoWinMatcher = PatternHandler.LOTTO_WIN.matcher(msg);
        if (lottoWinMatcher.find()) {
            BankMoneyModule.addBalance(Integer.parseInt(lottoWinMatcher.group(1)));
            return false;
        }


        Matcher cashGiveMatcher = PatternHandler.CASH_GIVE_PATTERN.matcher(msg);
        if (cashGiveMatcher.find()) {
            CashModule.removeBalance(Integer.parseInt(cashGiveMatcher.group(1)));
            return false;
        }

        Matcher cashTakeMatcher = PatternHandler.CASH_TAKE_PATTERN.matcher(msg);
        if (cashTakeMatcher.find()) {
            CashModule.addBalance(Integer.parseInt(cashTakeMatcher.group(2)));
            return false;
        }

        Matcher cashToFBankMatcher = PatternHandler.CASH_TO_FBANK_PATTERN.matcher(msg);
        if (cashToFBankMatcher.find()) {
            CashModule.removeBalance(Integer.parseInt(cashToFBankMatcher.group(1)));
            return false;
        }

        Matcher cashFromFBankMatcher = PatternHandler.CASH_FROM_FBANK_PATTERN.matcher(msg);
        if (cashFromFBankMatcher.find()) {
            CashModule.addBalance(Integer.parseInt(cashFromFBankMatcher.group(1)));
            return false;
        }

        Matcher cashGetMatcher = PatternHandler.CASH_GET_PATTERN.matcher(msg);
        if (cashGetMatcher.find()) {
            CashModule.addBalance(Integer.parseInt(cashGetMatcher.group(1)));
            return false;
        }

        Matcher cashRemoveMatcher = PatternHandler.CASH_REMOVE_PATTERN.matcher(msg);
        if (cashRemoveMatcher.find()) {
            CashModule.removeBalance(Integer.parseInt(cashRemoveMatcher.group(1)));
            return false;
        }

        Matcher cashStatsMatcher = PatternHandler.CASH_STATS_PATTERN.matcher(msg);
        if (cashStatsMatcher.find()) {
            CashModule.setBalance(Integer.parseInt(cashStatsMatcher.group(1)));
            return false;
        }

        return false;
    }
}