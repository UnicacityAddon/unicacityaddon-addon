package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import com.rettichlp.UnicacityAddon.modules.JobModule;
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author Dimiikou, RettichLP
 */
@UCEvent
public class MoneyEventHandler {

    private long reviveByMedicStartTime;
    private boolean isGRBankCommand;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher jobSalaryMatcher = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);
        if (jobSalaryMatcher.find()) {
            JobModule.addBalance(Integer.parseInt(jobSalaryMatcher.group(1)));
            return false;
        }

        Matcher jobExperienceMatcher = PatternHandler.JOB_EXPERIENCE_PATTERN.matcher(msg);
        if (jobExperienceMatcher.find()) {
            int experience = Integer.parseInt(jobExperienceMatcher.group(1));

            if (jobExperienceMatcher.group(3) != null) {
                int multiplier = Integer.parseInt(jobExperienceMatcher.group(3));
                JobModule.addExperience(experience * multiplier);
                return false;
            }

            JobModule.addExperience(experience);
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

        Matcher bankPayDayMatcher = PatternHandler.STATS_BANK_PATTERN.matcher(msg);
        if (bankPayDayMatcher.find()) {
            BankMoneyModule.setBalance(Integer.parseInt(bankPayDayMatcher.group(1)));
            JobModule.setBalance(0);
            JobModule.setExperience(0);
            PayDayModule.setTime(0);
            return false;
        }

        Matcher bankNewBalanceMatcher = PatternHandler.BANK_NEW_BALANCE_PATTERN.matcher(msg);
        if (bankNewBalanceMatcher.find()) {
            if (isGRBankCommand) {
                isGRBankCommand = false;
                return false;
            }
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
            if (System.currentTimeMillis() - reviveByMedicStartTime > TimeUnit.SECONDS.toMillis(10)) {
                CashMoneyModule.setBalance(0);
                return false;
            }
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
            CashMoneyModule.removeBalance(Integer.parseInt(cashGiveMatcher.group(2)));
            return false;
        }

        Matcher cashTakeMatcher = PatternHandler.CASH_TAKE_PATTERN.matcher(msg);
        if (cashTakeMatcher.find()) {
            CashMoneyModule.addBalance(Integer.parseInt(cashTakeMatcher.group(2)));
            return false;
        }

        Matcher cashToFBankMatcher = PatternHandler.CASH_TO_FBANK_PATTERN.matcher(msg);
        if (cashToFBankMatcher.find()) {
            CashMoneyModule.removeBalance(Integer.parseInt(cashToFBankMatcher.group(1)));
            return false;
        }

        Matcher cashFromFBankMatcher = PatternHandler.CASH_FROM_FBANK_PATTERN.matcher(msg);
        if (cashFromFBankMatcher.find()) {
            CashMoneyModule.addBalance(Integer.parseInt(cashFromFBankMatcher.group(1)));
            return false;
        }

        Matcher cashToBankMatcher = PatternHandler.CASH_TO_BANK_PATTERN.matcher(msg);
        if (cashToBankMatcher.find()) {
            CashMoneyModule.removeBalance(Integer.parseInt(cashToBankMatcher.group(1)));
            return false;
        }

        Matcher cashFromBankMatcher = PatternHandler.CASH_FROM_BANK_PATTERN.matcher(msg);
        if (cashFromBankMatcher.find()) {
            if (isGRBankCommand) return false;

            CashMoneyModule.addBalance(Integer.parseInt(cashFromBankMatcher.group(1)));
            return false;
        }

        Matcher cashGetMatcher = PatternHandler.CASH_GET_PATTERN.matcher(msg);
        if (cashGetMatcher.find()) {
            CashMoneyModule.addBalance(Integer.parseInt(cashGetMatcher.group(1)));
            return false;
        }

        Matcher cashRemoveMatcher = PatternHandler.CASH_REMOVE_PATTERN.matcher(msg);
        if (cashRemoveMatcher.find()) {
            CashMoneyModule.removeBalance(Integer.parseInt(cashRemoveMatcher.group(1)));
            return false;
        }

        Matcher cashStatsMatcher = PatternHandler.CASH_STATS_PATTERN.matcher(msg);
        if (cashStatsMatcher.find()) {
            CashMoneyModule.setBalance(Integer.parseInt(cashStatsMatcher.group(1)));
            return false;
        }

        return false;
    }

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatEvent e) {
        String msg = e.getMessage();
        isGRBankCommand = msg.startsWith("/grkasse get") || msg.startsWith("/grkasse drop");
        return false;
    }
}