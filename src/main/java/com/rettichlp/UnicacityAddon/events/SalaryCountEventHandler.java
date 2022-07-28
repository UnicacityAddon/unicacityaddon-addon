package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.JobSalaryModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class SalaryCountEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();
        Matcher JOB_SALARY_MATCHER = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);

        if (PatternHandler.JOB_SALARY_PATTERN.matcher(msg).find())
            JobSalaryModule.currentSalary += Integer.parseInt(JOB_SALARY_MATCHER.group(1));

        if (msg.equals("======== PayDay ========"))
            JobSalaryModule.resetSalary();

        return false;
    }
}
