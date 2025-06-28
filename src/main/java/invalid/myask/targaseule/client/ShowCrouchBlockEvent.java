package invalid.myask.targaseule.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import invalid.myask.targaseule.Config;
import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.util.ModLoaded;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import xonin.backhand.api.core.BackhandUtils;

public class ShowCrouchBlockEvent {
    public static ShowCrouchBlockEvent instance = new ShowCrouchBlockEvent();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void moveArm(RenderLivingEvent.Pre event) {
        if (Config.block_on_crouch && event.entity.isSneaking() && event.entity instanceof EntityPlayer player && event.renderer instanceof RenderPlayer rPlayer) {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemShield) {
                rPlayer.modelArmorChestplate.heldItemRight = rPlayer.modelArmor.heldItemRight = rPlayer.modelBipedMain.heldItemRight = 3;
            }
            if (ModLoaded.isBackHand() && BackhandUtils.getOffhandItem(player) != null && BackhandUtils.getOffhandItem(player).getItem() instanceof ItemShield) {
                rPlayer.modelArmorChestplate.heldItemLeft = rPlayer.modelArmor.heldItemLeft = rPlayer.modelBipedMain.heldItemLeft = 3;
            }
        }
    }
}
