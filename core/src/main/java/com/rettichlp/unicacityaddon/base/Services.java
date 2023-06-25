package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.FactionService;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.services.NameTagService;
import com.rettichlp.unicacityaddon.base.services.NavigationService;
import com.rettichlp.unicacityaddon.base.services.UtilService;
import com.rettichlp.unicacityaddon.base.services.WebService;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author RettichLP
 */
@Accessors(fluent = true)
@Getter
public class Services {

    private final FactionService faction;
    private final FileService file;
    private final NameTagService nametag;
    private final NavigationService navigation;
    private final UtilService util;
    private final WebService web;

    public Services(UnicacityAddon unicacityAddon) {
        this.faction = new FactionService(unicacityAddon);
        this.file = new FileService(unicacityAddon);
        this.nametag = new NameTagService(unicacityAddon);
        this.navigation = new NavigationService(unicacityAddon);
        this.util = new UtilService(unicacityAddon);
        this.web = new WebService(unicacityAddon);
    }
}
