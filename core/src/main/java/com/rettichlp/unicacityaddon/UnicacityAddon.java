package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
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
import com.rettichlp.unicacityaddon.events.ABuyEventHandler;
import com.rettichlp.unicacityaddon.events.AccountEventHandler;
import com.rettichlp.unicacityaddon.events.CarEventHandler;
import com.rettichlp.unicacityaddon.events.DeathsKillsEventHandler;
import com.rettichlp.unicacityaddon.events.FriendJoinEventHandler;
import com.rettichlp.unicacityaddon.events.HotkeyEventHandler;
import com.rettichlp.unicacityaddon.events.KarmaMessageEventHandler;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import com.rettichlp.unicacityaddon.events.MoneyEventHandler;
import com.rettichlp.unicacityaddon.events.NameTagEventHandler;
import com.rettichlp.unicacityaddon.events.NavigationEventHandler;
import com.rettichlp.unicacityaddon.events.RenderTagEventHandler;
import com.rettichlp.unicacityaddon.events.TabListEventHandler;
import com.rettichlp.unicacityaddon.events.TickEventHandler;
import com.rettichlp.unicacityaddon.events.TimerEventHandler;
import com.rettichlp.unicacityaddon.events.WeaponClickEventHandler;
import com.rettichlp.unicacityaddon.events.chatlog.ChatLogReceiveChatEventHandler;
import com.rettichlp.unicacityaddon.events.chatlog.ChatLogSendChatEventHandler;
import com.rettichlp.unicacityaddon.events.faction.AEquipEventHandler;
import com.rettichlp.unicacityaddon.events.faction.AFbankEinzahlenEventHandler;
import com.rettichlp.unicacityaddon.events.faction.ContractEventHandler;
import com.rettichlp.unicacityaddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.unicacityaddon.events.faction.EquipEventHandler;
import com.rettichlp.unicacityaddon.events.faction.FDSFChatEventHandler;
import com.rettichlp.unicacityaddon.events.faction.FDoorEventHandler;
import com.rettichlp.unicacityaddon.events.faction.FactionInfoEventHandler;
import com.rettichlp.unicacityaddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.unicacityaddon.events.faction.ShareLocationEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.BannerEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.DBankMessageEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.DrugInteractionEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.GaggedEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.GiftEigenbedarfEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.PlantEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistModifyEventHandler;
import com.rettichlp.unicacityaddon.events.faction.polizei.HQMessageEventHandler;
import com.rettichlp.unicacityaddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.FireEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.FirstAidEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.ReviveEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.ServiceMessageEventHandler;
import com.rettichlp.unicacityaddon.events.faction.terroristen.BombTimerEventHandler;
import com.rettichlp.unicacityaddon.events.house.HouseDataEventHandler;
import com.rettichlp.unicacityaddon.events.house.HouseInteractionEventHandler;
import com.rettichlp.unicacityaddon.events.house.HouseRenterEventHandler;
import com.rettichlp.unicacityaddon.events.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.events.job.JobEventHandler;
import com.rettichlp.unicacityaddon.events.team.ReportEventHandler;
import com.rettichlp.unicacityaddon.events.teamspeak.WaitingRoomEventHandler;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
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
        this.registerSettingCategory();

        this.registerListener(new ABuyEventHandler());
        this.registerListener(new AccountEventHandler());
        this.registerListener(new CarEventHandler());
        this.registerListener(new DeathsKillsEventHandler());
        this.registerListener(new FriendJoinEventHandler());
        this.registerListener(new HotkeyEventHandler());
        this.registerListener(new KarmaMessageEventHandler());
        this.registerListener(new MobileEventHandler());
        this.registerListener(new MoneyEventHandler());
        this.registerListener(new NameTagEventHandler());
        this.registerListener(new NavigationEventHandler());
        this.registerListener(new RenderTagEventHandler());
        this.registerListener(new TabListEventHandler());
        this.registerListener(new TickEventHandler());
        this.registerListener(new TimerEventHandler());
        this.registerListener(new WeaponClickEventHandler());
        // chatlog
        this.registerListener(new ChatLogReceiveChatEventHandler());
        this.registerListener(new ChatLogSendChatEventHandler());
        // faction
        this.registerListener(new AEquipEventHandler());
        this.registerListener(new AFbankEinzahlenEventHandler());
        this.registerListener(new ContractEventHandler());
        this.registerListener(new EmergencyServiceEventHandler());
        this.registerListener(new EquipEventHandler());
        this.registerListener(new FDSFChatEventHandler());
        this.registerListener(new FDoorEventHandler());
        this.registerListener(new FactionInfoEventHandler());
        this.registerListener(new ReinforcementEventHandler());
        this.registerListener(new ShareLocationEventHandler());
        // faction - badfaction
        this.registerListener(new BannerEventHandler());
        this.registerListener(new DBankMessageEventHandler());
        this.registerListener(new DrugInteractionEventHandler());
        this.registerListener(new GaggedEventHandler());
        this.registerListener(new GiftEigenbedarfEventHandler());
        this.registerListener(new PlantEventHandler());
        // faction - badfaction - blacklist
        this.registerListener(new BlacklistEventHandler());
        this.registerListener(new BlacklistModifyEventHandler());
        // faction - polizei
        this.registerListener(new HQMessageEventHandler());
        this.registerListener(new WantedEventHandler());
        // faction - rettungsdienst
        this.registerListener(new FireEventHandler());
        this.registerListener(new FirstAidEventHandler());
        this.registerListener(new MedicationEventHandler());
        this.registerListener(new ReviveEventHandler());
        this.registerListener(new ServiceMessageEventHandler());
        // faction - terroristen
        this.registerListener(new BombTimerEventHandler());
        // house
        this.registerListener(new HouseDataEventHandler());
        this.registerListener(new HouseInteractionEventHandler());
        this.registerListener(new HouseRenterEventHandler());
        // job
        this.registerListener(new FishermanEventHandler());
        this.registerListener(new JobEventHandler());
        // team
        this.registerListener(new ReportEventHandler());
        // teamspeak
        this.registerListener(new WaitingRoomEventHandler());

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

        BroadcastChecker.start();
        TokenManager.createToken();
        Syncer.syncAll();
        new Thread(TSClientQuery::getInstance).start();
        FileManager.loadData();

        this.logger().info("Enabled UnicacityAddon");
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    public static boolean isUnicacity() {
        return true; // TODO: 08.02.2023

//        if (MINECRAFT.clientWorld() == null)
//            return false;
//
//        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
//        if (serverData == null)
//            return false;
//
//        return serverData.address().matches("unicacity", 25565, true);
    }
}