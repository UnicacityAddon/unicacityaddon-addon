package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.DefaultAddonPlayer;
import com.rettichlp.unicacityaddon.base.Services;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.io.api.API;
import com.rettichlp.unicacityaddon.base.registry.Registry;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.controller.GuiController;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import com.rettichlp.unicacityaddon.controller.SoundController;
import com.rettichlp.unicacityaddon.controller.TabListController;
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
 * <h3>Session token ({@link API})</h3>
 * An important function of the addon is to collect statistics and make data available to all players. In order to offer
 * a high level of user-friendliness, an update should not have to be created due to small changes. That's why I use an
 * API through which I make some data available. I use a private server for this. This provides data for:
 * <ul>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
 *     <li>blackmarket locations <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blackmarket">API</a></li>
 *     <li>broadcasts <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/broadcast/queue">API</a></li>
 *     <li>events <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event">API</a></li>
 *     <li>house bans <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false">API</a> (unauthorized for <code>advanced=true</code>)</li>
 *     <li>house ban reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason">API</a></li>
 *     <li>users <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users">API</a></li>
 *     <li>navi points <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint">API</a></li>
 *     <li>revives <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive">API</a> (unauthorized)</li>
 *     <li>statistics <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP">API</a></li>
 *     <li>wanted reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason">API</a></li>
 *     <li>yasin <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin">API</a></li>
 * </ul>
 * This data can change constantly and can therefore not be entered statically in the code.
 * <p>
 * Why i need the session token for this? For example, the number of revives should only be seen by medics, as well as
 * the name of the person who entered a house ban (advanced house ban view). For editing any data, a certain faction and
 * rank in this faction is required.
 * <p>
 * I can read the faction and rank from the Unicacity website
 * (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>). But in order to be able to assign
 * the faction information to a player, I need his UUID. I could pass these as parameters in the api call, but you could
 * mess that up by calling the endpoint with a different UUID that isn't your own. I needed a way to pass the UUID so
 * that it cannot (so easily) be falsified. For this I use the session token, because I can use it to read the UUID via
 * the Mojang API and nobody else knows the session token.
 * <p>
 * A more detailed overview of how the authorization works can be found
 * <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/autorisierung/">here</a> and an overview of all data I
 * store can be found <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/daten-und-speicherung/">here</a>.
 * The session token is never saved ore logged. Only my specially generated token is saved in a database. If necessary I
 * can give access to the server code and give an insight into all stored data.
 * <p>
 * <h3>File storage access ({@link FileService})</h3>
 * The addon uses data that is not important for all players. That's why they are not stored on my server via the API,
 * but locally on the player's computer. This data contains, for example, the current account balance or the time until
 * the next payday. This is used to be able to display Hud-Widgets immediately after joining the server and not to wait
 * until a specific message is in the chat or to execute a command that supplies the said data. The data is saved in the
 * Minecraft folder under an extra folder called <code>unicacityaddon</code>.
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
    private Services services;
    private API api;
    private TeamSpeakAPI teamSpeakAPI;
    private Registry registry;

    @Override
    public void load() {
        this.player = new DefaultAddonPlayer(this);
        this.services = new Services(this);
        this.api = new API(this);
        this.teamSpeakAPI = new TeamSpeakAPI(this);
        this.registry = new Registry(this);

        this.logger().info("Loaded UnicacityAddon");
    }

    @Override
    protected void enable() {
        this.api.sync(this.player);
        this.registerSettingCategory();
        this.registry.registerTags();
        this.registry.registerHudWidgets();
        this.registry.registerListeners();
        this.registry.registerCommands();

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

    public ScreenshotController screenshotController() {
        return controller().getScreenshotController();
    }

    public SoundController soundController() {
        return controller().getSoundController();
    }

    public TabListController tabListController() {
        return controller().getTabListController();
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