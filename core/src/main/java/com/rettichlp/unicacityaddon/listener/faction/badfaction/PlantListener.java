package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 */
@UCEvent
public class PlantListener {

    private final UnicacityAddon unicacityAddon;

    public PlantListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    //    @Subscribe
//    public void onPlayerInteract(PlayerInteractEvent e) {
//        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || e.getHand().equals(EnumHand.OFF_HAND) || !UnicacityAddon.isUnicacity()) return;
//
//        World world = e.getWorld();
//        BlockPos clickedBlockPos = e.getPos();
//        BlockPos bottomBlockPos = e.getPos().down();
//
//        boolean clickedBlockIsFern = world.getBlockState(clickedBlockPos).equals(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
//        boolean bottomBlockIsPodzol = world.getBlockState(bottomBlockPos).equals(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
//
//        if (!clickedBlockIsFern || !bottomBlockIsPodzol) return;
//
//        p.sendServerMessage("/plant");
//    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.PLANT_HARVEST_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setPlantFertilizeTime(0L);
            FileManager.DATA.setPlantWaterTime(0L);
            return;
        }

        Matcher plantUseMatcher = PatternHandler.PLANT_USE_PATTERN.matcher(msg);
        if (plantUseMatcher.find() && msg.contains(UnicacityAddon.PLAYER.getName())) {
            if (msg.contains("gewässert") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > FileManager.DATA.getPlantWaterTime()) {
                FileManager.DATA.setPlantWaterTime(System.currentTimeMillis());
            } else if (msg.contains("gedüngt") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > FileManager.DATA.getPlantFertilizeTime()) {
                FileManager.DATA.setPlantFertilizeTime(System.currentTimeMillis());
            }
        }
    }
}