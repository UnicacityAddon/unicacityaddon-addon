package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class TabListController {

    public abstract void orderTabList(Collection<NetworkPlayerInfo> networkPlayerInfos);
}
