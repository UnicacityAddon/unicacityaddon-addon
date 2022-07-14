package com.rettichlp.UnicacityAddon.base.command;

import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;

import java.util.List;

/**
 * @author RettichLP
 */
public interface UnicacityCommand {

    boolean onCommand(UPlayer p, List<String> args);
}