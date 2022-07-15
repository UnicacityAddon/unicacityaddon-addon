package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import net.labymod.api.events.MessageReceiveEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;

/*
    @author Dimiikou

    Partly copied from https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ReinforcementEvent implements MessageReceiveEvent {

    private static ReinforcementCommand.ReinforcementType lastReinforcement;

    @Override
    public boolean onReceive(String msg, String formattedMsg) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher reinforcementMatcher = PatternHandler.REINFORCEMENT_PATTERN.matcher(msg);

        if (reinforcementMatcher.find()) {
            String fullName = reinforcementMatcher.group(1);
            String name = reinforcementMatcher.group(2);
            String[] splitFormattedMsg = formattedMsg.split(":");

            int posX = Integer.parseInt(reinforcementMatcher.group(3));
            int posY = Integer.parseInt(reinforcementMatcher.group(4));
            int posZ = Integer.parseInt(reinforcementMatcher.group(5));

            int distance = (int) p.getPosition().getDistance(posX, posY, posZ);

            boolean dChat = splitFormattedMsg[0].contains(ColorCode.RED.getCode())
                    && splitFormattedMsg[1].contains(ColorCode.RED.getCode());

            Message.Builder builder = Message.getBuilder();

            if (lastReinforcement != null && name.equals(lastReinforcement.getIssuer()) && System.currentTimeMillis() - lastReinforcement.getTime() < 1000) {
                builder.of(lastReinforcement.getType().getMessage()).color(ColorCode.RED).advance().space();
            }

            CustomNaviPoint nearestNaviPoint = ForgeUtils.getNearestObject(new BlockPos(posX, posY, posZ), NavigationUtil.NAVI_POINTS, CustomNaviPoint::getX, CustomNaviPoint::getY, CustomNaviPoint::getZ).getValue();
            if (nearestNaviPoint == null)
                nearestNaviPoint = new CustomNaviPoint(Collections.singletonList("n/a"), 0, 0, 0); // fix for instances where the webserver is not available
        }

        return false;
    }
}
