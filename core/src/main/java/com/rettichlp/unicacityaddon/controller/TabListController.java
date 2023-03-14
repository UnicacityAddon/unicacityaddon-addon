package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class TabListController {

    public abstract void orderTabList();

    public abstract String tabListName(NetworkPlayerInfo networkPlayerInfo);
}
