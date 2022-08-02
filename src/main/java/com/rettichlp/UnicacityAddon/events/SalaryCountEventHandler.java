package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
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
        String msg = e.getMessage().getUnformattedText();
        Matcher jobSalaryMatcher = PatternHandler.JOB_SALARY_PATTERN.matcher(msg);

        if (jobSalaryMatcher.find())
            JobSalaryModule.currentSalary += Integer.parseInt(jobSalaryMatcher.group(1));

        if (msg.equals("======== PayDay ========")) JobSalaryModule.resetSalary();

        return false;
    }
}
