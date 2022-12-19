package com.rettichlp.unicacityaddon;

import com.google.inject.Singleton;
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
import com.rettichlp.unicacityaddon.events.ShutDownEventHandler;
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
import com.rettichlp.unicacityaddon.events.faction.badfaction.FBIHackEventHandler;
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
import com.rettichlp.unicacityaddon.listener.ExampleGameTickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.models.addon.annotation.AddonListener;
import net.labymod.api.util.logging.Logging;

@Singleton
@AddonListener
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    public static final String VERSION = "2.0.0-dev";
    public static Minecraft MINECRAFT;
    public static Logging LOGGER;
    public static UnicacityAddon ADDON;
    public static UnicacityAddonConfiguration configuration;

    @Override
    protected void enable() {
        MINECRAFT = labyAPI().minecraft();
        ADDON = this;
        configuration = ADDON.configuration();
        LOGGER = this.logger();

        // TODO: 16.12.2022 NOT FOR PROD
        this.registerCommand(MoveToCommand.class);
        this.registerCommand(ASetBlacklistCommand.class);
        this.registerCommand(ClockCommand.class);
        this.registerCommand(ModifyWantedsCommand.class);
        this.registerCommand(MemberInfoCommand.class);
        this.registerCommand(PunishCommand.class);
        this.registerCommand(TokenCommand.class);
        this.registerCommand(ARezeptCommand.class);
        this.registerCommand(NearestJobCommand.class);
        this.registerCommand(BroadcastCommand.class);
        this.registerCommand(MoveCommand.class);
        this.registerCommand(DyavolCommand.class);
        this.registerCommand(AFbankEinzahlenCommand.class);
        this.registerCommand(HouseBankCommand.class);
        this.registerCommand(TSJoinCommand.class);
        this.registerCommand(TimerCommand.class);
        this.registerCommand(CalculateCommand.class);
        this.registerCommand(NearestATMCommand.class);
        this.registerCommand(CoordlistCommand.class);
        this.registerCommand(CountdownCommand.class);
        this.registerCommand(ShutdownJailCommand.class);
        this.registerCommand(SellDrugCommand.class);
        this.registerCommand(StummCommand.class);
        this.registerCommand(ModifyBlacklistCommand.class);
        this.registerCommand(ExplosiveBeltCommand.class);
        this.registerCommand(TopListCommand.class);
        this.registerCommand(AEquipCommand.class);
        this.registerCommand(ACallCommand.class);
        this.registerCommand(ReichensteuerCommand.class);
        this.registerCommand(NaviCommand.class);
        this.registerCommand(DForceCommand.class);
        this.registerCommand(HousebanReasonCommand.class);
        this.registerCommand(DiscordCommand.class);
        this.registerCommand(HouseStorageCommand.class);
        this.registerCommand(ShareLocationCommand.class);
        this.registerCommand(EinzahlenCommand.class);
        this.registerCommand(YasinCommand.class);
        this.registerCommand(PlayerGroupCommand.class);
        this.registerCommand(ServiceCountCommand.class);
        this.registerCommand(EquipListCommand.class);
        this.registerCommand(ASMSCommand.class);
        this.registerCommand(TodoListCommand.class);
//        this.registerCommand(ChatLogCommand.class);
        this.registerCommand(BlacklistInfoCommand.class);
        this.registerCommand(SyncPlayerDataCommand.class);
        this.registerCommand(ReinforcementCommand.class);
        this.registerCommand(MoveHereCommand.class);
        this.registerCommand(FForceCommand.class);
        this.registerCommand(ARezeptAnnehmenCommand.class);
        this.registerCommand(ShutdownFriedhofCommand.class);
        this.registerCommand(ChannelActivityCommand.class);
        this.registerCommand(ASUCommand.class);
        this.registerCommand(ScreenCommand.class);
        this.registerCommand(GiftEigenbedarfCommand.class);
        this.registerCommand(ABuyCommand.class);
        this.registerCommand(WantedReasonCommand.class);
        this.registerCommand(SFForceCommand.class);
        this.registerCommand(ADropMoneyCommand.class);
        this.registerCommand(HousebanCommand.class);
        this.registerCommand(FactionInfoCommand.class);
        this.registerCommand(ReplyCommand.class);
        this.registerCommand(CancelCountdownCommand.class);
        this.registerCommand(UpdateAddonCommand.class);
        this.registerCommand(EigenbedarfCommand.class);
        this.registerCommand(BlockCommand.class);
        this.registerCommand(TSFindCommand.class);
        this.registerCommand(NaviPointCommand.class);
        this.registerCommand(BlacklistReasonCommand.class);
        this.registerCommand(GaggedCommand.class);
        this.registerCommand(SchmarzmarktLocationsCommand.class);
        this.registerCommand(ACaptureCommand.class);
        this.registerListener(BlacklistModifyEventHandler.class);
        this.registerListener(TickEventHandler.class);
        this.registerListener(FDoorEventHandler.class);
        this.registerListener(FBIHackEventHandler.class);
        this.registerListener(WantedEventHandler.class);
//        this.registerListener(ChatLogReceiveChatEventHandler.class);
        this.registerListener(HQMessageEventHandler.class);
        this.registerListener(FriendJoinEventHandler.class);
        this.registerListener(BlacklistEventHandler.class);
        this.registerListener(HouseDataEventHandler.class);
        this.registerListener(HotkeyEventHandler.class);
        this.registerListener(DeathsKillsEventHandler.class);
        this.registerListener(KarmaMessageEventHandler.class);
        this.registerListener(ReportEventHandler.class);
        this.registerListener(GiftEigenbedarfEventHandler.class);
//        this.registerListener(DutyEventHandler.class);
        this.registerListener(MedicationEventHandler.class);
        this.registerListener(EquipEventHandler.class);
        this.registerListener(ServiceMessageEventHandler.class);
        this.registerListener(BombTimerEventHandler.class);
        this.registerListener(WeaponClickEventHandler.class);
        this.registerListener(WaitingRoomEventHandler.class);
        this.registerListener(AccountEventHandler.class);
//        this.registerListener(ChatLogSendChatEventHandler.class);
        this.registerListener(HouseRenterEventHandler.class);
        this.registerListener(FireEventHandler.class);
        this.registerListener(FactionInfoEventHandler.class);
        this.registerListener(ReviveEventHandler.class);
        this.registerListener(MoneyEventHandler.class);
        this.registerListener(AFbankEinzahlenEventHandler.class);
        this.registerListener(ShareLocationEventHandler.class);
        this.registerListener(GaggedEventHandler.class);
        this.registerListener(PlantEventHandler.class);
        this.registerListener(ReinforcementEventHandler.class);
        this.registerListener(JobEventHandler.class);
        this.registerListener(DBankMessageEventHandler.class);
//        this.registerListener(HouseInteractionEventHandler.class);
        this.registerListener(MobileEventHandler.class);
        this.registerListener(ContractEventHandler.class);
        this.registerListener(FDSFChatEventHandler.class);
        this.registerListener(NavigationEventHandler.class);
        this.registerListener(FirstAidEventHandler.class);
        this.registerListener(FishermanEventHandler.class);
        this.registerListener(NameTagEventHandler.class);
        this.registerListener(ABuyEventHandler.class);
        this.registerListener(AEquipEventHandler.class);
        this.registerListener(CarEventHandler.class);
        this.registerListener(EmergencyServiceEventHandler.class);
        this.registerListener(ShutDownEventHandler.class);

        //        UpdateUtils.modFile = e.getSourceFile();
        //        asmDataTable = e.getAsmData();
        //        CommandRegistry.register();
        //        EventRegistry.register();

        this.registerSettingCategory();
        //        HudWidgetRegistry.register();

        this.registerListener(ExampleGameTickListener.class);
        this.registerCommand(ExamplePingCommand.class);

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

        ServerData serverData = ADDON.labyAPI().serverController().getCurrentServerData();
        if (serverData == null)
            return false;

        return serverData.address().matches("unicacity", 25565, true);
    }
}