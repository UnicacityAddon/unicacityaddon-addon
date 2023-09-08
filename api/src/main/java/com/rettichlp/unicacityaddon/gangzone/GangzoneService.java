package com.rettichlp.unicacityaddon.gangzone;

import net.labymod.api.reference.annotation.Referenceable;

import java.util.Collection;

@Referenceable
public interface GangzoneService {

    Collection<Gangzone> getAllGangzones();
}
