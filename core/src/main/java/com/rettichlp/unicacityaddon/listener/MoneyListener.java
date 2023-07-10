package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.config.atm.ATMConfiguration;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.api.activity.PayEquipCommand;
import com.rettichlp.unicacityaddon.commands.money.ATMFillCommand;
import com.rettichlp.unicacityaddon.commands.money.RichTaxesCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class MoneyListener {

    private boolean isGRBankCommand;

    private final UnicacityAddon unicacityAddon;

    public MoneyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        Matcher jobSalaryMatcher = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);
        if (jobSalaryMatcher.find()) {
            this.unicacityAddon.fileService().data().addJobBalance(Integer.parseInt(jobSalaryMatcher.group(1)));
            return;
        }

        Matcher jobExperienceMatcher = PatternHandler.JOB_EXPERIENCE_PATTERN.matcher(msg);
        if (jobExperienceMatcher.find()) {
            int experience = Integer.parseInt(jobExperienceMatcher.group(1));

            if (jobExperienceMatcher.group(3) != null) {
                int multiplier = Integer.parseInt(jobExperienceMatcher.group(3));
                this.unicacityAddon.fileService().data().addJobExperience(experience * multiplier);
                return;
            }

            this.unicacityAddon.fileService().data().addJobExperience(experience);
            return;
        }

        Matcher bankStatementMatcher = PatternHandler.BANK_STATEMENT_PATTERN.matcher(msg);
        if (bankStatementMatcher.find()) {
            this.unicacityAddon.fileService().data().setBankBalance(Integer.parseInt(bankStatementMatcher.group(1)));

            ATMConfiguration atmConfiguration = this.unicacityAddon.configuration().atm();
            if (atmConfiguration.enabled().get()) {
                if (atmConfiguration.faction().get()) {
                    p.sendServerMessage("/fbank");
                }

                if (atmConfiguration.grouping().get()) {
                    p.sendServerMessage("/grkasse info");
                }

                if (atmConfiguration.info().get()) {
                    p.sendServerMessage("/atminfo");
                }
            }

            return;
        }

        Matcher bankPayDayMatcher = PatternHandler.BANK_STATS_PATTERN.matcher(msg);
        if (bankPayDayMatcher.find()) {
            this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.PLAYTIME);
            this.unicacityAddon.fileService().data().setBankBalance(Integer.parseInt(bankPayDayMatcher.group(1)));
            this.unicacityAddon.fileService().data().setJobBalance(0);
            this.unicacityAddon.fileService().data().setJobExperience(0);
            this.unicacityAddon.fileService().data().setPayDayTime(0);
            return;
        }

        Matcher bankNewBalanceMatcher = PatternHandler.BANK_NEW_BALANCE_PATTERN.matcher(msg);
        if (bankNewBalanceMatcher.find()) {
            if (isGRBankCommand) {
                isGRBankCommand = false;
                return;
            }
            this.unicacityAddon.fileService().data().setBankBalance(Integer.parseInt(bankNewBalanceMatcher.group(1)));
            return;
        }

        Matcher bankTransferToMatcher = PatternHandler.BANK_TRANSFER_TO_PATTERN.matcher(msg);
        if (bankTransferToMatcher.find()) {
            this.unicacityAddon.fileService().data().removeBankBalance(Integer.parseInt(bankTransferToMatcher.group(2)));
            return;
        }

        Matcher bankTransferGetMatcher = PatternHandler.BANK_TRANSFER_GET_PATTERN.matcher(msg);
        if (bankTransferGetMatcher.find()) {
            this.unicacityAddon.fileService().data().addBankBalance(Integer.parseInt(bankTransferGetMatcher.group(2)));
            return;
        }

        Matcher lottoWinMatcher = PatternHandler.LOTTO_WIN.matcher(msg);
        if (lottoWinMatcher.find()) {
            this.unicacityAddon.fileService().data().addBankBalance(Integer.parseInt(lottoWinMatcher.group(1)));
            return;
        }

        Matcher cashGiveMatcher = PatternHandler.CASH_GIVE_PATTERN.matcher(msg);
        if (cashGiveMatcher.find()) {
            this.unicacityAddon.fileService().data().removeCashBalance(Integer.parseInt(cashGiveMatcher.group(2)));
            return;
        }

        Matcher cashTakeMatcher = PatternHandler.CASH_TAKE_PATTERN.matcher(msg);
        if (cashTakeMatcher.find()) {
            this.unicacityAddon.fileService().data().addCashBalance(Integer.parseInt(cashTakeMatcher.group(2)));
            return;
        }

        Matcher cashToFBankMatcher = PatternHandler.CASH_TO_FBANK_PATTERN.matcher(msg);
        if (cashToFBankMatcher.find() && msg.contains(p.getName())) {
            int money = Integer.parseInt(cashToFBankMatcher.group(1));
            this.unicacityAddon.fileService().data().removeCashBalance(money);

            if (PayEquipCommand.payEquipMap.getValue() == money && this.unicacityAddon.utilService().isUnicacity()) {
                ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                        .activity(ActivityCheckBuilder.Activity.EQUIP_EDIT)
                        .type(PayEquipCommand.payEquipMap.getKey())
                        .value("true")
                        .send();
            }

            return;
        }

        Matcher cashFromFBankMatcher = PatternHandler.CASH_FROM_FBANK_PATTERN.matcher(msg);
        if (cashFromFBankMatcher.find() && msg.contains(p.getName())) {
            this.unicacityAddon.fileService().data().addCashBalance(Integer.parseInt(cashFromFBankMatcher.group(1)));
            return;
        }

        Matcher cashToBankMatcher = PatternHandler.CASH_TO_BANK_PATTERN.matcher(msg);
        if (cashToBankMatcher.find()) {
            this.unicacityAddon.fileService().data().removeCashBalance(Integer.parseInt(cashToBankMatcher.group(1)));
            return;
        }

        Matcher cashFromBankMatcher = PatternHandler.CASH_FROM_BANK_PATTERN.matcher(msg);
        if (cashFromBankMatcher.find()) {
            if (isGRBankCommand)
                return;
            this.unicacityAddon.fileService().data().addCashBalance(Integer.parseInt(cashFromBankMatcher.group(1)));
            return;
        }

        Matcher cashGetMatcher = PatternHandler.CASH_GET_PATTERN.matcher(msg);
        if (cashGetMatcher.find()) {
            this.unicacityAddon.fileService().data().addCashBalance(Integer.parseInt(cashGetMatcher.group(1)));
            return;
        }

        Matcher cashRemoveMatcher = PatternHandler.CASH_REMOVE_PATTERN.matcher(msg);
        if (cashRemoveMatcher.find()) {
            this.unicacityAddon.fileService().data().removeCashBalance(Integer.parseInt(cashRemoveMatcher.group(1)));
            return;
        }

        Matcher weaponTrainingStart = PatternHandler.WEAPON_TRAINING_START.matcher(msg);
        if (weaponTrainingStart.find()) {
            this.unicacityAddon.fileService().data().removeCashBalance(230);
            return;
        }

        Matcher cashStatsMatcher = PatternHandler.CASH_STATS_PATTERN.matcher(msg);
        if (cashStatsMatcher.find()) {
            this.unicacityAddon.fileService().data().setCashBalance(Integer.parseInt(cashStatsMatcher.group(1)));
            return;
        }

        Matcher atmInfoMatcher = PatternHandler.ATM_INFO_PATTERN.matcher(msg);
        if (atmInfoMatcher.find() && (RichTaxesCommand.isActive || ATMFillCommand.isActive)) {
            RichTaxesCommand.cashInATM = ATMFillCommand.cashInATM = Integer.parseInt(atmInfoMatcher.group(2));
            e.setCancelled(true);
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

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();
        isGRBankCommand = msg.startsWith("/grkasse get") || msg.startsWith("/grkasse drop");
    }
}