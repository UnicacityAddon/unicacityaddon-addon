package com.rettichlp.unicacityaddon.base.registry;

import com.google.common.collect.Sets;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.badge.NoPushBadge;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCBadge;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
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
import com.rettichlp.unicacityaddon.commands.GetGunPatternCommand;
import com.rettichlp.unicacityaddon.commands.MaskInfoCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoAllCommand;
import com.rettichlp.unicacityaddon.commands.MemberInfoCommand;
import com.rettichlp.unicacityaddon.commands.NaviCommand;
import com.rettichlp.unicacityaddon.commands.NearestATMCommand;
import com.rettichlp.unicacityaddon.commands.NearestJobCommand;
import com.rettichlp.unicacityaddon.commands.NearestNaviPointCommand;
import com.rettichlp.unicacityaddon.commands.RoleplayNameCommand;
import com.rettichlp.unicacityaddon.commands.ScreenCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownJailCommand;
import com.rettichlp.unicacityaddon.commands.SyncCommand;
import com.rettichlp.unicacityaddon.commands.TimerCommand;
import com.rettichlp.unicacityaddon.commands.TodoListCommand;
import com.rettichlp.unicacityaddon.commands.api.AutoNCCommand;
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
import com.rettichlp.unicacityaddon.commands.api.activity.DrugActivityCommand;
import com.rettichlp.unicacityaddon.commands.api.activity.MoneyActivityCommand;
import com.rettichlp.unicacityaddon.commands.api.activity.PayEquipCommand;
import com.rettichlp.unicacityaddon.commands.api.activity.ProtectionMoneyCommand;
import com.rettichlp.unicacityaddon.commands.api.activity.RoleplayActivityCommand;
import com.rettichlp.unicacityaddon.commands.faction.CheckActiveMembersCommand;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.commands.faction.EquipListCommand;
import com.rettichlp.unicacityaddon.commands.faction.FactionBankDepositCommand;
import com.rettichlp.unicacityaddon.commands.faction.ReinforcementCommand;
import com.rettichlp.unicacityaddon.commands.faction.SellDrugAllCommand;
import com.rettichlp.unicacityaddon.commands.faction.ServiceCountCommand;
import com.rettichlp.unicacityaddon.commands.faction.ShareLocationCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ASellDrugCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ASetBlacklistCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.BlackMarketCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.BlacklistInfoCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GaggedCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ModifyBlacklistCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.OwnUseCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.OwnUseGiftCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.SellDrugCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.DForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.FForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.chat.SFForceCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.CheckFireCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.RecipeAcceptCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.RecipeCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ASUCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ClearCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.CorruptionCalculatorCommand;
import com.rettichlp.unicacityaddon.commands.faction.state.ModifyWantedsCommand;
import com.rettichlp.unicacityaddon.commands.faction.terroristen.ExplosiveBeltCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseBankCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseBankDropGetAllCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseDropAmmunitionCommand;
import com.rettichlp.unicacityaddon.commands.house.HouseStorageCommand;
import com.rettichlp.unicacityaddon.commands.job.ADropMoneyCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ACallCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ASMSCommand;
import com.rettichlp.unicacityaddon.commands.mobile.BlockCommand;
import com.rettichlp.unicacityaddon.commands.mobile.MobileMuteCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ReplyCommand;
import com.rettichlp.unicacityaddon.commands.money.ATMFillCommand;
import com.rettichlp.unicacityaddon.commands.money.DepositCommand;
import com.rettichlp.unicacityaddon.commands.money.RichTaxesCommand;
import com.rettichlp.unicacityaddon.commands.supporter.PunishCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.ChannelActivityCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveHereCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.MoveToCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSFindCommand;
import com.rettichlp.unicacityaddon.commands.teamspeak.TSJoinCommand;
import com.rettichlp.unicacityaddon.hudwidgets.AmmunitionHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.CarHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.EmergencyServiceHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.HearthHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.InventoryHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.JobHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.MajorEventHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.MaskHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.MoneyHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PayDayHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PlantHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.TimerHudWidget;
import com.rettichlp.unicacityaddon.listener.AccountListener;
import com.rettichlp.unicacityaddon.listener.BroadcastListener;
import com.rettichlp.unicacityaddon.listener.CarListener;
import com.rettichlp.unicacityaddon.listener.DrugListener;
import com.rettichlp.unicacityaddon.listener.EquipShopListener;
import com.rettichlp.unicacityaddon.listener.EventRegistrationListener;
import com.rettichlp.unicacityaddon.listener.GangwarListener;
import com.rettichlp.unicacityaddon.listener.KarmaMessageListener;
import com.rettichlp.unicacityaddon.listener.LabyConnectListener;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import com.rettichlp.unicacityaddon.listener.MoneyListener;
import com.rettichlp.unicacityaddon.listener.NameTagRenderListener;
import com.rettichlp.unicacityaddon.listener.NavigationListener;
import com.rettichlp.unicacityaddon.listener.ScreenRenderListener;
import com.rettichlp.unicacityaddon.listener.ScreenshotListener;
import com.rettichlp.unicacityaddon.listener.ServerLoginListener;
import com.rettichlp.unicacityaddon.listener.TabCompletionListener;
import com.rettichlp.unicacityaddon.listener.TimerListener;
import com.rettichlp.unicacityaddon.listener.WeaponListener;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogReceiveChatListener;
import com.rettichlp.unicacityaddon.listener.chatlog.ChatLogSendChatListener;
import com.rettichlp.unicacityaddon.listener.faction.AFbankEinzahlenListener;
import com.rettichlp.unicacityaddon.listener.faction.ContractListener;
import com.rettichlp.unicacityaddon.listener.faction.EmergencyServiceListener;
import com.rettichlp.unicacityaddon.listener.faction.FDSFChatListener;
import com.rettichlp.unicacityaddon.listener.faction.FDoorListener;
import com.rettichlp.unicacityaddon.listener.faction.MajorEventListener;
import com.rettichlp.unicacityaddon.listener.faction.MemberInfoListener;
import com.rettichlp.unicacityaddon.listener.faction.ReinforcementListener;
import com.rettichlp.unicacityaddon.listener.faction.ShareLocationListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.BannerListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GaggedListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.PlantListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistModifyListener;
import com.rettichlp.unicacityaddon.listener.faction.kirche.PrayListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.FirstAidListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveListener;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import com.rettichlp.unicacityaddon.listener.house.HouseDataListener;
import com.rettichlp.unicacityaddon.listener.house.HouseInteractionListener;
import com.rettichlp.unicacityaddon.listener.house.HouseRenterListener;
import com.rettichlp.unicacityaddon.listener.house.HouseWeaponListener;
import com.rettichlp.unicacityaddon.listener.job.FishermanListener;
import com.rettichlp.unicacityaddon.listener.job.JobListener;
import com.rettichlp.unicacityaddon.listener.job.WinemakerListener;
import com.rettichlp.unicacityaddon.listener.team.ADutyListener;
import com.rettichlp.unicacityaddon.listener.team.AdListener;
import com.rettichlp.unicacityaddon.listener.team.NewbieChatListener;
import com.rettichlp.unicacityaddon.listener.team.ReportListener;
import com.rettichlp.unicacityaddon.listener.teamspeak.TeamSpeakKeyListener;
import com.rettichlp.unicacityaddon.listener.teamspeak.TeamSpeakNotificationListener;
import com.rettichlp.unicacityaddon.nametags.AddonTag;
import com.rettichlp.unicacityaddon.nametags.DutyTag;
import com.rettichlp.unicacityaddon.nametags.FactionInfoTag;
import com.rettichlp.unicacityaddon.nametags.HouseBanTag;
import com.rettichlp.unicacityaddon.nametags.NoPushTag;
import com.rettichlp.unicacityaddon.nametags.OutlawTag;
import com.rettichlp.unicacityaddon.nametags.RoleplayNameTag;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 */
public class Registry {

    @Accessors(fluent = true)
    @Getter
    private final Set<Command> commands = new HashSet<>();

    private final HashSet<Class<?>> badgeList = Sets.newHashSet(
            NoPushBadge.class
    );

    private final HashSet<Class<?>> nameTagList = Sets.newHashSet(
            AddonTag.class,
            DutyTag.class,
            FactionInfoTag.class,
            HouseBanTag.class,
            NoPushTag.class,
            OutlawTag.class,
            RoleplayNameTag.class
    );

    private final HashSet<Class<?>> hudWidgetList = Sets.newHashSet(
            AmmunitionHudWidget.class,
            CarHudWidget.class,
            EmergencyServiceHudWidget.class,
            HearthHudWidget.class,
            InventoryHudWidget.class,
            JobHudWidget.class,
            MajorEventHudWidget.class,
            MaskHudWidget.class,
            MoneyHudWidget.class,
            PayDayHudWidget.class,
            PlantHudWidget.class,
            TimerHudWidget.class
    );

    private final HashSet<Class<?>> listenerList = Sets.newHashSet(
            AccountListener.class,
            AdListener.class,
            ADutyListener.class,
            AFbankEinzahlenListener.class,
            BannerListener.class,
            BlacklistListener.class,
            BlacklistModifyListener.class,
            BroadcastListener.class,
            CarListener.class,
            ChatLogReceiveChatListener.class,
            ChatLogSendChatListener.class,
            ContractListener.class,
            DrugListener.class,
            EmergencyServiceListener.class,
            EquipShopListener.class,
            EventRegistrationListener.class,
            FDSFChatListener.class,
            FDoorListener.class,
            FishermanListener.class,
            FirstAidListener.class,
            GaggedListener.class,
            GangwarListener.class,
            HouseDataListener.class,
            HouseInteractionListener.class,
            HouseRenterListener.class,
            HouseWeaponListener.class,
            JobListener.class,
            KarmaMessageListener.class,
            LabyConnectListener.class,
            MajorEventListener.class,
            MedicationListener.class,
            MemberInfoListener.class,
            MobileListener.class,
            MoneyListener.class,
            NavigationListener.class,
            NameTagRenderListener.class,
            NewbieChatListener.class,
            PlantListener.class,
            PrayListener.class,
            ReinforcementListener.class,
            ReportListener.class,
            ReviveListener.class,
            ScreenshotListener.class,
            ScreenRenderListener.class,
            ServerLoginListener.class,
            ShareLocationListener.class,
            TabCompletionListener.class,
            TeamSpeakKeyListener.class,
            TeamSpeakNotificationListener.class,
            TimerListener.class,
            WantedListener.class,
            WeaponListener.class,
            WinemakerListener.class
    );

    private final HashSet<Class<?>> commandList = Sets.newHashSet(
            ABuyCommand.class,
            ACallCommand.class,
            ADropMoneyCommand.class,
            ASellDrugCommand.class,
            ASMSCommand.class,
            ASUCommand.class,
            ASetBlacklistCommand.class,
            ATMFillCommand.class,
            ActivityCommand.class,
            AutoNCCommand.class,
            BlackMarketCommand.class,
            BlacklistInfoCommand.class,
            BlacklistReasonCommand.class,
            BlockCommand.class,
            BroadcastCommand.class,
            BusCommand.class,
            CalculateCommand.class,
            CancelCountdownCommand.class,
            ChannelActivityCommand.class,
            ChatLogCommand.class,
            CheckActiveMembersCommand.class,
            CheckFireCommand.class,
            ClearCommand.class,
            ClockCommand.class,
            CoordlistCommand.class,
            CorruptionCalculatorCommand.class,
            CountdownCommand.class,
            DForceCommand.class,
            DepositCommand.class,
            DiscordCommand.class,
            DropDrugAllCommand.class,
            DrugActivityCommand.class,
            DutyCommand.class,
            DyavolCommand.class,
            EquipListCommand.class,
            ExplosiveBeltCommand.class,
            FForceCommand.class,
            FactionBankDepositCommand.class,
            GaggedCommand.class,
            GetGunPatternCommand.class,
            HouseBankCommand.class,
            HouseBankDropGetAllCommand.class,
            HouseDropAmmunitionCommand.class,
            HouseStorageCommand.class,
            HousebanCommand.class,
            HousebanReasonCommand.class,
            MaskInfoCommand.class,
            MemberInfoAllCommand.class,
            MemberInfoCommand.class,
            MobileMuteCommand.class,
            ModifyBlacklistCommand.class,
            ModifyWantedsCommand.class,
            MoneyActivityCommand.class,
            MoveCommand.class,
            MoveHereCommand.class,
            MoveToCommand.class,
            NaviCommand.class,
            NaviPointCommand.class,
            NearestATMCommand.class,
            NearestJobCommand.class,
            NearestNaviPointCommand.class,
            OwnUseCommand.class,
            OwnUseGiftCommand.class,
            PayEquipCommand.class,
            PlayerGroupCommand.class,
            ProtectionMoneyCommand.class,
            PunishCommand.class,
            RecipeAcceptCommand.class,
            RecipeCommand.class,
            ReinforcementCommand.class,
            ReplyCommand.class,
            ReviveStatsCommand.class,
            RichTaxesCommand.class,
            RoleplayActivityCommand.class,
            RoleplayNameCommand.class,
            SFForceCommand.class,
            ScreenCommand.class,
            SellDrugAllCommand.class,
            SellDrugCommand.class,
            ServiceCountCommand.class,
            ShareLocationCommand.class,
            ShutdownGraveyardCommand.class,
            ShutdownJailCommand.class,
            SyncCommand.class,
            TSFindCommand.class,
            TSJoinCommand.class,
            TimerCommand.class,
            TodoListCommand.class,
            TokenCommand.class,
            TopListCommand.class,
            WantedReasonCommand.class,
            YasinCommand.class
    );

    private final UnicacityAddon unicacityAddon;

    public Registry(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void registerBadges() {
        BadgeRegistry registry = Laby.references().badgeRegistry();

        AtomicInteger registeredBadgeCount = new AtomicInteger();
        Set<Class<?>> badgeClassSet = this.badgeList; // this.unicacityAddon.utilService().getAllClassesFromPackage("com.rettichlp.unicacityaddon.nametags");
        badgeClassSet.stream()
                .filter(badgeClass -> badgeClass.isAnnotationPresent(UCBadge.class))
                .forEach(badgeClass -> {
                    UCBadge ucBadge = badgeClass.getAnnotation(UCBadge.class);
                    try {
                        BadgeRenderer badgeRenderer = (BadgeRenderer) badgeClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                        Objects.requireNonNull(badgeRenderer, "Badge");
                        registry.register(ucBadge.name(), ucBadge.positionType(), badgeRenderer);

                        registeredBadgeCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.unicacityAddon.logger().warn("Can't register Badge: {}", badgeClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} Badges", registeredBadgeCount, badgeClassSet.size());
    }

    public void registerTags() {
        TagRegistry registry = Laby.labyAPI().tagRegistry();

        AtomicInteger registeredNameTagCount = new AtomicInteger();
        Set<Class<?>> nameTagClassSet = this.nameTagList; // this.unicacityAddon.utilService().getAllClassesFromPackage("com.rettichlp.unicacityaddon.nametags");
        nameTagClassSet.stream()
                .filter(nameTagClass -> nameTagClass.isAnnotationPresent(UCNameTag.class))
                .sorted(Comparator.comparing(nameTagClass -> nameTagClass.getAnnotation(UCNameTag.class).priority()))
                .forEach(nameTagClass -> {
                    UCNameTag ucNameTag = nameTagClass.getAnnotation(UCNameTag.class);
                    try {
                        NameTag nameTag = (NameTag) nameTagClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                        Objects.requireNonNull(nameTag, "NameTag");
                        registry.register(ucNameTag.name(), ucNameTag.positionType(), nameTag);

                        registeredNameTagCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.unicacityAddon.logger().warn("Can't register NameTag: {}", nameTagClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} NameTags", registeredNameTagCount, nameTagClassSet.size());
    }

    @SuppressWarnings("unchecked")
    public void registerHudWidgets() {
        HudWidgetRegistry registry = Laby.labyAPI().hudWidgetRegistry();

        AtomicInteger registeredHudWidgetCount = new AtomicInteger();
        Set<Class<?>> hudWidgetClassSet = this.hudWidgetList; // this.unicacityAddon.utilService().getAllClassesFromPackage("com.rettichlp.unicacityaddon.hudwidgets");
        hudWidgetClassSet.forEach(hudWidgetClass -> {
            try {
                TextHudWidget<TextHudWidgetConfig> textHudWidget = (TextHudWidget<TextHudWidgetConfig>) hudWidgetClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                Objects.requireNonNull(textHudWidget, "HudWidget");
                registry.register(textHudWidget);

                registeredHudWidgetCount.getAndIncrement();
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                     InstantiationException e) {
                this.unicacityAddon.logger().warn("Can't register HudWidget: {}", hudWidgetClass.getSimpleName());
                e.printStackTrace();
            }
        });
        this.unicacityAddon.logger().info("Registered {}/{} HudWidgets", registeredHudWidgetCount, hudWidgetClassSet.size());
    }

    public void registerListeners() {
        AtomicInteger registeredListenerCount = new AtomicInteger();
        Set<Class<?>> listenerClassSet = this.listenerList; // this.unicacityAddon.utilService().getAllClassesFromPackage("com.rettichlp.unicacityaddon.listener");
        listenerClassSet.stream()
                .filter(listenerClass -> listenerClass.isAnnotationPresent(UCEvent.class))
                .forEach(listenerClass -> {
                    try {
                        Object listener = listenerClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                        Objects.requireNonNull(listener, "Listener");
                        Laby.labyAPI().eventBus().registerListener(listener);

                        registeredListenerCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.unicacityAddon.logger().warn("Can't register Listener: {}", listenerClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} Listeners", registeredListenerCount, listenerClassSet.size());
    }

    public void registerCommands() {
        AtomicInteger registeredCommandCount = new AtomicInteger();
        AtomicInteger deactivatedCommandCount = new AtomicInteger();
        Set<Class<?>> commandClassSet = this.commandList; // this.unicacityAddon.utilService().getAllClassesFromPackage("com.rettichlp.unicacityaddon.commands");
        commandClassSet.remove(UnicacityCommand.class);
        commandClassSet.stream()
                .filter(commandClass -> commandClass.isAnnotationPresent(UCCommand.class))
                .forEach(commandClass -> {
                    UCCommand ucCommand = commandClass.getAnnotation(UCCommand.class);
                    if (ucCommand.deactivated()) {
                        deactivatedCommandCount.getAndIncrement();
                    } else {
                        try {
                            Command command = (Command) commandClass.getConstructor(UnicacityAddon.class, UCCommand.class).newInstance(this.unicacityAddon, ucCommand);

                            Objects.requireNonNull(command, "Command");
                            this.commands.add(command);
                            Laby.labyAPI().commandService().register(command);

                            registeredCommandCount.getAndIncrement();
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                                 InstantiationException e) {
                            this.unicacityAddon.logger().warn("Can't register Command: {}", commandClass.getSimpleName());
                            e.printStackTrace();
                        }
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} Commands, {} skipped (deactivated)", registeredCommandCount, commandClassSet.size() - deactivatedCommandCount.get(), deactivatedCommandCount.get());
    }
}
