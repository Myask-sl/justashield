package invalid.myask.targaseule.client;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderLivingEvent;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import invalid.myask.targaseule.Config;
import invalid.myask.undertow.compat.BackhandWraps;
import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.util.ModLoaded;
import invalid.myask.undertow.util.ShieldUtil;

import xonin.backhand.api.core.BackhandUtils;

public class BlockAnimTweakEventHandler {

    public static BlockAnimTweakEventHandler instance = new BlockAnimTweakEventHandler();

    @SuppressWarnings("unused")
    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void moveArm(RenderLivingEvent.Pre event) {
        if (Config.block_on_crouch && event.entity.isSneaking()
            && event.entity instanceof EntityPlayer player
            && event.renderer instanceof RenderPlayer rPlayer) {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemShield) {
                rPlayer.modelArmorChestplate.heldItemRight = rPlayer.modelArmor.heldItemRight = rPlayer.modelBipedMain.heldItemRight = 3;
            }
            if (ModLoaded.isBackHand() && BackhandUtils.getOffhandItem(player) != null
                && BackhandUtils.getOffhandItem(player).getItem() instanceof ItemShield) {
                rPlayer.modelArmorChestplate.heldItemLeft = rPlayer.modelArmor.heldItemLeft = rPlayer.modelBipedMain.heldItemLeft = 3;
            }
        }
        if (Config.no_sword_block_tilt_when_shield_block
            && event.entity instanceof EntityPlayer player
            && event.renderer instanceof RenderPlayer rPlayer) {
            if (ShieldUtil.isUsingShield(player)) {
                if (ShieldUtil.nullguardGetItem(player.getCurrentEquippedItem()) instanceof ItemSword) {
                    rPlayer.modelArmorChestplate.heldItemRight = rPlayer.modelArmor.heldItemRight = rPlayer.modelBipedMain.heldItemRight = 0;
                } else if (ShieldUtil.nullguardGetItem(BackhandWraps.passthrough.getOffHandItem(player)) instanceof ItemSword) {
                    rPlayer.modelArmorChestplate.heldItemLeft = rPlayer.modelArmor.heldItemLeft = rPlayer.modelBipedMain.heldItemLeft = 0;
                }
            }
        }
    }
}
