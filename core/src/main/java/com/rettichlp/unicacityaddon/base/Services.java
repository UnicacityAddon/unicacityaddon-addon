package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.FactionService;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.services.NameTagService;
import com.rettichlp.unicacityaddon.base.services.NavigationService;
import com.rettichlp.unicacityaddon.base.services.UtilService;
import com.rettichlp.unicacityaddon.base.services.WebService;

public class Services {

    private final FactionService factionService;
    private final FileService fileService;
    private final NameTagService nametagService;
    private final NavigationService navigationService;
    private final UtilService utilService;
    private final WebService webService;

    public Services(UnicacityAddon unicacityAddon) {
        this.factionService = new FactionService(unicacityAddon);
        this.fileService = new FileService(unicacityAddon);
        this.nametagService = new NameTagService(unicacityAddon);
        this.navigationService = new NavigationService(unicacityAddon);
        this.utilService = new UtilService(unicacityAddon);
        this.webService = new WebService(unicacityAddon);
    }

    public FactionService faction() {
        return factionService;
    }

    public FileService file() {
        return fileService;
    }

    public NameTagService nametag() {
        return nametagService;
    }

    public NavigationService navigation() {
        return navigationService;
    }

    public UtilService util() {
        return utilService;
    }

    public WebService web() {
        return webService;
    }
}
