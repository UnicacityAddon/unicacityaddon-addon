package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.FactionService;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.services.NameTagService;
import com.rettichlp.unicacityaddon.base.services.NavigationService;
import com.rettichlp.unicacityaddon.base.services.WebService;

public class Services {

    private final FactionService factionService;
    private final FileService fileService;
    private final NameTagService nametagService;
    private final NavigationService navigationService;
    private final WebService webService;

    public Services(UnicacityAddon unicacityAddon) {
        this.factionService = new FactionService(unicacityAddon);
        this.fileService = new FileService(unicacityAddon);
        this.nametagService = new NameTagService(unicacityAddon);
        this.navigationService = new NavigationService(unicacityAddon);
        this.webService = new WebService(unicacityAddon);
    }

    public FactionService factionService() {
        return factionService;
    }

    public FileService fileService() {
        return fileService;
    }

    public NameTagService nametagService() {
        return nametagService;
    }

    public NavigationService navigationService() {
        return navigationService;
    }

    public WebService webService() {
        return webService;
    }
}
