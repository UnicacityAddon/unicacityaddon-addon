package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.DefaultAddonPlayer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
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
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.commands.CalculateCommand;
import com.rettichlp.unicacityaddon.commands.CancelCountdownCommand;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import com.rettichlp.unicacityaddon.commands.ClockCommand;
import com.rettichlp.unicacityaddon.commands.CoordlistCommand;
import com.rettichlp.unicacityaddon.commands.CountdownCommand;
import com.rettichlp.unicacityaddon.commands.DiscordCommand;
import com.rettichlp.unicacityaddon.commands.DyavolCommand;
import com.rettichlp.unicacityaddon.commands.EinzahlenCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoCommand;
import com.rettichlp.unicacityaddon.commands.NaviCommand;
import com.rettichlp.unicacityaddon.commands.NearestATMCommand;
import com.rettichlp.unicacityaddon.commands.NearestJobCommand;
import com.rettichlp.unicacityaddon.commands.ReichensteuerCommand;
import com.rettichlp.unicacityaddon.commands.ScreenCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownJailCommand;
import com.rettichlp.unicacityaddon.commands.SyncPlayerDataCommand;
import com.rettichlp.unicacityaddon.commands.TimerCommand;
import com.rettichlp.unicacityaddon.commands.TodoListCommand;
import com.rettichlp.unicacityaddon.commands.UpdateAddonCommand;
import com.rettichlp.unicacityaddon.commands.YasinCommand;
import com.rettichlp.unicacityaddon.commands.api.BlacklistReasonCommand;
import com.rettichlp.unicacityaddon.commands.api.BroadcastCommand;
import com.rettichlp.unicacityaddon.commands.api.HousebanCommand;
import com.rettichlp.unicacityaddon.commands.api.HousebanReasonCommand;
import com.rettichlp.unicacityaddon.commands.api.NaviPointCommand;
import com.rettichlp.unicacityaddon.commands.api.PlayerGroupCommand;
import com.rettichlp.unicacityaddon.commands.api.TokenCommand;
import com.rettichlp.unicacityaddon.commands.api.TopListCommand;
import com.rettichlp.unicacityaddon.commands.api.WantedReasonCommand;
import com.rettichlp.unicacityaddon.commands.faction.AEquipCommand;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
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
import com.rettichlp.unicacityaddon.commands.faction.polizei.ASUCommand;
import com.rettichlp.unicacityaddon.commands.faction.polizei.ModifyWantedsCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptAnnehmenCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptCommand;
import com.rettichlp.unicacityaddon.commands.faction.terroristen.ExplosiveBeltCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseBankCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseStorageCommand;
import com.rettichlp.unicacityaddon.commands.job.ADropMoneyCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ACallCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ASMSCommand;
import com.rettichlp.unicacityaddon.commands.mobile.BlockCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ReplyCommand;
import com.rettichlp.unicacityaddon.commands.mobile.StummCommand;
import com.rettichlp.unicacityaddon.commands.supporter.PunishCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.ChannelActivityCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveHereCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveToCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSFindCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSJoinCommand;
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
import com.rettichlp.unicacityaddon.listener.ABuyEventHandler;
import com.rettichlp.unicacityaddon.listener.AccountEventHandler;
import com.rettichlp.unicacityaddon.listener.CarEventHandler;
import com.rettichlp.unicacityaddon.listener.DeathsKillsEventHandler;
import com.rettichlp.unicacityaddon.listener.FriendJoinEventHandler;
import com.rettichlp.unicacityaddon.listener.HotkeyEventHandler;
import com.rettichlp.unicacityaddon.listener.KarmaMessageEventHandler;
import com.rettichlp.unicacityaddon.listener.MobileEventHandler;
import com.rettichlp.unicacityaddon.listener.MoneyEventHandler;
import com.rettichlp.unicacityaddon.listener.NameTagEventHandler;
import com.rettichlp.unicacityaddon.listener.NavigationEventHandler;
import com.rettichlp.unicacityaddon.listener.ServerLoginEventHandler;
import com.rettichlp.unicacityaddon.listener.TabListEventHandler;
import com.rettichlp.unicacityaddon.listener.TickEventHandler;
import com.rettichlp.unicacityaddon.listener.TimerEventHandler;
import com.rettichlp.unicacityaddon.listener.WeaponClickEventHandler;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogReceiveChatEventHandler;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogSendChatEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.AEquipEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.AFbankEinzahlenEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.ContractEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.EquipEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.FDSFChatEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.FDoorEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.FactionInfoEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.ReinforcementEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.ShareLocationEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.BannerEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.DBankMessageEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.DrugInteractionEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GaggedEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GiftEigenbedarfEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.PlantEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistModifyEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.polizei.HQMessageEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.polizei.WantedEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FireEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FirstAidEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ServiceMessageEventHandler;
import com.rettichlp.unicacityaddon.listener.faction.terroristen.BombTimerEventHandler;
import com.rettichlp.unicacityaddon.listener.house.HouseDataEventHandler;
import com.rettichlp.unicacityaddon.listener.house.HouseInteractionEventHandler;
import com.rettichlp.unicacityaddon.listener.house.HouseRenterEventHandler;
import com.rettichlp.unicacityaddon.listener.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.listener.job.JobEventHandler;
import com.rettichlp.unicacityaddon.listener.team.ReportEventHandler;
import com.rettichlp.unicacityaddon.listener.teamspeak.WaitingRoomEventHandler;
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

@AddonMain
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    public static final String VERSION = "2.0.0-alpha";
    public static UnicacityAddon ADDON;
    public static AddonPlayer PLAYER;
    public static Logging LOGGER;

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

        this.registerListeners();
        this.registerCommands();
        this.registerHudWidgets();
        this.registerTags();

        this.logger().info("Enabled UnicacityAddon");

        TokenManager.createToken(this.labyAPI().minecraft().sessionAccessor().session());
        this.logger().info("Created Token");

        BroadcastChecker.start();
        this.logger().info("Started BroadcastChecker");

        APIConverter.syncAll();
        this.logger().info("Started Sync process");
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    private void registerListeners() {
        this.registerListener(new ABuyEventHandler(this));
        this.registerListener(new AccountEventHandler(this));
        this.registerListener(new CarEventHandler(this));
        this.registerListener(new DeathsKillsEventHandler(this));
        this.registerListener(new FriendJoinEventHandler(this));
        this.registerListener(new HotkeyEventHandler(this));
        this.registerListener(new KarmaMessageEventHandler(this));
        this.registerListener(new MobileEventHandler(this));
        this.registerListener(new MoneyEventHandler(this));
        this.registerListener(new NameTagEventHandler(this));
        this.registerListener(new NavigationEventHandler(this));
        this.registerListener(new ServerLoginEventHandler(this));
        this.registerListener(new TabListEventHandler(this));
        this.registerListener(new TickEventHandler(this));
        this.registerListener(new TimerEventHandler(this));
        this.registerListener(new WeaponClickEventHandler(this));
        // chatlog
        this.registerListener(new ChatLogReceiveChatEventHandler(this));
        this.registerListener(new ChatLogSendChatEventHandler(this));
        // faction
        this.registerListener(new AEquipEventHandler(this));
        this.registerListener(new AFbankEinzahlenEventHandler(this));
        this.registerListener(new ContractEventHandler(this));
        this.registerListener(new EmergencyServiceEventHandler(this));
        this.registerListener(new EquipEventHandler(this));
        this.registerListener(new FDSFChatEventHandler(this));
        this.registerListener(new FDoorEventHandler(this));
        this.registerListener(new FactionInfoEventHandler(this));
        this.registerListener(new ReinforcementEventHandler(this));
        this.registerListener(new ShareLocationEventHandler(this));
        // faction - badfaction
        this.registerListener(new BannerEventHandler(this));
        this.registerListener(new DBankMessageEventHandler(this));
        this.registerListener(new DrugInteractionEventHandler(this));
        this.registerListener(new GaggedEventHandler(this));
        this.registerListener(new GiftEigenbedarfEventHandler(this));
        this.registerListener(new PlantEventHandler(this));
        // faction - badfaction - blacklist
        this.registerListener(new BlacklistEventHandler(this));
        this.registerListener(new BlacklistModifyEventHandler(this));
        // faction - polizei
        this.registerListener(new HQMessageEventHandler(this));
        this.registerListener(new WantedEventHandler(this));
        // faction - rettungsdienst
        this.registerListener(new FireEventHandler(this));
        this.registerListener(new FirstAidEventHandler(this));
        this.registerListener(new MedicationEventHandler(this));
        this.registerListener(new ReviveEventHandler(this));
        this.registerListener(new ServiceMessageEventHandler(this));
        // faction - terroristen
        this.registerListener(new BombTimerEventHandler(this));
        // house
        this.registerListener(new HouseDataEventHandler(this));
        this.registerListener(new HouseInteractionEventHandler(this));
        this.registerListener(new HouseRenterEventHandler(this));
        // job
        this.registerListener(new FishermanEventHandler(this));
        this.registerListener(new JobEventHandler(this));
        // team
        this.registerListener(new ReportEventHandler(this));
        // teamspeak
        this.registerListener(new WaitingRoomEventHandler(this));
    }

    private void registerCommands() {
        this.registerCommand(new ABuyCommand());
        this.registerCommand(new BusCommand());
        this.registerCommand(new CalculateCommand());
        this.registerCommand(new CancelCountdownCommand());
        this.registerCommand(new ChatLogCommand());
        this.registerCommand(new ClockCommand());
        this.registerCommand(new CoordlistCommand());
        this.registerCommand(new CountdownCommand());
        this.registerCommand(new DiscordCommand());
        this.registerCommand(new DyavolCommand());
        this.registerCommand(new EinzahlenCommand());
        this.registerCommand(new MemberInfoCommand());
        this.registerCommand(new NaviCommand());
        this.registerCommand(new NearestATMCommand());
        this.registerCommand(new NearestJobCommand());
        this.registerCommand(new ReichensteuerCommand());
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
        Icon hudIcon = Icon.texture(ResourceLocation.create("unicacityaddon", "textures/uc.png")).resolution(64, 64);
        HudWidgetRegistry registry = this.labyAPI().hudWidgetRegistry();
        registry.register(new AmmunitionHudWidget("ammunition", hudIcon));
        registry.register(new BombHudWidget("bomb", hudIcon));
        registry.register(new CarHudWidget("car", hudIcon));
        registry.register(new EmergencyServiceHudWidget("service", hudIcon));
        registry.register(new HearthHudWidget("hearth", hudIcon));
        registry.register(new InventoryHudWidget("inventory", hudIcon));
        registry.register(new JobHudWidget("job", hudIcon));
        registry.register(new MoneyHudWidget("money", hudIcon));
        registry.register(new PayDayHudWidget("payday", hudIcon));
        registry.register(new PlantHudWidget("plant", hudIcon));
        registry.register(new TimerHudWidget("timer", hudIcon));
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