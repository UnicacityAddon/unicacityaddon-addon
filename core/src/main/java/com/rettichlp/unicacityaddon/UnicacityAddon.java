package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.DefaultAddonPlayer;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.io.api.API;
import com.rettichlp.unicacityaddon.base.registry.Registry;
import com.rettichlp.unicacityaddon.base.services.FactionService;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.services.NameTagService;
import com.rettichlp.unicacityaddon.base.services.NavigationService;
import com.rettichlp.unicacityaddon.base.services.UtilService;
import com.rettichlp.unicacityaddon.base.services.WebService;
import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.controller.GuiController;
import com.rettichlp.unicacityaddon.controller.PlayerListController;
import com.rettichlp.unicacityaddon.controller.RenderController;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import com.rettichlp.unicacityaddon.controller.SoundController;
import com.rettichlp.unicacityaddon.controller.TransportController;
import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import com.rettichlp.unicacityaddon.core.generated.DefaultReferenceStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

/**
 * <h2>Hello Labymod addon reviewers and others,</h2>
 * <p>
 * I know the guidelines for publication. Nevertheless, I use the session token of users, access the file system and
 * only return messages in German. Below I describe why I do this.
 * <p>
 * <h3>Session token</h3>
 * An important addon function is to collect statistics and make data available to all players. To ensure
 * user-friendliness, an update should not always have to be created for changes to content-related data. I utilize an
 * API to provide data, leveraging a private server. Data is available for the following purposes:
 * <ul>
 *     <li>activity check <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/activitycheck/LEMILIEU/add">API</a> (unauthorized)</li>
 *     <li>auto nc <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/autonc">API</a> (unauthorized)</li>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
 *     <li>blackmarket locations <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blackmarket">API</a></li>
 *     <li>events <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event">API</a></li>
 *     <li>house bans <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false">API</a> (unauthorized for <code>advanced=true</code>)</li>
 *     <li>house ban reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason">API</a></li>
 *     <li>users <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users">API</a></li>
 *     <li>navi points <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint">API</a></li>
 *     <li>revives <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive">API</a> (unauthorized)</li>
 *     <li>roleplay <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/roleplay">API</a></li>
 *     <li>statistics <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP">API</a></li>
 *     <li>wanted reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason">API</a></li>
 *     <li>yasin <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin">API</a></li>
 * </ul>
 * This data can change constantly. Therefore, it cannot be statically entered into the code.
 * <p>
 * Why do I need the LabyConnect session token for this? For example, the number of revives or the player name who
 * entered a house ban (advanced house ban view) should only be visible to medics. For editing any data, a faction and
 * rank in this faction is necessary.
 * <p>
 * I can read the faction and rank from the Unicacity website
 * (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>). But to assign the faction
 * information to a player, I need his UUID. I could pass these as parameters in the API call, but you could mess that
 * up by calling the endpoint with a different UUID from your own. The LabyMod session token contains the UUID. On the
 * server, I verify the session token with a public key. If the verification passes, I store the player UUID with my
 * generated API token.
 * <p>
 * An additional overview of the authorization can be found
 * <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/autorisierung/">here</a>. An overview of all stored
 * data can be found <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/daten-und-speicherung/">here</a>.
 * The LabyConnect session token is never saved or logged. Only my specially generated token is stored in a database. If
 * necessary, I can provide access to the server code and an insight into all stored data.
 * <p>
 * <h3>File storage access ({@link FileService})</h3>
 * The addon uses data that is not important for all players. That's why they are not stored on my server via the API,
 * but locally on the player's computer. This data contains, for example, the current account balance or the time until
 * the next payday. This is used to be able to display Hud-Widgets immediately after joining the server and not to wait
 * until a specific message is in the chat or to execute a command that supplies the said data. The data is saved in the
 * Minecraft folder under an extra folder called <code>unicacityAddon</code>.
 * <p>
 * <h3>Language file</h3>
 * The addon is for a German speaking server. Thus, all messages that are sent in response to commands or events are
 * also in German. A random survey has shown that many players play in English, but still want German messages in the
 * chat. Only the configuration is also available in English.
 *
 * @author RettichLP
 */
@AddonMain
@Accessors(fluent = true)
@Getter
@NoArgsConstructor
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    private AddonPlayer player;
    private API api;
    private TeamSpeakAPI teamSpeakAPI;
    private Registry registry;

    private FactionService factionService;
    private FileService fileService;
    private NameTagService nameTagService;
    private NavigationService navigationService;
    private UtilService utilService;
    private WebService webService;

    @Override
    public void load() {
        this.player = new DefaultAddonPlayer(this);
        this.api = new API(this);
        this.teamSpeakAPI = new TeamSpeakAPI(this);
        this.registry = new Registry(this);

        this.factionService = new FactionService(this);
        this.fileService = new FileService(this);
        this.nameTagService = new NameTagService(this);
        this.navigationService = new NavigationService(this);
        this.utilService = new UtilService(this);
        this.webService = new WebService(this);

        this.logger().info("Loaded UnicacityAddon");
    }

    @Override
    protected void enable() {
        this.registerSettingCategory();
        this.registry.registerBadges();
        this.registry.registerTags();
        this.registry.registerHudWidgets();
        this.registry.registerListeners();
        this.registry.registerCommands();
        this.registry.registerGangzones();

        new Thread(this.teamSpeakAPI::initialize).start();

        this.logger().info("Enabled UnicacityAddon");
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    public GuiController guiController() {
        return controller().getGuiController();
    }

    public DeadBodyController deadBodyController() {
        return controller().getDeadBodyController();
    }

    public PlayerListController playerListController() {
        return controller().getPlayerListController();
    }

    public RenderController renderController() {
        return controller().getRenderController();
    }

    public ScreenshotController screenshotController() {
        return controller().getScreenshotController();
    }

    public SoundController soundController() {
        return controller().getSoundController();
    }

    public TransportController transportController() {
        return controller().getTransportController();
    }

    public WorldInteractionController worldInteractionController() {
        return controller().getWorldInteractionController();
    }

    private DefaultReferenceStorage controller() {
        return this.referenceStorageAccessor();
    }
}