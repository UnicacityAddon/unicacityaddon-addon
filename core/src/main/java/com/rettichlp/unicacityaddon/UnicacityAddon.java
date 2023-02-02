package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.commands.ABuyCommand;
import com.rettichlp.unicacityaddon.commands.CalculateCommand;
import com.rettichlp.unicacityaddon.commands.CancelCountdownCommand;
import com.rettichlp.unicacityaddon.commands.ClockCommand;
import com.rettichlp.unicacityaddon.commands.CoordlistCommand;
import com.rettichlp.unicacityaddon.commands.CountdownCommand;
import com.rettichlp.unicacityaddon.commands.DiscordCommand;
import com.rettichlp.unicacityaddon.commands.DyavolCommand;
import com.rettichlp.unicacityaddon.commands.EinzahlenCommand;
import com.rettichlp.unicacityaddon.commands.ExamplePingCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoCommand;
import com.rettichlp.unicacityaddon.commands.NaviCommand;
import com.rettichlp.unicacityaddon.commands.NearestATMCommand;
import com.rettichlp.unicacityaddon.commands.NearestJobCommand;
import com.rettichlp.unicacityaddon.commands.ReichensteuerCommand;
import com.rettichlp.unicacityaddon.commands.ScreenCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownFriedhofCommand;
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
import com.rettichlp.unicacityaddon.events.FriendJoinEventHandler;
import com.rettichlp.unicacityaddon.events.TickEventHandler;
import com.rettichlp.unicacityaddon.events.faction.FDoorEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.FBIHackEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistModifyEventHandler;
import com.rettichlp.unicacityaddon.events.faction.polizei.HQMessageEventHandler;
import com.rettichlp.unicacityaddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.unicacityaddon.listener.ExampleGameTickListener;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.logging.Logging;

@AddonMain
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    public static final String VERSION = "2.0.0-dev";
    public static Minecraft MINECRAFT;
    public static Logging LOGGER;
    public static UnicacityAddon ADDON;
    public static UnicacityAddonConfiguration configuration;

    @Override
    protected void enable() {
        MINECRAFT = labyAPI().minecraft();
        LOGGER = this.logger();
        ADDON = this;
        configuration = this.configuration();

        // TODO: 16.12.2022 NOT FOR PROD
        this.registerCommand(new MoveToCommand());
        this.registerCommand(new ASetBlacklistCommand());
        this.registerCommand(new ClockCommand());
        this.registerCommand(new ModifyWantedsCommand());
        this.registerCommand(new MemberInfoCommand());
        this.registerCommand(new PunishCommand());
        this.registerCommand(new TokenCommand());
        this.registerCommand(new ARezeptCommand());
        this.registerCommand(new NearestJobCommand());
        this.registerCommand(new BroadcastCommand());
        this.registerCommand(new MoveCommand());
        this.registerCommand(new DyavolCommand());
        this.registerCommand(new AFbankEinzahlenCommand());
        this.registerCommand(new HouseBankCommand());
        this.registerCommand(new TSJoinCommand());
        this.registerCommand(new TimerCommand());
        this.registerCommand(new CalculateCommand());
        this.registerCommand(new NearestATMCommand());
        this.registerCommand(new CoordlistCommand());
        this.registerCommand(new CountdownCommand());
        this.registerCommand(new ShutdownJailCommand());
        this.registerCommand(new SellDrugCommand());
        this.registerCommand(new StummCommand());
        this.registerCommand(new ModifyBlacklistCommand());
        this.registerCommand(new ExplosiveBeltCommand());
        this.registerCommand(new TopListCommand());
        this.registerCommand(new AEquipCommand());
        this.registerCommand(new ACallCommand());
        this.registerCommand(new ReichensteuerCommand());
        this.registerCommand(new NaviCommand());
        this.registerCommand(new DForceCommand());
        this.registerCommand(new HousebanReasonCommand());
        this.registerCommand(new DiscordCommand());
        this.registerCommand(new HouseStorageCommand());
        this.registerCommand(new ShareLocationCommand());
        this.registerCommand(new EinzahlenCommand());
        this.registerCommand(new YasinCommand());
        this.registerCommand(new PlayerGroupCommand());
        this.registerCommand(new ServiceCountCommand());
        this.registerCommand(new EquipListCommand());
        this.registerCommand(new ASMSCommand());
        this.registerCommand(new TodoListCommand());
//        this.registerCommand(new ChatLogCommand());
        this.registerCommand(new BlacklistInfoCommand());
        this.registerCommand(new SyncPlayerDataCommand());
        this.registerCommand(new ReinforcementCommand());
        this.registerCommand(new MoveHereCommand());
        this.registerCommand(new FForceCommand());
        this.registerCommand(new ARezeptAnnehmenCommand());
        this.registerCommand(new ShutdownFriedhofCommand());
        this.registerCommand(new ChannelActivityCommand());
        this.registerCommand(new ASUCommand());
        this.registerCommand(new ScreenCommand());
        this.registerCommand(new GiftEigenbedarfCommand());
        this.registerCommand(new ABuyCommand());
        this.registerCommand(new WantedReasonCommand());
        this.registerCommand(new SFForceCommand());
        this.registerCommand(new ADropMoneyCommand());
        this.registerCommand(new HousebanCommand());
        this.registerCommand(new FactionInfoCommand());
        this.registerCommand(new ReplyCommand());
        this.registerCommand(new CancelCountdownCommand());
        this.registerCommand(new UpdateAddonCommand());
        this.registerCommand(new EigenbedarfCommand());
        this.registerCommand(new BlockCommand());
        this.registerCommand(new TSFindCommand());
        this.registerCommand(new NaviPointCommand());
        this.registerCommand(new BlacklistReasonCommand());
        this.registerCommand(new GaggedCommand());
        this.registerCommand(new SchmarzmarktLocationsCommand());
        this.registerCommand(new ACaptureCommand());
        this.registerListener(new BlacklistModifyEventHandler(this));
        this.registerListener(new TickEventHandler(this));
        this.registerListener(new FDoorEventHandler(this));
        this.registerListener(new FBIHackEventHandler(this));
        this.registerListener(new WantedEventHandler(this));
//        this.registerListener(new ChatLogReceiveChatEventHandler(this));
        this.registerListener(new HQMessageEventHandler(this));
        this.registerListener(new FriendJoinEventHandler(this));
        this.registerListener(new BlacklistEventHandler(this));
//        this.registerListener(new HouseDataEventHandler(this));
//        this.registerListener(new HotkeyEventHandler(this));
//        this.registerListener(new DeathsKillsEventHandler(this));
//        this.registerListener(new KarmaMessageEventHandler(this));
//        this.registerListener(new ReportEventHandler(this));
//        this.registerListener(new GiftEigenbedarfEventHandler(this));
//        this.registerListener(new DutyEventHandler(this));
//        this.registerListener(new MedicationEventHandler(this));
//        this.registerListener(new EquipEventHandler(this));
//        this.registerListener(new ServiceMessageEventHandler(this));
//        this.registerListener(new BombTimerEventHandler(this));
//        this.registerListener(new WeaponClickEventHandler(this));
//        this.registerListener(new WaitingRoomEventHandler(this));
//        this.registerListener(new AccountEventHandler(this));
//        this.registerListener(new ChatLogSendChatEventHandler(this));
//        this.registerListener(new HouseRenterEventHandler(this));
//        this.registerListener(new FireEventHandler(this));
//        this.registerListener(new FactionInfoEventHandler(this));
//        this.registerListener(new ReviveEventHandler(this));
//        this.registerListener(new MoneyEventHandler(this));
//        this.registerListener(new AFbankEinzahlenEventHandler(this));
//        this.registerListener(new ShareLocationEventHandler(this));
//        this.registerListener(new GaggedEventHandler(this));
//        this.registerListener(new PlantEventHandler(this));
//        this.registerListener(new ReinforcementEventHandler(this));
//        this.registerListener(new JobEventHandler(this));
//        this.registerListener(new DBankMessageEventHandler(this));
//        this.registerListener(new HouseInteractionEventHandler(this));
//        this.registerListener(new MobileEventHandler(this));
//        this.registerListener(new ContractEventHandler(this));
//        this.registerListener(new FDSFChatEventHandler(this));
//        this.registerListener(new NavigationEventHandler(this));
//        this.registerListener(new FirstAidEventHandler(this));
//        this.registerListener(new FishermanEventHandler(this));
//        this.registerListener(new NameTagEventHandler(this));
//        this.registerListener(new ABuyEventHandler(this));
//        this.registerListener(new AEquipEventHandler(this));
//        this.registerListener(new CarEventHandler(this));
//        this.registerListener(new EmergencyServiceEventHandler(this));
//        this.registerListener(new ShutDownEventHandler(this));

        //        UpdateUtils.modFile = e.getSourceFile();
        //        asmDataTable = e.getAsmData();
        //        CommandRegistry.register();
        //        EventRegistry.register();

        this.registerSettingCategory();
        //        HudWidgetRegistry.register();

        this.registerListener(new ExampleGameTickListener(this));
        this.registerCommand(new ExamplePingCommand());

        BroadcastChecker.start();
        LOGGER.info("Started BroadcastChecker");
        TokenManager.createToken();
        LOGGER.info("Created API Token");
        Syncer.syncAll();

        new Thread(TSClientQuery::getInstance).start();
        LOGGER.info("Started TSClientQuery");

        //        KeyBindRegistry.registerKeyBinds();
        FileManager.loadData();


        LOGGER.info("Enabled the Addon");
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    public static boolean isUnicacity() {
        if (MINECRAFT.clientWorld() == null)
            return false;

        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null)
            return false;

        return serverData.address().matches("unicacity", 25565, true);
    }
}