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
import com.rettichlp.unicacityaddon.commands.MemberInfoCommand;
import com.rettichlp.unicacityaddon.commands.NaviCommand;
import com.rettichlp.unicacityaddon.commands.NearestATMCommand;
import com.rettichlp.unicacityaddon.commands.NearestJobCommand;
import com.rettichlp.unicacityaddon.commands.ReichensteuerCommand;
import com.rettichlp.unicacityaddon.commands.ScreenCommand;
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
import com.rettichlp.unicacityaddon.events.WeaponClickEventHandler;
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
import com.rettichlp.unicacityaddon.events.faction.badfaction.DBankMessageEventHandler;
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
import com.rettichlp.unicacityaddon.events.house.HouseRenterEventHandler;
import com.rettichlp.unicacityaddon.events.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.events.job.JobEventHandler;
import com.rettichlp.unicacityaddon.events.team.ReportEventHandler;
import com.rettichlp.unicacityaddon.events.teamspeak.WaitingRoomEventHandler;
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
        this.registerSettingCategory();

        this.registerListener(new ABuyEventHandler()); // TODO: 08.02.2023
        this.registerListener(new AccountEventHandler()); // TODO: 08.02.2023
        this.registerListener(new CarEventHandler()); // TODO: 08.02.2023
        this.registerListener(new DeathsKillsEventHandler()); // TODO: 08.02.2023
        this.registerListener(new FriendJoinEventHandler()); // TODO: 08.02.2023
        this.registerListener(new HotkeyEventHandler()); // TODO: 08.02.2023
        this.registerListener(new KarmaMessageEventHandler()); // TODO: 08.02.2023
        this.registerListener(new MobileEventHandler()); // TODO: 08.02.2023
        this.registerListener(new MoneyEventHandler()); // TODO: 08.02.2023
        this.registerListener(new NameTagEventHandler()); // TODO: 08.02.2023
        this.registerListener(new NavigationEventHandler()); // TODO: 08.02.2023
        this.registerListener(new RenderTagEventHandler()); // TODO: 08.02.2023
        this.registerListener(new TabListEventHandler()); // TODO: 08.02.2023
        this.registerListener(new TickEventHandler()); // TODO: 08.02.2023
        this.registerListener(new TimerEventHandler()); // TODO: 08.02.2023
        this.registerListener(new WeaponClickEventHandler()); // TODO: 08.02.2023
        // chatlog
        this.registerListener(new ChatLogReceiveChatEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ChatLogSendChatEventHandler()); // TODO: 08.02.2023
        // faction
        this.registerListener(new AEquipEventHandler()); // TODO: 08.02.2023
        this.registerListener(new AFbankEinzahlenEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ContractEventHandler()); // TODO: 08.02.2023
        this.registerListener(new EmergencyServiceEventHandler()); // TODO: 08.02.2023
        this.registerListener(new EquipEventHandler()); // TODO: 08.02.2023
        this.registerListener(new FDSFChatEventHandler()); // TODO: 08.02.2023
        this.registerListener(new FDoorEventHandler()); // TODO: 08.02.2023
        this.registerListener(new FactionInfoEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ReinforcementEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ShareLocationEventHandler()); // TODO: 08.02.2023
        // faction - badfaction
        this.registerListener(new BannerEventHandler()); // TODO: 08.02.2023
        this.registerListener(new DBankMessageEventHandler()); // TODO: 08.02.2023
        this.registerListener(new DrugInteractionEventHandler()); // TODO: 08.02.2023
        this.registerListener(new GaggedEventHandler()); // TODO: 08.02.2023
        this.registerListener(new GiftEigenbedarfEventHandler()); // TODO: 08.02.2023
        this.registerListener(new PlantEventHandler()); // TODO: 08.02.2023
        // faction - badfaction - blacklist
        this.registerListener(new BlacklistEventHandler()); // TODO: 08.02.2023
        this.registerListener(new BlacklistModifyEventHandler()); // TODO: 08.02.2023
        // faction - polizei
        this.registerListener(new HQMessageEventHandler()); // TODO: 08.02.2023
        this.registerListener(new WantedEventHandler()); // TODO: 08.02.2023
        // faction - rettungsdienst
        this.registerListener(new FireEventHandler()); // TODO: 08.02.2023
        this.registerListener(new FirstAidEventHandler()); // TODO: 08.02.2023
        this.registerListener(new MedicationEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ReviveEventHandler()); // TODO: 08.02.2023
        this.registerListener(new ServiceMessageEventHandler()); // TODO: 08.02.2023
        // faction - terroristen
        this.registerListener(new BombTimerEventHandler()); // TODO: 08.02.2023
        // house
        this.registerListener(new HouseDataEventHandler());
        this.registerListener(new HouseInteractionEventHandler()); // TODO: 08.02.2023
        this.registerListener(new HouseRenterEventHandler()); // TODO: 08.02.2023
        // job
        this.registerListener(new FishermanEventHandler()); // TODO: 08.02.2023
        this.registerListener(new JobEventHandler()); // TODO: 08.02.2023
        // team
        this.registerListener(new ReportEventHandler()); // TODO: 08.02.2023
        // teamspeak
        this.registerListener(new WaitingRoomEventHandler()); // TODO: 08.02.2023

        this.registerCommand(new ABuyCommand()); // TODO: 08.02.2023
        this.registerCommand(new BusCommand()); // TODO: 08.02.2023
        this.registerCommand(new CalculateCommand()); // TODO: 08.02.2023
        this.registerCommand(new CancelCountdownCommand()); // TODO: 08.02.2023
        this.registerCommand(new ChatLogCommad()); // TODO: 08.02.2023
        this.registerCommand(new ClockCommand()); // TODO: 08.02.2023
        this.registerCommand(new CoordlistCommand()); // TODO: 08.02.2023
        this.registerCommand(new CountdownCommand()); // TODO: 08.02.2023
        this.registerCommand(new DiscordCommand()); // TODO: 08.02.2023
        this.registerCommand(new DyavolCommand()); // TODO: 08.02.2023
        this.registerCommand(new EinzahlenCommand()); // TODO: 08.02.2023
        this.registerCommand(new MemberInfoCommand()); // TODO: 08.02.2023
        this.registerCommand(new NaviCommand()); // TODO: 08.02.2023
        this.registerCommand(new NearestATMCommand()); // TODO: 08.02.2023
        this.registerCommand(new NearestJobCommand()); // TODO: 08.02.2023
        this.registerCommand(new ReichensteuerCommand()); // TODO: 08.02.2023
        this.registerCommand(new ScreenCommand()); // TODO: 08.02.2023
        this.registerCommand(new ShutdownGraveyardCommand()); // TODO: 08.02.2023
        this.registerCommand(new ShutdownJailCommand()); // TODO: 08.02.2023
        this.registerCommand(new SyncPlayerDataCommand()); // TODO: 08.02.2023
        this.registerCommand(new TimerCommand()); // TODO: 08.02.2023
        this.registerCommand(new TodoListCommand()); // TODO: 08.02.2023
        this.registerCommand(new UpdateAddonCommand()); // TODO: 08.02.2023
        this.registerCommand(new YasinCommand()); // TODO: 08.02.2023
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

        if (MINECRAFT.clientWorld() == null)
            return false;

        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null)
            return false;

        return serverData.address().matches("unicacity", 25565, true);
    }
}