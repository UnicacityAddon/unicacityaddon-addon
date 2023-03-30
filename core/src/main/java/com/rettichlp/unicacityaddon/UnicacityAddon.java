package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.DefaultAddonPlayer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.nametags.AddonTag;
import com.rettichlp.unicacityaddon.base.nametags.DutyTag;
import com.rettichlp.unicacityaddon.base.nametags.FactionInfoTag;
import com.rettichlp.unicacityaddon.base.nametags.HouseBanTag;
import com.rettichlp.unicacityaddon.base.nametags.OutlawTag;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.ABuyCommand;
import com.rettichlp.unicacityaddon.commands.ActivityCommand;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.commands.CalculateCommand;
import com.rettichlp.unicacityaddon.commands.CancelCountdownCommand;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import com.rettichlp.unicacityaddon.commands.ClockCommand;
import com.rettichlp.unicacityaddon.commands.CoordlistCommand;
import com.rettichlp.unicacityaddon.commands.CountdownCommand;
import com.rettichlp.unicacityaddon.commands.DiscordCommand;
import com.rettichlp.unicacityaddon.commands.DutyCommand;
import com.rettichlp.unicacityaddon.commands.DyavolCommand;
import com.rettichlp.unicacityaddon.commands.GetGunCommand;
import com.rettichlp.unicacityaddon.commands.MaskInfoCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoAllCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoCommand;
import com.rettichlp.unicacityaddon.commands.NaviCommand;
import com.rettichlp.unicacityaddon.commands.NearestATMCommand;
import com.rettichlp.unicacityaddon.commands.NearestJobCommand;
import com.rettichlp.unicacityaddon.commands.ScreenCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownJailCommand;
import com.rettichlp.unicacityaddon.commands.SyncPlayerDataCommand;
import com.rettichlp.unicacityaddon.commands.TimerCommand;
import com.rettichlp.unicacityaddon.commands.TodoListCommand;
import com.rettichlp.unicacityaddon.commands.UpdateAddonCommand;
import com.rettichlp.unicacityaddon.commands.api.BlacklistReasonCommand;
import com.rettichlp.unicacityaddon.commands.api.BroadcastCommand;
import com.rettichlp.unicacityaddon.commands.api.HousebanCommand;
import com.rettichlp.unicacityaddon.commands.api.HousebanReasonCommand;
import com.rettichlp.unicacityaddon.commands.api.NaviPointCommand;
import com.rettichlp.unicacityaddon.commands.api.PlayerGroupCommand;
import com.rettichlp.unicacityaddon.commands.api.ReviveStatsCommand;
import com.rettichlp.unicacityaddon.commands.api.TokenCommand;
import com.rettichlp.unicacityaddon.commands.api.TopListCommand;
import com.rettichlp.unicacityaddon.commands.api.WantedReasonCommand;
import com.rettichlp.unicacityaddon.commands.api.YasinCommand;
import com.rettichlp.unicacityaddon.commands.faction.AEquipCommand;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.commands.faction.EquipListCommand;
import com.rettichlp.unicacityaddon.commands.faction.FactionInfoCommand;
import com.rettichlp.unicacityaddon.commands.faction.ReinforcementCommand;
import com.rettichlp.unicacityaddon.commands.faction.ServiceCountCommand;
import com.rettichlp.unicacityaddon.commands.faction.ShareLocationCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ACaptureCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ASetBlacklistCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.BlacklistInfoCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.DBankDropAllCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.EigenbedarfCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GaggedCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GiftEigenbedarfCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ModifyBlacklistCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.SchmarzmarktLocationsCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.SellDrugCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.DForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.FForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.SFForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptAnnehmenCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ASUCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ClearCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.KorruptionsrechnerCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ModifyWantedsCommand;
import com.rettichlp.unicacityaddon.commands.faction.terroristen.ExplosiveBeltCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseBankCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseBankDropGetAllCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseStorageCommand;
import com.rettichlp.unicacityaddon.commands.job.ADropMoneyCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ACallCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ASMSCommand;
import com.rettichlp.unicacityaddon.commands.mobile.BlockCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ReplyCommand;
import com.rettichlp.unicacityaddon.commands.mobile.StummCommand;
import com.rettichlp.unicacityaddon.commands.money.ATMFillCommand;
import com.rettichlp.unicacityaddon.commands.money.EinzahlenCommand;
import com.rettichlp.unicacityaddon.commands.money.ReichensteuerCommand;
import com.rettichlp.unicacityaddon.commands.supporter.PunishCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.ChannelActivityCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveHereCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveToCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSFindCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSJoinCommand;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.controller.OverlayMessageController;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import com.rettichlp.unicacityaddon.controller.TabListController;
import com.rettichlp.unicacityaddon.controller.TransportController;
import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import com.rettichlp.unicacityaddon.core.generated.DefaultReferenceStorage;
import com.rettichlp.unicacityaddon.hudwidgets.AmmunitionHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.BombHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.CarHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.EmergencyServiceHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.HearthHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.InventoryHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.JobHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.MoneyHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PayDayHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PlantHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.TimerHudWidget;
import com.rettichlp.unicacityaddon.listener.ABuyListener;
import com.rettichlp.unicacityaddon.listener.AccountListener;
import com.rettichlp.unicacityaddon.listener.BroadcastListener;
import com.rettichlp.unicacityaddon.listener.CarListener;
import com.rettichlp.unicacityaddon.listener.DrugListener;
import com.rettichlp.unicacityaddon.listener.EventListener;
import com.rettichlp.unicacityaddon.listener.FriendJoinListener;
import com.rettichlp.unicacityaddon.listener.HotkeyListener;
import com.rettichlp.unicacityaddon.listener.KarmaMessageListener;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import com.rettichlp.unicacityaddon.listener.MoneyListener;
import com.rettichlp.unicacityaddon.listener.NameTagListener;
import com.rettichlp.unicacityaddon.listener.NavigationListener;
import com.rettichlp.unicacityaddon.listener.ServerLoginListener;
import com.rettichlp.unicacityaddon.listener.TabListListener;
import com.rettichlp.unicacityaddon.listener.TickListener;
import com.rettichlp.unicacityaddon.listener.TimerListener;
import com.rettichlp.unicacityaddon.listener.WeaponClickListener;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogReceiveChatListener;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogSendChatListener;
import com.rettichlp.unicacityaddon.listener.faction.AEquipListener;
import com.rettichlp.unicacityaddon.listener.faction.AFbankEinzahlenListener;
import com.rettichlp.unicacityaddon.listener.faction.ContractListener;
import com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceListener;
import com.rettichlp.unicacityaddon.listener.faction.EquipListener;
import com.rettichlp.unicacityaddon.listener.faction.FDSFChatListener;
import com.rettichlp.unicacityaddon.listener.faction.FDoorListener;
import com.rettichlp.unicacityaddon.listener.faction.MemberInfoListener;
import com.rettichlp.unicacityaddon.listener.faction.ReinforcementListener;
import com.rettichlp.unicacityaddon.listener.faction.ShareLocationListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.BannerListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GaggedListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GiftEigenbedarfListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.PlantListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistModifyListener;
import com.rettichlp.unicacityaddon.listener.faction.kirche.PrayListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FireListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FirstAidListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveListener;
import com.rettichlp.unicacityaddon.listener.faction.state.HQMessageListener;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import com.rettichlp.unicacityaddon.listener.faction.terroristen.BombListener;
import com.rettichlp.unicacityaddon.listener.house.HouseDataListener;
import com.rettichlp.unicacityaddon.listener.house.HouseInteractionListener;
import com.rettichlp.unicacityaddon.listener.house.HouseRenterListener;
import com.rettichlp.unicacityaddon.listener.job.FishermanListener;
import com.rettichlp.unicacityaddon.listener.job.JobListener;
import com.rettichlp.unicacityaddon.listener.team.AdListener;
import com.rettichlp.unicacityaddon.listener.team.ReportListener;
import com.rettichlp.unicacityaddon.listener.teamspeak.WaitingRoomListener;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.logging.Logging;

/**
 * <h2>Hello Labymod addon reviewers and others,</h2>
 * <p>
 * I know the guidelines for publication but still use the user's session token and access the player's file system. In the following I will briefly explain why I do this:
 * <br><br>
 * <h3>Session token</h3>
 * An important function of the addon is to collect statistics and make data available to all players.
 * I use a private server for this. This provides data for:
 * <ul>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
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
 * This data can change constantly and can therefore not be entered statically in the code.<br>
 * <br>
 * For example, the number of revives should only be seen by medics, as well as the name of the
 * person who entered a house ban (advanced house ban view).<br>
 * For editing any data, a certain faction and rank in this faction is required.<br><br>
 * I can read the faction and rank from the Unicacity website (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>).<br>
 * But in order to be able to assign the faction information to a player, I need his UUID. I could pass these as
 * parameters, but you could mess that up by calling the endpoint with a different UUID that isn't your own.<br>
 * I needed a way to pass the UUID so that it cannot (so easily) be falsified. For this I use the session token, because
 * I can use it to read the UUID via the Mojang API and nobody else knows the session token.<br><br>
 * The session token is never saved ore logged. Only my specially generated token is saved in a database.<br>
 * If necessary I can give access to the server code and give an insight into all stored data.
 * <br><br>
 * <h3>File storage access</h3>
 * The addon uses data that is not important for all players. That's why they are not stored on my server via the API, but locally on the player's computer.<br>
 * This data contains, for example, the current account balance or the time until the next payday. This is used to be able to display Hudwidgets
 * immediately after joining the server and not to wait until a specific message is in the chat or to execute a command that supplies the said data.<br>
 * The data is saved in the Minecraft folder under an extra folder called <code>unicacityaddon</code>.
 *
 * @author RettichLP
 */
@AddonMain
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    public static final String VERSION = "2.0.0-alpha.1";
    public static UnicacityAddon ADDON;
    public static AddonPlayer PLAYER;
    public static Logging LOGGER;
    public static final Icon ICON = Icon.texture(ResourceLocation.create("unicacityaddon", "textures/uc.png")).resolution(64, 64);

    @Override
    public void load() {
        ADDON = this;
        PLAYER = new DefaultAddonPlayer(this);
        LOGGER = this.logger();
        FileManager.loadData();
        new Thread(TSClientQuery::getInstance).start();
    }

    @Override
    protected void enable() {
        this.registerSettingCategory();

        DefaultReferenceStorage referenceStorage = this.getReferenceStorageAccessor();

        this.registerListeners(referenceStorage);
        this.registerCommands(referenceStorage);
        this.registerHudWidgets();
        this.registerTags();
        this.logger().info("Enabled UnicacityAddon");

        TokenManager.createToken(this.labyAPI().minecraft().sessionAccessor().session());
        APIConverter.syncAll();
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    private void registerListeners(DefaultReferenceStorage referenceStorage) {
        TransportController transportController = referenceStorage.getTransportController();
        DeadBodyController deadBodyController = referenceStorage.getDeadBodyController();
        OverlayMessageController overlayMessageController = referenceStorage.getOverlayMessageController();
        ScreenshotController screenshotController = referenceStorage.getScreenshotController();
        TabListController tabListController = referenceStorage.getTabListController();
        WorldInteractionController worldInteractionController = referenceStorage.getWorldInteractionController();

        this.registerListener(new ABuyListener(this));
        this.registerListener(new AccountListener(this));
        this.registerListener(new AdListener(this));
        this.registerListener(new AEquipListener(this));
        this.registerListener(new AFbankEinzahlenListener(this));
        this.registerListener(new BannerListener(this, worldInteractionController));
        this.registerListener(new BlacklistListener(this));
        this.registerListener(new BlacklistModifyListener(this));
        this.registerListener(new BombListener(this));
        this.registerListener(new BroadcastListener(this));
        this.registerListener(new CarListener(this, transportController));
        this.registerListener(new ChatLogReceiveChatListener(this));
        this.registerListener(new ChatLogSendChatListener(this));
        this.registerListener(new ContractListener(this));
        this.registerListener(new DrugListener(this));
        this.registerListener(new EmergencyServiceListener(this));
        this.registerListener(new EquipListener(this));
        this.registerListener(new EventListener(this));
        this.registerListener(new FDoorListener(this));
        this.registerListener(new FDSFChatListener(this));
        this.registerListener(new FireListener(this));
        this.registerListener(new FirstAidListener(this));
        this.registerListener(new FishermanListener(this));
        this.registerListener(new FriendJoinListener(this));
        this.registerListener(new GaggedListener(this));
        this.registerListener(new GiftEigenbedarfListener(this));
        this.registerListener(new HotkeyListener(this, screenshotController));
        this.registerListener(new HouseDataListener(this));
        this.registerListener(new HouseInteractionListener(this, overlayMessageController));
        this.registerListener(new HouseRenterListener(this));
        this.registerListener(new HQMessageListener(this));
        this.registerListener(new JobListener(this, worldInteractionController));
        this.registerListener(new KarmaMessageListener(this));
        this.registerListener(new MedicationListener(this));
        this.registerListener(new MemberInfoListener(this));
        this.registerListener(new MobileListener(this));
        this.registerListener(new MoneyListener(this));
        this.registerListener(new NameTagListener(this));
        this.registerListener(new NavigationListener(this));
        this.registerListener(new PlantListener(this));
        this.registerListener(new PrayListener(this));
        this.registerListener(new ReinforcementListener(this));
        this.registerListener(new ReportListener(this));
        this.registerListener(new ReviveListener(this));
        this.registerListener(new ServerLoginListener(this));
        this.registerListener(new ShareLocationListener(this));
        this.registerListener(new TabListListener(this, tabListController));
        this.registerListener(new TickListener(this, screenshotController, deadBodyController, transportController));
        this.registerListener(new TimerListener(this));
        this.registerListener(new WaitingRoomListener(this));
        this.registerListener(new WantedListener(this));
        this.registerListener(new WeaponClickListener(this));
    }

    private void registerCommands(DefaultReferenceStorage referenceStorage) {
        OverlayMessageController overlayMessageController = referenceStorage.getOverlayMessageController();

        this.registerCommand(new ABuyCommand());
        this.registerCommand(new ActivityCommand());
        this.registerCommand(new ATMFillCommand());
        this.registerCommand(new BusCommand());
        this.registerCommand(new CalculateCommand());
        this.registerCommand(new CancelCountdownCommand());
        this.registerCommand(new ChatLogCommand());
        this.registerCommand(new ClearCommand());
        this.registerCommand(new ClockCommand());
        this.registerCommand(new CoordlistCommand());
        this.registerCommand(new CountdownCommand());
        this.registerCommand(new DiscordCommand());
        this.registerCommand(new DutyCommand());
        this.registerCommand(new DropDrugAllCommand());
        this.registerCommand(new DyavolCommand());
        this.registerCommand(new EinzahlenCommand());
        this.registerCommand(new GetGunCommand());
        this.registerCommand(new HouseBankDropGetAllCommand());
        this.registerCommand(new KorruptionsrechnerCommand());
        this.registerCommand(new MaskInfoCommand());
        this.registerCommand(new MemberInfoAllCommand());
        this.registerCommand(new MemberInfoCommand());
        this.registerCommand(new NaviCommand());
        this.registerCommand(new NearestATMCommand());
        this.registerCommand(new NearestJobCommand());
        this.registerCommand(new ReichensteuerCommand());
        this.registerCommand(new ReviveStatsCommand(overlayMessageController));
        this.registerCommand(new ScreenCommand());
        this.registerCommand(new ShutdownGraveyardCommand());
        this.registerCommand(new ShutdownJailCommand());
        this.registerCommand(new SyncPlayerDataCommand());
        this.registerCommand(new TimerCommand());
        this.registerCommand(new TodoListCommand());
        this.registerCommand(new UpdateAddonCommand());
        this.registerCommand(new YasinCommand());
        // api
        this.registerCommand(new BlacklistReasonCommand());
        this.registerCommand(new BroadcastCommand());
        this.registerCommand(new HousebanCommand());
        this.registerCommand(new HousebanReasonCommand());
        this.registerCommand(new NaviPointCommand());
        this.registerCommand(new PlayerGroupCommand());
        this.registerCommand(new TokenCommand());
        this.registerCommand(new TopListCommand());
        this.registerCommand(new WantedReasonCommand());
        // faction
        this.registerCommand(new AEquipCommand());
        this.registerCommand(new AFbankEinzahlenCommand());
        this.registerCommand(new EquipListCommand());
        this.registerCommand(new FactionInfoCommand());
        this.registerCommand(new ReinforcementCommand());
        this.registerCommand(new ServiceCountCommand());
        this.registerCommand(new ShareLocationCommand());
        // faction - badfaction
        this.registerCommand(new ACaptureCommand());
        this.registerCommand(new ASetBlacklistCommand());
        this.registerCommand(new BlacklistInfoCommand());
        this.registerCommand(new DBankDropAllCommand());
        this.registerCommand(new EigenbedarfCommand());
        this.registerCommand(new GaggedCommand());
        this.registerCommand(new GiftEigenbedarfCommand());
        this.registerCommand(new ModifyBlacklistCommand());
        this.registerCommand(new SchmarzmarktLocationsCommand());
        this.registerCommand(new SellDrugCommand());
        // faction - chat
        this.registerCommand(new DForceCommand());
        this.registerCommand(new FForceCommand());
        this.registerCommand(new SFForceCommand());
        // faction - polizei
        this.registerCommand(new ASUCommand());
        this.registerCommand(new ModifyWantedsCommand());
        // faction - rettungsdienst
        this.registerCommand(new ARezeptAnnehmenCommand());
        this.registerCommand(new ARezeptCommand());
        // faction - terroristen
        this.registerCommand(new ExplosiveBeltCommand());
        // house
        this.registerCommand(new HouseBankCommand());
        this.registerCommand(new HouseStorageCommand());
        // job
        this.registerCommand(new ADropMoneyCommand());
        // mobile
        this.registerCommand(new ACallCommand());
        this.registerCommand(new ASMSCommand());
        this.registerCommand(new BlockCommand());
        this.registerCommand(new ReplyCommand());
        this.registerCommand(new StummCommand());
        // supporter
        this.registerCommand(new PunishCommand());
        // teamspeak
        this.registerCommand(new ChannelActivityCommand());
        this.registerCommand(new MoveCommand());
        this.registerCommand(new MoveHereCommand());
        this.registerCommand(new MoveToCommand());
        this.registerCommand(new TSFindCommand());
        this.registerCommand(new TSJoinCommand());
    }

    private void registerHudWidgets() {
        HudWidgetRegistry registry = this.labyAPI().hudWidgetRegistry();
        registry.register(new AmmunitionHudWidget("ammunition", ICON));
        registry.register(new BombHudWidget("bomb", ICON));
        registry.register(new CarHudWidget("car", ICON));
        registry.register(new EmergencyServiceHudWidget("service", ICON));
        registry.register(new HearthHudWidget("hearth", ICON));
        registry.register(new InventoryHudWidget("inventory", ICON));
        registry.register(new JobHudWidget("job", ICON));
        registry.register(new MoneyHudWidget("money", ICON));
        registry.register(new PayDayHudWidget("payday", ICON));
        registry.register(new PlantHudWidget("plant", ICON));
        registry.register(new TimerHudWidget("timer", ICON));
    }

    private void registerTags() {
        TagRegistry tagRegistry = this.labyAPI().tagRegistry();
        tagRegistry.register(
                "unicacityaddon_addontag",
                PositionType.ABOVE_NAME,
                AddonTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_housebantag",
                PositionType.LEFT_TO_NAME,
                HouseBanTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_outlawtag",
                PositionType.LEFT_TO_NAME,
                OutlawTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_factioninfotag",
                PositionType.RIGHT_TO_NAME,
                FactionInfoTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_dutytag",
                PositionType.RIGHT_TO_NAME,
                DutyTag.create(this)
        );
    }

    public static boolean isUnicacity() {
        LabyAPI labyAPI = ADDON.labyAPI();
        if (labyAPI.minecraft().isIngame()) {
            ServerData serverData = labyAPI.serverController().getCurrentServerData();
            return serverData != null && serverData.address().matches("unicacity.de", 25565, true);
        }
        return false;
    }

    public static void debug(String debugMessage) {
        UnicacityAddon.PLAYER.sendMessage(Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("DEBUG").color(ColorCode.YELLOW).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance().space()
                .add(debugMessage)
                .createComponent());
    }
}